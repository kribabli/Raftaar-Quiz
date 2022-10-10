package com.example.raftaarquiz.Model;

public class LeaderBoard {
    String id, name, image_url, score, rank;

    public LeaderBoard(String id, String name, String image_url, String score, String rank) {
        this.id = id;
        this.name = name;
        this.image_url = image_url;
        this.score = score;
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
