package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.dao.LanguagePreferenceDAO;
import com.makers.pairingapp.dao.MatchDAO;
import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.model.*;
import org.springframework.web.bind.annotation.GetMapping;
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

    public MatchController(ApplicationUserDAO applicationUserDAO, MatchDAO matchDAO, LanguagePreferenceDAO languagePreferenceDAO, LanguageDAO languageDAO) {
        this.applicationUserDAO = applicationUserDAO;
        this.matchDAO = matchDAO;
        this.languagePreferenceDAO = languagePreferenceDAO;
        this.languageDAO = languageDAO;
    }

    @GetMapping("/matches")
    Iterable<Match> all() {
        return matchDAO.findAll();
    }

    @PostMapping("/matches")
    public void makeMatches() {
        Iterable<Match> matches = matchDAO.findAll();

        for (Match match : matches) {
            match.setComplete(true);
            matchDAO.save(match);
        }

        MatchingAlgorithm algorithm = new MatchingAlgorithm(matchDAO, languageDAO, languagePreferenceDAO);


        //************************************
        //SORTING USERS BY LANGUAGES SELECTED
        //************************************

        List<Long> orderedUsers = algorithm.languageSort();

        //*************************************************
        //GETTING HASH OF LANGUAGE AND USERS THAT CHOSE IT
        //*************************************************

        //Creates hash to store name of language and the ids of the users who selected it as a preference
        HashMap<String, List<Long>> languageUsers = new HashMap<>();
        

        //Goes through users in order of least to most languages
        for (int i = 0; i < orderedUsers.size(); i++) {
            //Gets the user currently being looked at and stores it in currentUser
            Optional<ApplicationUser> currentUser = applicationUserDAO.findById(orderedUsers.get(i));
            //Grabs the language preferences of that user and stores it in list
            List<LanguagePreference> currentUserPreferences = languagePreferenceDAO.findByUserId(currentUser.get().getId());

            //Iterates over all languages the user selected
            for (int j = 0; j < currentUserPreferences.size(); j++) {
                //Gets the id of the language currently being looked at from the users selections
                Long currentLanguageId = currentUserPreferences.get(j).getLanguage().getId();
                //Gets all the preferences made by all users for this language
                List<LanguagePreference> preferencesForLanguage = languagePreferenceDAO.findByLanguageId(currentLanguageId);

                //Iterates over all the preferences made for a chosen language by all users (this will be a language selected by one
                // of the users that needs to be paired)
                for (int k = 0; k < preferencesForLanguage.size(); k++) {
                    //Gets the name of the language and assigns it to key
                    String key = preferencesForLanguage.get(k).getLanguage().getName();
                    //Gets the id of the user who had this language preference and assigns it to value
                    Long value = preferencesForLanguage.get(k).getUser().getId();

                    //If the language already has some users listed under it, the id of the new user that has selected this language is
                    //added to the array of the value

                    //Otherwise, a blank array is created, the user is added and then this is set as the value of a new entry in the hash
                    if (languageUsers.containsKey(key)) {
                        languageUsers.get(key).add(value);
                    } else {
                        List<Long> temp = new ArrayList<Long>();
                        temp.add(value);
                        languageUsers.put(key, temp);
                    }
                }
            }
        }


        //****************************************
        //USING THIS HASH TO PAIR PEOPLE TOGETHER
        //****************************************



        System.out.println("TESTING!");
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
                if (status == true){
                    for (int y = 0; y < potentialPairs.get(x).size(); y++) {
                            LanguagePreference preferenceOne = languagePreferenceDAO.findByUserIdAndLanguageId(potentialPairs.get(x).get(y).get(1), potentialPairs.get(x).get(y).get(0));
                            LanguagePreference preferenceTwo = languagePreferenceDAO.findByUserIdAndLanguageId(potentialPairs.get(x).get(y).get(2), potentialPairs.get(x).get(y).get(0));

                            Integer newSkillGap = Math.abs(preferenceOne.getSkill() - preferenceTwo.getSkill());

                            if (newSkillGap < skillGap) {
                                skillGap = newSkillGap;
                                firstUserId = potentialPairs.get(x).get(y).get(1);
                                secondUserId = potentialPairs.get(x).get(y).get(2);
                                languageId = potentialPairs.get(x).get(y).get(0);
                            }

                        }
                    }
                    System.out.println("NEW PAIRING: "+firstUserId+ ", "+secondUserId);
                    skillGap = 3;
                    paired.add(firstUserId);
                    paired.add(secondUserId);

                    System.out.println("Paired: "+paired);
                    Match match = new Match();
                    ApplicationUser user1 = applicationUserDAO.findById(firstUserId).get();
                    ApplicationUser user2 = applicationUserDAO.findById(secondUserId).get();
                    Language language = languageDAO.findById(languageId).get();
                    match.setUser1(user1);
                    match.setUser2(user2);
                    match.setLanguage(language);
                    match.setComplete(false);
                    match.setStart_time(new Timestamp(System.currentTimeMillis()));
                    System.out.println("match in db:" +match);
                    matchDAO.save(match);

                    if (paired.size() == orderedUsers.size() || paired.size() == orderedUsers.size()-1){
                        break;
                    }
                }
            }
        }



