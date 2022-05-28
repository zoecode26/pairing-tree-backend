package com.makers.pairingapp.model;

import com.google.common.primitives.Longs;
import com.makers.pairingapp.dao.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchHelper {
    private final LanguageDAO languageDAO;
    private final LanguagePreferenceDAO languagePreferenceDAO;
    private final ApplicationUserDAO applicationUserDAO;
    private final MatchDAO matchDAO;
    private final MessageDAO messageDAO;

    public MatchHelper(LanguageDAO languageDAO, LanguagePreferenceDAO languagePreferenceDAO,
                       ApplicationUserDAO applicationUserDAO, MatchDAO matchDAO, MessageDAO messageDAO) {
        this.languageDAO = languageDAO;
        this.languagePreferenceDAO = languagePreferenceDAO;
        this.applicationUserDAO = applicationUserDAO;
        this.matchDAO = matchDAO;
        this.messageDAO = messageDAO;
    }

    // Generate map of language to a 2d array containing potential user pairings for that language
    public HashMap<Long, ArrayList<ArrayList<Long>>> generateUserPairs () {
        Iterable<Language> languages = languageDAO.findAll();
        HashMap<Long, ArrayList<ArrayList<Long>>> languageHash = new HashMap<>();

        for(Language language: languages) {
            List<LanguagePreference> prefs = languagePreferenceDAO.findByLanguageId(language.getId());
            Collections.sort(prefs);
            List<Long> userIds = prefs.stream().map(pref -> pref.getUser().getId()).collect(Collectors.toList());
            ArrayList<ArrayList<Long>> dividedArray = divideArray(Longs.toArray(userIds), userIds.size());
            languageHash.put(language.getId(), dividedArray);
        }

        return languageHash;
    }

    public void makeMatches(HashMap<Long, ArrayList<ArrayList<Long>>> userPairs) {
        for(Map.Entry<Long, ArrayList<ArrayList<Long>>> entry: userPairs.entrySet()) {
            for(ArrayList<Long> userids: entry.getValue()) {
                if (userids.size() > 0 && userids.get(0) != 0 && userids.get(1) != 0) {
                    Match match = new Match();
                    ApplicationUser user1 = applicationUserDAO.findById(userids.get(0)).get();
                    ApplicationUser user2 = applicationUserDAO.findById(userids.get(1)).get();
                    Language language = languageDAO.findById(entry.getKey()).get();
                    match.setUser1(user1);
                    match.setUser2(user2);
                    match.setLanguage(language);
                    match.setComplete(false);
                    match.setStart_time(new Timestamp(System.currentTimeMillis()));
                    matchDAO.save(match);

                    sendIntroMessage(user1, user2);
                }
            }
        }
    }

    private void sendIntroMessage(ApplicationUser user1, ApplicationUser user2) {
        Message introMessage = new Message();
        introMessage.setContent("This is an automated message from the admin team. Please contact each other to " +
                                "arrange a convenient time for your pairing session. Have fun!");
        introMessage.setSender(user1);
        introMessage.setReceiver(user2);
        introMessage.setTime_sent(new Timestamp(System.currentTimeMillis()));
        introMessage.setViewed(false);
        messageDAO.save(introMessage);
    }

    // Used to split all users of an array into pairs
    private ArrayList<ArrayList<Long>> divideArray(long[] nums, int N) {
        ArrayList<ArrayList<Long>> ans = new ArrayList<ArrayList<Long>>();
        ArrayList<Long> temp = new ArrayList<Long>();
        for (int i = 0; i < N; i++) {
            temp.add(nums[i]);
            if (((i + 1) % 2) == 0) {
                ans.add(temp);
                temp = new ArrayList<Long>();
            }
        }
        // If last group doesn't have enough elements then add 0 to it
        if (temp.size() != 0) {
            int a = temp.size();
            while (a != 2) {
                temp.add(0L);
                a++;
            }
            ans.add(temp);
        }
        return ans;
    }
}
