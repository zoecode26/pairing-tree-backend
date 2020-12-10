package com.makers.pairingapp.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "applicationuser")
public class ApplicationUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Email
  @NotEmpty
  @Column(unique=true)
  private String email;
  @NotEmpty
  @Column(columnDefinition = "varchar(100)")
  private String username;
  @Column
  @NotEmpty
  private String password;
  @Column(columnDefinition = "boolean default false")
  private Boolean active;



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
    return "{" +
            "id=" + id +
            ", username='" + username + '\'' +
            '}';
  }
}
