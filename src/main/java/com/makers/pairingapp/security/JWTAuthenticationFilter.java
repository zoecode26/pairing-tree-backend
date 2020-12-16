package com.makers.pairingapp.security;

import com.auth0.jwt.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.model.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.makers.pairingapp.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;
  private ApplicationUserDAO applicationUserDAO;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationUserDAO applicationUserDAO) {
    this.authenticationManager = authenticationManager;
    this.applicationUserDAO = applicationUserDAO;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
                                              HttpServletResponse res) throws AuthenticationException {
    try {
      ApplicationUser creds = new ObjectMapper()
              .readValue(req.getInputStream(), ApplicationUser.class);

      return authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      creds.getUsername(),
                      creds.getPassword(),
                      new ArrayList<>())
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException, ServletException {

    String token = JWT.create()
            .withSubject(((User) auth.getPrincipal()).getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(HMAC512(SECRET.getBytes()));
    res.setHeader("Access-Control-Allow-Headers", "Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
            "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    PrintWriter payload = res.getWriter();
    ApplicationUser user = this.applicationUserDAO.findByUsername(((User) auth.getPrincipal()).getUsername());
    payload.print(user.getId());
    payload.flush();
  }
}