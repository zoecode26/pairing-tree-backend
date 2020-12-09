package com.makers.pairingapp.model;

import javax.persistence.*;

@Entity
@Table(name = "applicationuser")
public class ApplicationUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String username;
  private String password;

//  NO CONSTRUCTOR????

  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  // NO EQUALS AND HASHCODE METHODS

  @Override
  public String toString() {
    return "ApplicationUser{" +
            "id=" + id +
            ", username='" + username + '\'' +
            '}';
  }
}
