package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.dao.AvailabilityDAO;
import com.makers.pairingapp.model.ApplicationUser;
import com.makers.pairingapp.model.Availability;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvailabilityController {

    private final AvailabilityDAO availabilityDAO;
    private final ApplicationUserDAO applicationUserDAO;

    public AvailabilityController(AvailabilityDAO availabilityDAO, ApplicationUserDAO applicationUserDAO) {
        this.availabilityDAO = availabilityDAO;
        this.applicationUserDAO = applicationUserDAO;
    }

    @GetMapping("/availabilities")
    Iterable<Availability> all() {return availabilityDAO.findAll();}

    @PostMapping("/availabilities")
    Availability newAvailability(@RequestBody Map<String, Object> body) {
        Availability newAvailability = new Availability();
        newAvailability.setStart_time(Timestamp.valueOf(body.get("start_time").toString()));
        Optional<ApplicationUser> user = applicationUserDAO.findById(Long.parseLong(body.get("user_id").toString()));
        return availabilityDAO.save(newAvailability);
    }

}
