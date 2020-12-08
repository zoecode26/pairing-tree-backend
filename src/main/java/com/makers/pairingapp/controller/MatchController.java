package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.MatchDAO;
import com.makers.pairingapp.dao.UserDAO;
import com.makers.pairingapp.model.Match;
import com.makers.pairingapp.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

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

//    THINK ABOUT HOW WE WILL TRIGGER MATCHES TO BE STORED IN DATABASE
//    @PostMapping("/matches")
//    Match newMatch(@RequestBody Map<String,Object> body) {
//        Match newMatch = new Match();
//        newMatch.setStart_time(Timestamp.valueOf(body.get("start_time").toString()));
//
//        Optional<User> user1 = userDAO.findById(Long.parseLong(body.get("user_id").toString()));
//        newMatch.set(user1.get());
//    }
}
