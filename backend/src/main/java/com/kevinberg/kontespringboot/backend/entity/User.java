package com.kevinberg.kontespringboot.backend.entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

//entity must have ID attribute~
@Entity
public class User {

    //NotBlank for Strings and NotNull for ints and everything else.
    @Id
    @NotBlank
    @Size(max = 128)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 128)
    private String password;


    @NotNull
    private boolean enabled;

    //eager = load everything asap
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;


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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
