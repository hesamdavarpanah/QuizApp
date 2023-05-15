package models;


import java.util.Date;

public class UserExam {
    private int id;
    private User user;
    private Exam exam;
    private Date examDate;
    private int result;
    private String description;
    private Date modifiedDate;
    private Date createdDate;

    public UserExam() {
    }

    public UserExam(User user, Exam exam, Date examDate, int result, String description) {
        this.user = user;
        this.exam = exam;
        this.examDate = examDate;
        this.result = result;
        this.description = description;
    }

    public UserExam(int id, User user, Exam exam, Date examDate, int result, String description, Date modifiedDate, Date createdDate) {
        this.id = id;
        this.user = user;
        this.exam = exam;
        this.examDate = examDate;
        this.result = result;
        this.description = description;
        this.modifiedDate = modifiedDate;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return String.format("%s\t%s\t%d", getExamDate(), getExam().getName(), getResult());
    }
}
