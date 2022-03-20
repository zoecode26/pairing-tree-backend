package com.makers.pairingapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "MATCHES")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp start_time;

    private Boolean complete;

    public Match() {
    }

    public Match(Timestamp start_time, Boolean complete) {
        this.start_time = start_time;
        this.complete = complete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    @ManyToOne
    @JoinColumn(name="user1_id")
    private ApplicationUser user1;

    @ManyToOne
    @JoinColumn(name="user2_id")
    private ApplicationUser user2;

    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public ApplicationUser getUser1() {
        return user1;
    }

    public void setUser1(ApplicationUser user1) {
        this.user1 = user1;
    }

    public ApplicationUser getUser2() {
        return user2;
    }

    public void setUser2(ApplicationUser user2) {
        this.user2 = user2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", start_time=" + start_time +
                ", complete=" + complete +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", language=" + language +
                '}';
    }
}
