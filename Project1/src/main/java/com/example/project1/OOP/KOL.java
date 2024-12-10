package com.example.project1.OOP;

public class KOL {
    private String username;
    private int followers;
    private String link;
    private int ranking;

    public KOL(String username, int followers, String link, int ranking) {
        this.username = username;
        this.followers = followers;
        this.link = link;
        this.ranking = ranking;
    }

    // Getters and setters for all properties
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}