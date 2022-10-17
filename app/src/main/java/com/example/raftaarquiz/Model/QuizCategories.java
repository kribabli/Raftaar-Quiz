package com.example.raftaarquiz.Model;

public class QuizCategories {
    String id, title, image, Quiz_start_time, Quiz_close_time, Quiz_date;

    public QuizCategories(String id, String title, String image, String quiz_start_time, String quiz_close_time, String quiz_date) {
        this.id = id;
        this.title = title;
        this.image = image;
        Quiz_start_time = quiz_start_time;
        Quiz_close_time = quiz_close_time;
        Quiz_date = quiz_date;
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

    public String getQuiz_start_time() {
        return Quiz_start_time;
    }

    public void setQuiz_start_time(String quiz_start_time) {
        Quiz_start_time = quiz_start_time;
    }

    public String getQuiz_close_time() {
        return Quiz_close_time;
    }

    public void setQuiz_close_time(String quiz_close_time) {
        Quiz_close_time = quiz_close_time;
    }

    public String getQuiz_date() {
        return Quiz_date;
    }

    public void setQuiz_date(String quiz_date) {
        Quiz_date = quiz_date;
    }
}
