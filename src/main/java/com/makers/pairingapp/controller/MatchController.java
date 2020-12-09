package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.MatchDAO;
import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.model.Match;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {

    private final ApplicationUserDAO applicationUserDAO;
    private final MatchDAO matchDAO;

    public MatchController(ApplicationUserDAO applicationUserDAO, MatchDAO matchDAO) {
        this.applicationUserDAO = applicationUserDAO;
        this.matchDAO = matchDAO;
    }

    @GetMapping("/matches")
    Iterable<Match> all() {return matchDAO.findAll();}

}
