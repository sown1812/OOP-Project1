package com.example.project1.OOP;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class KOL {
    private final StringProperty username;
    private final IntegerProperty followers;
    private final StringProperty link;
    private final IntegerProperty ranking;

    public KOL(String username, int followers, String link, int ranking) {
        this.username = new SimpleStringProperty(username);
        this.followers = new SimpleIntegerProperty(followers);
        this.link = new SimpleStringProperty(link);
        this.ranking = new SimpleIntegerProperty(ranking);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public int getFollowers() {
        return followers.get();
    }

    public IntegerProperty followersProperty() {
        return followers;
    }

    public String getLink() {
        return link.get();
    }

    public StringProperty linkProperty() {
        return link;
    }

    public int getRanking() {
        return ranking.get();
    }

    public IntegerProperty rankingProperty() {
        return ranking;
    }
}
