package models;

import java.util.Date;

public class Exam {
    private int id;
    private String name;
    private int difficulty;
    private Date modifiedDate;
    private Date createdDate;

    private Category category;
    public Exam() {
    }

//    public Exam(int id, String name, int difficulty, int categoryId, Date modifiedDate, Date createdDate) {
    public Exam(int id, String name, int difficulty, Category category) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        return "Exam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", modifiedDate=" + modifiedDate +
                ", createdDate=" + createdDate +
                ", category=" + category +
                '}';
    }
}
