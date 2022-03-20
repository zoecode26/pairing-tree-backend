package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.LanguagePreference;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface LanguagePreferenceDAO extends CrudRepository <LanguagePreference, Long>{
    List<LanguagePreference> findByUserId(Long user_id);
    List<LanguagePreference> findByLanguageId(Long language_id);
    LanguagePreference findByUserIdAndLanguageId(Long user_id, Long language_id);
}
