package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.LanguageDAO;
import com.makers.pairingapp.model.Language;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

    private final LanguageDAO languageDAO;

    public LanguageController(LanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

    @GetMapping("/languages")
    Iterable<Language> all() {return languageDAO.findAll();}
}
