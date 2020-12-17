package com.makers.pairingapp.model;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.dao.LanguagePreferenceDAO;
import com.makers.pairingapp.dao.MatchDAO;

import java.util.*;

public class MatchingAlgorithm {

    private final MatchDAO matchDAO;
    private final LanguageDAO languageDAO;
    private final LanguagePreferenceDAO languagePreferenceDAO;
    private final ApplicationUserDAO applicationUserDAO;

    public MatchingAlgorithm(MatchDAO matchDAO, LanguageDAO languageDAO, LanguagePreferenceDAO languagePreferenceDAO, ApplicationUserDAO applicationUserDAO) {
        this.matchDAO = matchDAO;
        this.languageDAO = languageDAO;
        this.languagePreferenceDAO = languagePreferenceDAO;
        this.applicationUserDAO = applicationUserDAO;
    }


    public List<List<List<Long>>> getPotentialPairs(List<Long> orderedUsers, HashMap<String, List<Long>> languageUsers) {

        //Going through sorted list of user ids from least languages to most languages to pair those with less options first
        List<List<List<Long>>> allPairs = new ArrayList<List<List<Long>>>();
        for (int i = 0; i < orderedUsers.size(); i++) {
            List<List<Long>> potentialPairs = new ArrayList<List<Long>>();
            //Iterates over ordered users again to get one to pair with user first for loop is currently looking at
            for (int j = 0; j < orderedUsers.size(); j++) {
                //Iterating over hash with language names and users that selected them
                for (Map.Entry<String, List<Long>> entry : languageUsers.entrySet()) {
                    //Checking if user we're currently trying to compare is included in the language we're currently iterating over, if not we go to the next language
                    if (entry.getValue().contains(orderedUsers.get(i)) && entry.getValue().contains(orderedUsers.get(j))) {
                        //Checking the user to pair with is not themself and that the user has not already been paired (and their value has therefore been set to 0)
                        //Pairing users have value of 0 to distinguish between paired and unpaired without changing list length and therefore messing up for loop
                        if (orderedUsers.get(i) != orderedUsers.get(j)) {
                            //If the list we're looking at contains out initial user and one other (as near to start of list as possible) who has selected the same language,
                            //they are paired!

                            if (entry.getValue().contains(orderedUsers.get(j))) {

                                List<Match> matchOne = matchDAO.findByUser1IdAndUser2Id(orderedUsers.get(i), orderedUsers.get(j));
                                List<Match> matchTwo = matchDAO.findByUser1IdAndUser2Id(orderedUsers.get(j), orderedUsers.get(i));

                                if (matchOne.size() == 0 && matchTwo.size() == 0) {
                                    List<Long> temp = new ArrayList<Long>();
                                    Language language = languageDAO.findByName(entry.getKey());
                                    temp.add(language.getId());
                                    temp.add(orderedUsers.get(i));
                                    temp.add(orderedUsers.get(j));
                                    potentialPairs.add(temp);
                                }

                            }
                        }
                    }
                }
            }

            if (potentialPairs.size() == 0) {
                break;
            }

            allPairs.add(potentialPairs);
        }

        return allPairs;
    }

    public List<Long> languageSort() {
        //Gets list of all language preferences from DAO
        Iterable<LanguagePreference> languagePreferences = languagePreferenceDAO.findAll();
        //Sets up hash to hold id of each user and the amount of languages they selected as their preferences
        Map<Long, Integer> languageCount = new HashMap<Long, Integer>();

        //Iterates through all of the language preferences
        for (LanguagePreference preference : languagePreferences) {
            //Key variable set equal to the id of the user that the preference belongs to
            long key = preference.getUser().getId();

            //If user already exists as a key in the hash (i.e. one of their preferences has already been counted),
            //one will be added to their value to keep track of another language being added.

            //If the user doesn't exist yet, their value is set to one to count the first language encountered and from
            //then on will be incremented using the code in the if statement.
            ApplicationUser currentUser = applicationUserDAO.findById(key).get();
            if (currentUser.getActive()) {
                if (languageCount.containsKey(key)) {
                    languageCount.put(key, languageCount.get(key) + 1);
                } else {
                    languageCount.put(key, 1);
                }
            }
        }


        //List to hold sorted user ids from least to most languages selected
        List<Long> orderedUsers = new ArrayList<Long>();

        //Sorts languageCount hash by ascending value
        languageCount.entrySet().stream()
                .sorted((k1, k2) -> k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> orderedUsers.add(k.getKey()));

        return orderedUsers;
    }


    public HashMap<String, List<Long>> makeLanguageHash (List<Long>orderedUsers) {
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
        return languageUsers;
    }

}
