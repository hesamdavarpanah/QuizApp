package models;

import java.util.Date;

public class Answer {
    private int id;
    private String answerText;
    private Question question;
    private boolean isTrue;
    private Date modifiedDate;
    private Date createdDate;

    public Answer() {
    }

    public Answer(int id, String answerText, Question question, boolean isTrue, Date modifiedDate, Date createdDate) {
        this.id = id;
        this.answerText = answerText;
        this.question = question;
        this.isTrue = isTrue;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
