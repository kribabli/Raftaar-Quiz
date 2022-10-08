package com.example.raftaarquiz.Model;

public class QuizCategories {
    String id, title, image;

    public QuizCategories(String id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
