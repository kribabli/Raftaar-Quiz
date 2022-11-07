package com.example.raftaarquiz.Model;

public class MockTestPOJO {
    String id;
    String title;
    String availableTS;
    String questionTime;
    String totalQuestion;
    String marks;

    public MockTestPOJO(String id, String title, String availableTS, String questionTime, String totalQuestion, String marks) {
        this.id = id;
        this.title = title;
        this.availableTS = availableTS;
        this.questionTime = questionTime;
        this.totalQuestion = totalQuestion;
        this.marks = marks;
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

    public String getAvailableTS() {
        return availableTS;
    }

    public void setAvailableTS(String availableTS) {
        this.availableTS = availableTS;
    }

    public String getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }

    public String getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
