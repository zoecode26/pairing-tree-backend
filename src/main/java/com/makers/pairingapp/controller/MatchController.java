package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.MatchDAO;
import com.makers.pairingapp.dao.UserDAO;
import com.makers.pairingapp.model.Match;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {

    private final UserDAO userDAO;
    private final MatchDAO matchDAO;

    public MatchController(UserDAO userDAO, MatchDAO matchDAO) {
        this.userDAO = userDAO;
        this.matchDAO = matchDAO;
    }

    @GetMapping("/matches")
    Iterable<Match> all() {return matchDAO.findAll();}

}
