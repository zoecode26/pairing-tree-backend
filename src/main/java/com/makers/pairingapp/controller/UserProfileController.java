package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.dao.LanguagePreferenceDAO;
import com.makers.pairingapp.model.ApplicationUser;
import com.makers.pairingapp.model.Language;
import com.makers.pairingapp.model.LanguagePreference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserProfileController {
    private final LanguagePreferenceDAO languagePreferenceDAO;
    private final LanguageDAO languageDAO;
    private final ApplicationUserDAO applicationUserDAO;

    public UserProfileController(LanguagePreferenceDAO languagePreferenceDAO, LanguageDAO languageDAO, ApplicationUserDAO applicationUserDAO) {
        this.languagePreferenceDAO = languagePreferenceDAO;
        this.languageDAO = languageDAO;
        this.applicationUserDAO = applicationUserDAO;
    }

    @PostMapping("/profile/{user_id}")
    ApplicationUser updateProfile(@PathVariable(value="user_id") ApplicationUser user, @RequestBody Map<String, String> body) {
        user.setGithub(body.get("github"));
        for (Map.Entry<String, String> entry : body.entrySet()) {
            if(entry.getKey() == "github") {
                continue;
            }
            System.out.println(entry.getKey() + ":" + entry.getValue());
            Language language = languageDAO.findByName(entry.getKey());
            LanguagePreference newLanguagePreference = new LanguagePreference();
            newLanguagePreference.setUser(user);
            newLanguagePreference.setLanguage(language);
            newLanguagePreference.setSkill(entry.getValue());
            languagePreferenceDAO.save(newLanguagePreference);
        }
        user.setActive(true);
        user.setProfileComplete(true);
        return applicationUserDAO.save(user);
    }

    @PostMapping("/profile/toggle-active/{user_id}")
    ApplicationUser toggleActive(@PathVariable(value="user_id") ApplicationUser user, @RequestBody Map<String, Boolean> body) {
        user.setActive(body.get("active"));
        return applicationUserDAO.save(user);
    }

}
