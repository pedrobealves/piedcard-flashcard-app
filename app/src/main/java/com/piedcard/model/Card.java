package com.piedcard.model;

public class Card {

    private Long id;
    private String front;
    private String back;
    private Long id_deck;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public Long getId_deck() {
        return id_deck;
    }

    public void setId_deck(Long id_deck) {
        this.id_deck = id_deck;
    }
}
