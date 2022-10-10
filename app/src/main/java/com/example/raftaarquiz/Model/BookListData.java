package com.example.raftaarquiz.Model;

public class BookListData {
    String id;
    String title;
    String description;
    String authorname;
    String image;

    public BookListData(String id, String title, String description, String authorname, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorname = authorname;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
