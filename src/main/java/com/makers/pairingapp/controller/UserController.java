package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.model.ApplicationUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
  private ApplicationUserDAO applicationUserDAO;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserController(ApplicationUserDAO applicationUserDAO,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.applicationUserDAO = applicationUserDAO;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder    ;
  }

  @PostMapping("/sign-up")
  public void signUp(@RequestBody ApplicationUser user) {
    System.out.println("here");
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    applicationUserDAO.save(user);
  }

}

