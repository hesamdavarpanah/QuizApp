package models;

import java.util.Date;

public class Question {
    private int id;
    private String questionText;
    private int point;
    private Exam exam;
    private Date modifiedDate;
    private Date createdDate;

    public Question() {
    }

//    public Question(int id, String questionText, int point, Exam exam, Date modifiedDate, Date createdDate) {
    public Question(int id, String questionText, int point, Exam exam) {
        this.id = id;
        this.questionText = questionText;
        this.point = point;
        this.exam = exam;
//        this.modifiedDate = modifiedDate;
//        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", point=" + point +
                ", exam=" + exam +
                ", modifiedDate=" + modifiedDate +
                ", createdDate=" + createdDate +
                '}';
    }
}
