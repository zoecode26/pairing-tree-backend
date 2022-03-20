package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.Match;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface MatchDAO extends CrudRepository <Match, Long> {
    List<Match> findByUser1IdAndUser2Id(Long user1_id, Long user2_id);
}
