package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.dao.LanguagePreferenceDAO;
import com.makers.pairingapp.model.ApplicationUser;
import com.makers.pairingapp.model.Availability;
import com.makers.pairingapp.model.Language;
import com.makers.pairingapp.model.LanguagePreference;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.*;

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

    @PostMapping("/languagepreferences")
    LanguagePreference newLanguagePreference(@RequestBody Map<String, Object> body, Principal principal) {
        //Optional<ApplicationUser> user = applicationUserDAO.findById(Long.parseLong(body.get("user_id").toString()));
        /// what is this
//        Object java = body.get("java");
//        Object ruby = body.get("ruby");
//        Object py = body.get("python");
//        Object js = body.get("javaScript");
//
//
        HashMap<String, String> user_languages = new HashMap<String, String>();
//        user_languages.put("java", (Boolean) java);
//        user_languages.put("python", (Boolean) py);
//        user_languages.put("ruby", (Boolean) ruby);
//        user_languages.put("javaScript", (Boolean) js);

        //System.out.println(user_languages);

        System.out.println(principal);
        String email = principal.getName();
        ApplicationUser user = applicationUserDAO.findByUsername(email);

        System.out.println(user);


//        for (String language : user_languages) {
//            LanguagePreference newLanguagePreference = new LanguagePreference();
////            newLanguagePreference.setUser(user);
////            newLanguagePreference.setLanguage(language);
////            LanguagePreferenceDAO.save(newLanguagePreference);
////      }
//
//        }
//iterate form

        public void iterateUsingLambda(HashMap<String, String> user_languages) {
            user_languages.forEach((k,v) -> System.out.println(k + v));
        }
                LanguagePreference newLanguagePreference = new LanguagePreference();
        return newLanguagePreference;

    }
}
