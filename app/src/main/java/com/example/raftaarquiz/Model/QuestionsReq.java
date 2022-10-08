package com.example.raftaarquiz.Model;

public class QuestionsReq {
    String id, type, category, question, correct_ans, ans1, ans2, ans3, ans4;
    boolean seleced;

    public QuestionsReq(String id, String type, String category, String question, String correct_ans, String ans1, String ans2, String ans3, String ans4, boolean seleced) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.question = question;
        this.correct_ans = correct_ans;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.seleced = seleced;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public boolean isSeleced() {
        return seleced;
    }

    public void setSeleced(boolean seleced) {
        this.seleced = seleced;
    }
}