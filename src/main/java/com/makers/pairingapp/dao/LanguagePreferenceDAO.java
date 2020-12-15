package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.Language;
import com.makers.pairingapp.model.LanguagePreference;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LanguagePreferenceDAO extends CrudRepository <LanguagePreference, Long>{
    List<LanguagePreference> findByUserId(Long user_id);
    List<LanguagePreference> findByLanguageId(Long language_id);
    LanguagePreference findByUserIdAndLanguageId(Long user_id, Long language_id);
}
