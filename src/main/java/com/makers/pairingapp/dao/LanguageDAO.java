package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.ApplicationUser;
import com.makers.pairingapp.model.Language;
import org.springframework.data.repository.CrudRepository;

public interface LanguageDAO extends CrudRepository<Language, Long> {
    Language findByName(String name);
}
