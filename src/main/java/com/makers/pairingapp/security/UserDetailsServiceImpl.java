package com.makers.pairingapp.security;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.model.ApplicationUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private ApplicationUserDAO applicationUserDAO;

  public UserDetailsServiceImpl(ApplicationUserDAO applicationUserDAO) {
    this.applicationUserDAO = applicationUserDAO;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ApplicationUser applicationUser = applicationUserDAO.findByUsername(username);
    if (applicationUser == null) {
      throw new UsernameNotFoundException(username);
    }
    return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
  }
}