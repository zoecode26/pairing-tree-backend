package com.makers.pairingapp.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name  = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

//    @OneToMany(mappedBy = "user")
//    private List<Availability> availabilities;
//
//    public List<Availability> getAvailabilities() {
//        return availabilities;
//    }
//
//    public void setAvailabilities(List<Availability> availabilities) {
//        this.availabilities = availabilities;
//    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
