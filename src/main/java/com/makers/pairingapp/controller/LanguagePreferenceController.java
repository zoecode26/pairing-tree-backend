package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.dao.LanguagePreferenceDAO;
import com.makers.pairingapp.model.ApplicationUser;
import com.makers.pairingapp.model.Availability;
import com.makers.pairingapp.model.Language;
import com.makers.pairingapp.model.LanguagePreference;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

@RestController
public class LanguagePreferenceController {

    private final LanguagePreferenceDAO languagePreferenceDAO;
    private final LanguageDAO languageDAO;
    private final ApplicationUserDAO applicationUserDAO;


    public LanguagePreferenceController(LanguagePreferenceDAO languagePreferenceDAO, LanguageDAO languageDAO, ApplicationUserDAO applicationUserDAO) {
        this.languagePreferenceDAO = languagePreferenceDAO;
        this.languageDAO = languageDAO;
        this.applicationUserDAO = applicationUserDAO;
    }

    @GetMapping("/languagepreferences")
    Iterable<LanguagePreference> all() {return languagePreferenceDAO.findAll();}

}
