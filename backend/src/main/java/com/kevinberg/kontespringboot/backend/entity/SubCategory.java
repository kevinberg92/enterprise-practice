package com.kevinberg.kontespringboot.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class SubCategory {


    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max=128)
    private String name;

    @ManyToOne
    @NotNull
    private Category parent;


    public SubCategory() {}


    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}