package com.makers.pairingapp.controller;


import com.makers.pairingapp.dao.*;
import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.dao.LanguagePreferenceDAO;
import com.makers.pairingapp.dao.MatchDAO;
import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.model.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

@RestController
public class MatchController {

    private final ApplicationUserDAO applicationUserDAO;
    private final MatchDAO matchDAO;
    private final LanguagePreferenceDAO languagePreferenceDAO;
    private final LanguageDAO languageDAO;
    private final MessageDAO messageDAO;

    public MatchController(ApplicationUserDAO applicationUserDAO, MatchDAO matchDAO, LanguagePreferenceDAO languagePreferenceDAO, LanguageDAO languageDAO, MessageDAO messageDAO) {
        this.applicationUserDAO = applicationUserDAO;
        this.matchDAO = matchDAO;
        this.languagePreferenceDAO = languagePreferenceDAO;
        this.languageDAO = languageDAO;
        this.messageDAO = messageDAO;
    }

    @GetMapping("/matches")
    Iterable<Match> all() {
        return matchDAO.findAll();
    }

    @PostMapping("/matches/complete/{match_id}")
    void completeMatch(@PathVariable(value = "match_id") Match match) {
        match.setComplete(true);
        matchDAO.save(match);
    }


    @PostMapping("/matches")
    public void makeMatches() {

        //Setting all matches already in db table to complete
        Iterable<Match> matches = matchDAO.findAll();

        for (Match match : matches) {
            match.setComplete(true);
            matchDAO.save(match);
        }


        MatchingAlgorithm algorithm = new MatchingAlgorithm(matchDAO, languageDAO, languagePreferenceDAO, applicationUserDAO);

        //Calling the languages sort method from matching algorithm which returns list of users sorted in order of least to most languages selected.
        List<Long> orderedUsers = algorithm.languageSort();

        //Calling the makeLanguageHash method which creates a hash to store name of language and the ids of the users who selected it as a preference
        HashMap<String, List<Long>> languageUsers = algorithm.makeLanguageHash(orderedUsers);

        List<List<List<Long>>> potentialPairs = algorithm.getPotentialPairs(orderedUsers, languageUsers);
        if (potentialPairs.size() < orderedUsers.size()){
            matchDAO.deleteAll();
            potentialPairs = algorithm.getPotentialPairs(orderedUsers, languageUsers);
        }

        int skillGap = 3;
        Long firstUserId = null;
        Long secondUserId = null;
        Long languageId = null;
        Boolean status = true;


        List<Long> paired = new ArrayList<Long>();
        for (int x = 0; x < potentialPairs.size(); x++){
            Boolean done = false;

            for (int y = 0; y < potentialPairs.get(x).size(); y++) {
                LanguagePreference preferenceOne = languagePreferenceDAO.findByUserIdAndLanguageId(potentialPairs.get(x).get(y).get(1), potentialPairs.get(x).get(y).get(0));
                LanguagePreference preferenceTwo = languagePreferenceDAO.findByUserIdAndLanguageId(potentialPairs.get(x).get(y).get(2), potentialPairs.get(x).get(y).get(0));

                Integer newSkillGap = Math.abs(preferenceOne.getSkill() - preferenceTwo.getSkill());

                if (newSkillGap < skillGap) {
                    if (!(paired.contains(potentialPairs.get(x).get(y).get(1)) || paired.contains(potentialPairs.get(x).get(y).get(2)))) {
                        skillGap = newSkillGap;
                        firstUserId = potentialPairs.get(x).get(y).get(1);
                        secondUserId = potentialPairs.get(x).get(y).get(2);
                        languageId = potentialPairs.get(x).get(y).get(0);
                    } else {
                        done = true;
                    }
                }
            }

            if (done == false) {
                System.out.println("NEW PAIRING: " + firstUserId + ", " + secondUserId);
                skillGap = 3;
                paired.add(firstUserId);
                paired.add(secondUserId);

                System.out.println("Paired: " + paired);
                Match match = new Match();
                ApplicationUser user1 = applicationUserDAO.findById(firstUserId).get();
                ApplicationUser user2 = applicationUserDAO.findById(secondUserId).get();
                Language language = languageDAO.findById(languageId).get();
                match.setUser1(user1);
                match.setUser2(user2);
                match.setLanguage(language);
                match.setComplete(false);
                match.setStart_time(new Timestamp(System.currentTimeMillis()));
                System.out.println("match in db:" + match);
                matchDAO.save(match);

                Message introMessage = new Message();
                introMessage.setContent("This is an automated message from the admin team. Please contact each other to arrange a convenient time for your pairing session. Have fun!");
                introMessage.setSender(user1);
                introMessage.setReceiver(user2);
                introMessage.setTime_sent(new Timestamp(System.currentTimeMillis()));
                introMessage.setViewed(false);
                messageDAO.save(introMessage);
                System.out.println(introMessage);

            }

            if (paired.size() == orderedUsers.size() || paired.size() == orderedUsers.size()-1){
                break;
            }
        }
    }
}

