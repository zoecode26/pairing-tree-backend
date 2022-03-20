package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.model.ApplicationUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final ApplicationUserDAO applicationUserDAO;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserController(ApplicationUserDAO applicationUserDAO,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.applicationUserDAO = applicationUserDAO;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @PostMapping("/sign-up")
  public void signUp(@RequestBody ApplicationUser user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    applicationUserDAO.save(user);
  }

  @GetMapping("/{user_id}")
  public ApplicationUser getUser(@PathVariable(value = "user_id") ApplicationUser user) {
    return user;
  }
}

