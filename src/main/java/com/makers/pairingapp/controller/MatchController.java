package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.MatchDAO;
import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.model.ApplicationUser;
import com.makers.pairingapp.model.Match;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

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

    @PostMapping("/matches")
    public void makeMatches() {
        List<ApplicationUser> users = applicationUserDAO.findAll();
        System.out.println(users.get(0));

        for (int i = 0; i < users.size()-1; i+=2) {
            Match match = new Match();
            match.setUser1(users.get(i));
            match.setUser2(users.get(i+1));
            match.setStart_time(new Timestamp(System.currentTimeMillis()));
            System.out.println(match);
            matchDAO.save(match);
        }

    }

}
