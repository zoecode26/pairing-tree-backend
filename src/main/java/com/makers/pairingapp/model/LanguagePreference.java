package com.makers.pairingapp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "language_preferences")
public class LanguagePreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer skill;

    @ManyToOne
    @JoinColumn(name="user_id")
    private ApplicationUser user;

    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;

    public LanguagePreference() {
    }

    public LanguagePreference(Integer skill, ApplicationUser user, Language language) {
        this.skill = skill;
        this.user = user;
        this.language = language;
    }

    public Integer getSkill() {
        return skill;
    }

    public void setSkill(Integer skill) {
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
}
