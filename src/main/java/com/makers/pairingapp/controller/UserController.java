package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.model.ApplicationUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;




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

  @GetMapping("/{user_id}")
  public ApplicationUser getUser(@PathVariable(value = "user_id") ApplicationUser user) {
    return user;
  }



}

