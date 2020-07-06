package com.kevinberg.kontespringboot.backend.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class MatchStats {

    //is id always Long? when is it 'long' ??
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @OneToOne
    private User user;

    //when to use Integer over int?
    @Min(0)
    @NotNull
    private int victories = 0;

    @Min(0)
    @NotNull
    private int defeats = 0;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userStats) {
        this.user = userStats;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }
}
