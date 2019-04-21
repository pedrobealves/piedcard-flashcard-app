package com.piedcard.model;

import java.io.Serializable;

public class Deck implements Serializable {

    private Long id;
    private String name;
    private String description;

    public Deck(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Deck(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
