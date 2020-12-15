package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.model.ApplicationUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
