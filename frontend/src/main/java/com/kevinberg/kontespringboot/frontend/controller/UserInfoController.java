package com.kevinberg.kontespringboot.frontend.controller;


import com.kevinberg.kontespringboot.backend.entity.MatchStats;
import com.kevinberg.kontespringboot.backend.services.MatchStatsService;
import com.kevinberg.kontespringboot.backend.services.UserService;
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
