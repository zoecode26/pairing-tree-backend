package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.ApplicationUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ApplicationUserDAO extends JpaRepository<ApplicationUser, Long> {
  static Object getUserProfileId() {
    return ApplicationUserDAO.getUserProfileId();
  }

  ApplicationUser findByUsername(String username);


}
