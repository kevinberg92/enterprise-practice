package org.kevinberg.konteprise.quizgame.services;


import org.kevinberg.konteprise.quizgame.entity.MatchStats;
import org.kevinberg.konteprise.quizgame.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

//counts as stateless? so cant have fields.
@Service
@Transactional
public class MatchStatsService {
    //new victory
    //new defeat
    //get stats for X user

    @Autowired
    private EntityManager em;


    public void reportVictory(String username) {

        MatchStats match = getMatchStats(username);

        match.setVictories(1 + match.getVictories());
    }

    public void reportDefeat(String username) {

        MatchStats match = getMatchStats(username);
        match.setDefeats(match.getDefeats() +1);
    }


    public MatchStats getMatchStats(String username) {

        TypedQuery<MatchStats> query = em.createQuery(
                "select m from MatchStats m where m.user.username=?1", MatchStats.class);
        query.setParameter(1, username);

        List<MatchStats> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        }

        User user = em.find(User.class, username);
        if (user == null) {
            throw new IllegalArgumentException("No existing user named: " + username);
        }

        MatchStats match = new MatchStats();
        match.setUser(user);
        em.persist(match);

        return match;
    }


}
