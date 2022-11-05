package com.example.raftaarquiz.Model;

public class AvailableTestSeries {
    String id;
    String title;
    String category;
    String Image;
    String total;

    public AvailableTestSeries(String id, String title, String category, String image, String total) {
        this.id = id;
        this.title = title;
        this.category = category;
        Image = image;
        this.total = total;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
