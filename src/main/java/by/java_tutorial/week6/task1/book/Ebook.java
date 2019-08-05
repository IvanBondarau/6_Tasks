package by.java_tutorial.week6.task1.book;

public class Ebook extends Book {

    private String location;

    public Ebook(int id, String name, String author, String publisher, int yearOfPublishing, String location) {
        super(id, name, author, publisher, yearOfPublishing);

        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "E-Book" + "\n" +
                "ID: " + getId() + '\n' +
                "Title: " + getTitle() + '\n' +
                "Author: " + getAuthor() + '\n' +
                "Publisher: " + getPublisher() + '\n' +
                "Year of publishing: " + getYearOfPublishing() + '\n' +
                "Location: " + location + "\n";
    }
}
