package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserDAO extends CrudRepository<ApplicationUser, Long> {
  ApplicationUser findByUsername(String username);
}
