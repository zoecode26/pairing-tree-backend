package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.AvailabilityDAO;
import com.makers.pairingapp.dao.UserDAO;
import com.makers.pairingapp.model.Availability;
import com.makers.pairingapp.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

@RestController
public class AvailabilityController {

    private final AvailabilityDAO availabilityDAO;
    private final UserDAO userDAO;

    public AvailabilityController(AvailabilityDAO availabilityDAO, UserDAO userDAO) {
        this.availabilityDAO = availabilityDAO;
        this.userDAO = userDAO;
    }

    @GetMapping("/availabilities")
    Iterable<Availability> all() {return availabilityDAO.findAll();}

    @PostMapping("/availabilities")
    Availability newAvailability(@RequestBody Map<String, Object> body) {
        Availability newAvailability = new Availability();
        newAvailability.setStart_time(Timestamp.valueOf(body.get("start_time").toString()));
        Optional<User> user = userDAO.findById(Long.parseLong(body.get("user_id").toString()));
        return availabilityDAO.save(newAvailability);
    }

}
