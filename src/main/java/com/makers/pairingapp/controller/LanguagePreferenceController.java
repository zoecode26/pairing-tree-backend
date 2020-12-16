package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.dao.LanguagePreferenceDAO;
import com.makers.pairingapp.model.ApplicationUser;
import com.makers.pairingapp.model.Language;
import com.makers.pairingapp.model.LanguagePreference;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

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
    Iterable<LanguagePreference> all() {
        return languagePreferenceDAO.findAll();
    }

    @GetMapping("/languagepreferences/{user_id}")
    List<LanguagePreference> all(@PathVariable(value = "user_id") ApplicationUser user) {
        return languagePreferenceDAO.findByUserId(user.getId());
    }

    @PostMapping("/languagepreferences")
    void newLanguagePreference(@RequestBody Map<String, Integer> body, Principal principal) {

        System.out.println(principal);
        String email = principal.getName();
        ApplicationUser user = applicationUserDAO.findByUsername(email);

        System.out.println(user);
        System.out.println(body.entrySet());


        for (Map.Entry<String, Integer> entry : body.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            Language language = languageDAO.findByName(entry.getKey());
            LanguagePreference newLanguagePreference = new LanguagePreference();
            newLanguagePreference.setUser(user);
            newLanguagePreference.setLanguage(language);
            newLanguagePreference.setSkill(entry.getValue());
            languagePreferenceDAO.save(newLanguagePreference);
        }
    }
}
