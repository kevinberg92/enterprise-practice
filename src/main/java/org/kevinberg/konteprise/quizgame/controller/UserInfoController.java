package org.kevinberg.konteprise.quizgame.controller;


import org.kevinberg.konteprise.quizgame.entity.MatchStats;
import org.kevinberg.konteprise.quizgame.services.MatchStatsService;
import org.kevinberg.konteprise.quizgame.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class UserInfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private MatchStatsService matchStatsService;


    public String getUserName() {
        //no way in hell I would have figured this one out.
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public MatchStats getMatchStats() {
        return matchStatsService.getMatchStats(getUserName());
    }
}
