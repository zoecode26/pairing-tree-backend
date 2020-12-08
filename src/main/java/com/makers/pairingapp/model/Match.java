package com.makers.pairingapp.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "MATCHES")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user1_id;

    private Long user2_id;

    private Timestamp start_time;

    public Match() {
    }

    public Match(Long user1_id, Long user2_id, Timestamp start_time) {
        this.user1_id = user1_id;
        this.user2_id = user2_id;
        this.start_time = start_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="user1_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name="user2_id")
    private User user2;

//    public Long getUser1_id() {
//        return user1_id;
//    }
//
//    public void setUser1_id(Long user1_id) {
//        this.user1_id = user1_id;
//    }
//
//    public Long getUser2_id() {
//        return user2_id;
//    }
//
//    public void setUser2_id(Long user2_id) {
//        this.user2_id = user2_id;
//    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
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
                ", user1_id=" + user1_id +
                ", user2_id=" + user2_id +
                ", start_time=" + start_time +
                '}';
    }
}
