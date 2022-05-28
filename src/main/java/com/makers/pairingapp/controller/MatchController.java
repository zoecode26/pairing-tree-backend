package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.dao.LanguagePreferenceDAO;
import com.makers.pairingapp.dao.MatchDAO;
import com.makers.pairingapp.dao.MessageDAO;
import com.makers.pairingapp.model.Match;
import com.makers.pairingapp.model.MatchHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class MatchController {
    private final ApplicationUserDAO applicationUserDAO;
    private final MatchDAO matchDAO;
    private final LanguagePreferenceDAO languagePreferenceDAO;
    private final LanguageDAO languageDAO;
    private final MessageDAO messageDAO;

    public MatchController(ApplicationUserDAO applicationUserDAO, MatchDAO matchDAO, LanguagePreferenceDAO
                           languagePreferenceDAO, LanguageDAO languageDAO, MessageDAO messageDAO) {
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

        MatchHelper algorithm = new MatchHelper(languageDAO, languagePreferenceDAO, applicationUserDAO,
                                                            matchDAO, messageDAO);

        HashMap<Long, ArrayList<ArrayList<Long>>> userPairs = algorithm.generateUserPairs();
        algorithm.makeMatches(userPairs);
    }
}

