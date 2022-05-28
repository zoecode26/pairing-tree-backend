package com.makers.pairingapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "language_preferences")
public class LanguagePreference implements Comparable<LanguagePreference>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int skill;

    @ManyToOne
    @JoinColumn(name="user_id")
    private ApplicationUser user;

    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;

    public LanguagePreference() {
    }

    public LanguagePreference(int skill, ApplicationUser user, Language language) {
        this.skill = skill;
        this.user = user;
        this.language = language;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguagePreference that = (LanguagePreference) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LanguagePreference{" +
                "id=" + id +
                ", skill='" + skill + '\'' +
                ", user=" + user +
                ", language=" + language +
                '}';
    }

    @Override
    public int compareTo(LanguagePreference o) {
        System.out.println("Using custom comparison");
        if (o.getSkill() > this.getSkill()) {
            return -1;
        } else if (o.getSkill() < this.getSkill()) {
            return 1;
        }

        return 0;
    }
}
