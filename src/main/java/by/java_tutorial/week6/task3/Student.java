package by.java_tutorial.week6.task3;

public class Student {

    private int id;
    private String name;
    private String surname;
    private int averageScore;

    public Student(int id, String name, String surname, int averageScore) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.averageScore = averageScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return getId() + "###" + getName() + "###" + getSurname() + "###" + getAverageScore();
    }
}
