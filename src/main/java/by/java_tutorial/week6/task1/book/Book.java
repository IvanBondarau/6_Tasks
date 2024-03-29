package by.java_tutorial.week6.task1.book;

import java.io.Serializable;

public class Book implements Serializable {

    private transient int id;
    private String title;
    private String author;
    private String publisher;
    private int yearOfPublishing;

    public Book(int id, String title, String author, String publisher, int yearOfPublishing) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearOfPublishing = yearOfPublishing;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    @Override
    public String toString() {
        return "Book" + "\n" +
                "ID: " + id + '\n' +
                "Title: " + title + '\n' +
                "Author: " + author + '\n' +
                "Publisher: " + publisher + '\n' +
                "Year of publishing: " + yearOfPublishing + '\n';
    }

}
