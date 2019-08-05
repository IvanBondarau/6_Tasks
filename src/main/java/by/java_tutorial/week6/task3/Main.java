package by.java_tutorial.week6.task3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static Scanner in;

    private static void printStudent(Student student) {
        System.out.println("Id: " + student.getId());
        System.out.println(student.getName() + " " + student.getSurname());
        System.out.println("Average score: " + student.getAverageScore());
        System.out.println();
    }

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

    public static void main(String[] args) {
        in = new Scanner(System.in);

        StudentServer server = null;
        try {
            server = new StudentServer(6666);
        } catch (IOException e) {
            System.out.println("Server: connection error");
            e.printStackTrace();
            System.exit(0);
        }

        var service = Executors.newFixedThreadPool(10);

        service.submit(server);

        System.out.println("Server started");

        StudentClient client = new StudentClient();
        try {
            client.startConnection("127.0.0.1", 6666);
        } catch (IOException e) {
            System.out.println("Client: connection error");
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Client started");


        while (true) {
            System.out.println("1. Add new student");
            System.out.println("2. Print all");
            System.out.println("3. Edit student");
            System.out.println("4. Exit");
            int choice = getInt(1, 4);

            if (choice == 1) {

                System.out.println("Enter student name");
                String name = in.nextLine();
                System.out.println("Enter student surname");
                String surname = in.nextLine();
                System.out.println("Enter student average score");
                int score = getInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                client.addStudent(name, surname, score);

            } else if (choice == 2) {
                ArrayList<Student> students = client.getAll();

                for (var student: students) {
                    printStudent(student);
                }

            } else if (choice == 3) {

                System.out.println("Enter student id");
                int id = getInt(0, Integer.MAX_VALUE);
                Student student = client.getStudent(id);

                System.out.println();
                if (student == null) {
                    System.out.println("Student not found");
                } else {
                    printStudent(student);

                    System.out.println("Enter new name");
                    String newName = in.nextLine();
                    System.out.println("Enter new surname");
                    String newSurname = in.nextLine();
                    System.out.println("Enter new average score");
                    Integer score = getInt(Integer.MIN_VALUE, Integer.MAX_VALUE);

                    client.editStudent(student.getId(), newName, newSurname, score);
                }


            } else if (choice == 4) {
                try {
                    client.sendMessage("finish");
                    client.stopConnection();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;

            }

        }

    }

}
