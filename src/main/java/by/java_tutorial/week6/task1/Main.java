package by.java_tutorial.week6.task1;

import by.java_tutorial.week6.task1.book.Book;
import by.java_tutorial.week6.task1.book.Ebook;
import by.java_tutorial.week6.task1.user.User;
import by.java_tutorial.week6.task1.user.UserDatabase;
import org.mindrot.jbcrypt.BCrypt;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner in;

    public static int getInt(int min, int max) {

        String choice = in.nextLine();
        try {
            int res =  Integer.parseInt(choice);
            if (res < min || res > max) {
                throw new NumberFormatException("Invalid number");
            }
            return res;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: try again");
            return getInt(min, max);
        }
    }

    public static void printBooks(ArrayList<Book> books) {
        int index = 0;
        while (true) {
            for (int i = index; i < Integer.min(books.size(), index+4); i++) {
                System.out.println(books.get(i));
            }

            System.out.println("1 - next page, 2 - previous page, 3 - exit");
            int choice = getInt(1, 3);
            if (choice == 1) {
                index += 4;
                if (index >= books.size()) {
                    index -= 4;
                }
            } else if (choice == 2) {
                index = Integer.max(0, index - 4);
            } else {
                return;
            }
        }
    }

    public static User getNewUser(boolean admin) {
        System.out.println("Enter email: ");
        String email = in.nextLine();
        System.out.println("Enter login: ");
        String login = in.nextLine();
        System.out.println("Enter password: ");
        String password = in.nextLine();

        return new User(login, email, BCrypt.hashpw(password, BCrypt.gensalt()), admin);

    }

    public static void findBook(Catalogue books) {
        System.out.println("1. By id");
        System.out.println("2. By title");
        System.out.println("3. By author");
        System.out.println("4. By publisher");
        System.out.println("5. By year of publishing");
        int choice2 = getInt(1, 5);

        if (choice2 == 1) {
            System.out.print("Enter id:");
            int id = getInt(0, Integer.MAX_VALUE);
            Book book = books.findBookById(id);
            System.out.println(book == null ? "Book is not found" : book);

        } else if (choice2 == 2) {
            System.out.print("Enter title:");
            String title = in.nextLine();
            var filtered = books.filterByTitle(title);
            printBooks(filtered);

        } else if (choice2 == 3) {
            System.out.print("Enter author:");
            String author = in.nextLine();
            var filtered = books.filterByAuthor(author);
            printBooks(filtered);

        }  else if (choice2 == 4) {
            System.out.print("Enter publisher:");
            String publisher = in.nextLine();
            var filtered = books.filterByPublisher(publisher);
            printBooks(filtered);

        } else if (choice2 == 5) {
            System.out.print("Enter range start year and finish year:");
            int startYear = in.nextInt();
            int finishYear = in.nextInt();
            in.nextLine();

            var filtered = books.filterByYear(startYear, finishYear);
            printBooks(filtered);
        }
    }


    public static void addNewBook(Catalogue books) {

        System.out.println("Adding new book...");
        System.out.print("Title: ");
        String title = in.nextLine();
        System.out.print("Author: ");
        String author = in.nextLine();
        System.out.print("Publisher: ");
        String publisher = in.nextLine();
        System.out.print("Year of publishing: ");
        int year = in.nextInt();
        in.nextLine();

        Book book;
        System.out.println("Is it e-book? (1 - yes, 2 - no)");
        int choice3 = getInt(1, 2);
        if (choice3 == 1) {
            System.out.println("Enter web location of this book (URL, website, etc.)");
            String location = in.nextLine();
            book = new Ebook(0, title, author, publisher, year, location);
        } else {
            book = new Book(0, title, author, publisher, year);
        }

        books.addBook(book);
    }

    public static void removeBook(Catalogue books) {

        System.out.println("Enter book id: ");
        int id = getInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (books.removeBook(id)) {
            System.out.println("Successful");
        } else {
            System.out.println("Error: book is not found");
        }
    }


    public static void main(String[] args) {
        in = new Scanner(System.in);

        UserDatabase users = new UserDatabase();
        Catalogue books = new Catalogue();

        boolean logged = false;
        User user = null;

        while (!logged) {
            System.out.println("1. Register new user");
            System.out.println("2. Register new admin");
            System.out.println("3. Login");
            int choice = getInt(1, 3);

            if (choice == 1) {

                User newUser = getNewUser(false);

                if (!users.checkLogin(newUser.getLogin())) {
                    System.out.println("Sorry: this login has already been taken");
                    System.out.println("Registration cancelled");
                } else {
                    users.addNewUser(newUser);
                }

            } else if (choice == 2) {
                System.out.println("Enter master password: ");
                String masterPassword = in.nextLine();
                if (users.loginAsMaster(masterPassword)) {
                    System.out.println("OK\n");

                    User newUser = getNewUser(true);

                    if (!users.checkLogin(newUser.getLogin())) {
                        System.out.println("Sorry: this login has already been taken");
                        System.out.println("Registration cancelled");
                    } else {
                        users.addNewUser(newUser);
                    }
                }
            } else {
                System.out.println("Enter login: ");
                String login = in.nextLine();
                System.out.println("Enter password: ");
                String password = in.nextLine();
                user = users.login(login, password);
                if (user == null) {
                    System.out.println("Failed");
                } else {
                    System.out.println("Ok, logged");
                    logged = true;
                }
            }

            System.out.println("Press enter to continue...");
            in.nextLine();
        }


        boolean exit = false;

        if (user.isAdmin()) {

            while (!exit) {
                System.out.println("Menu");
                System.out.println("1. Find book");
                System.out.println("2. List all books");
                System.out.println("3. Add new book");
                System.out.println("4. Remove book");
                System.out.println("5. Exit");
                int choice = getInt(1, 5);

                if (choice == 1) {

                    findBook(books);

                } else if (choice == 2) {

                    printBooks(books.filterByYear(Integer.MIN_VALUE, Integer.MAX_VALUE));

                } else if (choice == 3) {

                    addNewBook(books);

                } else if (choice == 4) {

                    removeBook(books);

                } else if (choice == 5) {

                    exit = true;
                }
            }
        } else {

            while (!exit) {

                System.out.println("Menu");
                System.out.println("1. Find book");
                System.out.println("2. List all books");
                System.out.println("3. Exit");
                int choice = getInt(1, 3);

                if (choice == 1) {

                    findBook(books);

                } else if (choice == 2) {

                    printBooks(books.filterByYear(Integer.MIN_VALUE, Integer.MAX_VALUE));

                } else if (choice == 3) {

                    exit = true;

                }
            }
        }

        user.logout();

        users.saveToFile();
        books.saveToFile();

    }
}
