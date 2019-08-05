package by.java_tutorial.week6.task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class StudentServer implements Runnable {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private ArrayList<Student> students;

    StudentServer(int port)
        throws IOException {
        serverSocket = new ServerSocket(port);
        students = new ArrayList<>();
    }

    private void addStudent(String name, String surname, int marks) {
        students.add(new Student(students.size(), name, surname, marks));
        System.out.println("Server: added");
    }

    private Student getStudent(int id) {
        for (var student: students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public void run() {
        try {
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Request get");
                String[] requestParams = inputLine.split("###");
                if ("finish".equals(requestParams[0])) {

                    System.out.println("Finishing connection");
                    stop();

                    break;
                } else if (requestParams[0].equals("add")){
                    if (requestParams.length == 4) {
                        addStudent(requestParams[1], requestParams[2], Integer.parseInt(requestParams[3]));
                        out.println("Ok");
                    } else {
                        System.out.println("Error [add]: invalid params number ");
                        System.out.println(inputLine + " " + requestParams.length);
                        out.println("Error###nparams");
                    }
                } else if (requestParams[0].equals("get")) {
                    if (requestParams.length == 2) {
                        int idOfStudent = Integer.parseInt(requestParams[1]);
                        Student student = getStudent(idOfStudent);
                        if (student == null) {
                            System.out.println("Error [get]: invalid id");
                            System.out.println(inputLine + " " + requestParams.length);
                            out.println("Error###invalidid");
                        } else {
                            out.println("Ok###" + student);
                        }

                    } else {
                        System.out.println("Error [get]: invalid params number ");
                        System.out.println(inputLine + " " + requestParams.length);
                        out.println("Error###nparams");
                    }
                } else if (requestParams[0].equals("getall")) {
                    StringBuilder response = new StringBuilder("Ok");
                    for (var student: students) {
                        response.append("###");
                        response.append(student.getId());
                    }
                    out.println(response);
                } else if (requestParams[0].equals("edit")) {

                    Student student = getStudent(Integer.parseInt(requestParams[1]));
                    student.setName(requestParams[2]);
                    student.setSurname(requestParams[3]);
                    student.setAverageScore(Integer.parseInt(requestParams[4]));

                    out.println("Ok");

                } else {
                    System.out.println("Invalid request");
                    System.out.println(inputLine);
                    out.println("Error###invalid");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }


    public void stop()
        throws IOException
    {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

}
