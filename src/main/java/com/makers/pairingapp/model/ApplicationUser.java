package com.makers.pairingapp.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "applicationuser")
public class ApplicationUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;



  //@GeneratedValue(strategy = GenerationType.AUTO)
  //@Column(name="id", insertable = false, updatable = false, nullable = false)
  private UUID imageid;

  //use username as our unique email feed because otherwise spring security throws a fit
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
  private String userProfileImageLink;




  public ApplicationUser() { }

  public Optional<String> getUserProfileImageLink() {
    return Optional.ofNullable(userProfileImageLink);
  }

  public void setUserProfileImageLink(String userProfileImageLink) {
    this.userProfileImageLink = userProfileImageLink;
  }

  public ApplicationUser(@Email @NotEmpty String username, String fullName, @NotEmpty String password,  Boolean active) {
    this.username = username;
    this.fullName = fullName;
    this.password = password;
    this.userProfileImageLink = null; //
    this.imageid = UUID.randomUUID();
    this.active = active;
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



//  NO CONSTRUCTOR????
  private long getId(){
    return id;
  }
  public UUID getimageId() {
    return imageid;
  }

  public UUID getUserProfileId() { return imageid; }


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
            ", fullName='" + fullName + '\'' +
            ", active=" +  active + '\''
            + "imageid=" + imageid +'\''
            + "userprofileimagelink=" + userProfileImageLink +'}';
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ApplicationUser that = (ApplicationUser) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(username, that.username) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(active, that.active) &&
            Objects.equals(imageid, that.imageid) &&
            Objects.equals(userProfileImageLink, that.userProfileImageLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, fullName, active, userProfileImageLink);
  }
}

