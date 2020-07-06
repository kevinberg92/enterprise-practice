package com.kevinberg.kontespringboot.frontend.controller;


import com.kevinberg.kontespringboot.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

//alot of new spring-security stuff i would not have figured out by myself either.
@Named
@RequestScoped
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    //since its a service you create new ones instead of going through entity....(?)

    //can apparently have getters and setters here, but not import entities...
    private String username;

    private String password;

    //sign up new user, if successful redirect to home page
    //successful also log in the user.
    //save the new info to database
    public String signUpUser() {
        boolean registered = false;
        try {
            registered = userService.createUserWithPassword(username,password);
        } catch(Exception e) {
            return "error in SignUpController";
        }

        if(registered) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    password,
                    userDetails.getAuthorities());

            authenticationManager.authenticate(token);

            if(token.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(token);
            }
            //telling it to redirect to index e.g homepage if above goes true
            return "/index.jsf?faces-redirect=true";
        } else {
            //redirect to signup if not goes through, not sure about error=true..
            return "/signup.jsf?faces-redirect=true&error=true";
        }
    }

             //HOW NOT TO DO
    /*
     public boolean signUpAndLoginUser(String username, String password) {
         userService.createUserWithPassword(username,password);
         if(signUpAndLoginUser(username, password)) {
             //redirect
         }
     }

    */

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
}
