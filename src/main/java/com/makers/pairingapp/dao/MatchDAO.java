package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchDAO extends CrudRepository <Match, Long> { }
