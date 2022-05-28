package com.makers.pairingapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
  private String username;
  @Column
  private String fullName;
  @Column
  @NotEmpty
  private String password;
  @Column(columnDefinition = "boolean default false")
  private Boolean active;
  @Column
  private String github;
  @Column(columnDefinition = "boolean default false")
  private Boolean profileComplete;

  public ApplicationUser() { }

  public ApplicationUser(@Email @NotEmpty String username, String fullName, @NotEmpty String password, Boolean active,
                         String github, Boolean profileComplete) {
    this.username = username;
    this.fullName = fullName;
    this.password = password;
    this.active = active;
    this.github = github;
    this.profileComplete = profileComplete;
  }

  public Boolean getProfileComplete() {
    return profileComplete;
  }

  public void setProfileComplete(Boolean profileComplete) {
    this.profileComplete = profileComplete;
  }

  public String getGithub() {
    return github;
  }

  public void setGithub(String github) {
    this.github = github;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public long getId(){
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

  @Override
  public String toString() {
    return "ApplicationUser{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", fullName='" + fullName + '\'' +
            ", password='" + password + '\'' +
            ", active=" + active +
            ", github='" + github + '\'' +
            ", profileComplete=" + profileComplete +
            '}';
  }
}

