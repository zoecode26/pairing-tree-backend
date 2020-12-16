package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.LanguagePreference;
import com.makers.pairingapp.model.Match;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchDAO extends CrudRepository <Match, Long> {
    List<Match> findByUser1IdAndUser2Id(Long user1_id, Long user2_id);
}
