package by.java_tutorial.week6.task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class StudentClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port)
        throws IOException
    {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg)
        throws IOException
    {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public boolean addStudent(String name, String surname, int marks) {
        String response;
        String[] responseParams;
        try {
            response = sendMessage("add###"+name+"###"+surname+"###"+marks);

            responseParams = response.split("###");
            if (responseParams[0].equals("Ok")) {
                System.out.println("Ok, added");
                return true;
            } else {
                System.out.println("Error while adding student: " + responseParams[1]);
                return false;
            }
        } catch (IOException e) {
            System.out.println("Adding error: ");
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Student> getAll() {
        String response;
        String[] responseParams;
        Integer[] studentId;
        try {
            response = sendMessage("getall");
            responseParams = response.split("###");
            ArrayList<Student> students = new ArrayList<>();
            for (int i = 1; i < responseParams.length; i++) {
                Student student = getStudent(Integer.parseInt(responseParams[i]));
                if (student != null) {
                    students.add(student);
                }
            }
            return students;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Student getStudent(int id) {
        String response;
        String[] responseParams;
        try {
            response = sendMessage("get###" + id);
            responseParams = response.split("###");

            if (responseParams[0].equals("Ok")) {
                Student student = new Student(Integer.parseInt(responseParams[1]), responseParams[2],
                        responseParams[3], Integer.parseInt(responseParams[4]));
                return student;
            } else {
                System.out.println("Error while adding student: " + responseParams[1]);
                return null;
            }
        } catch (IOException e) {
            System.out.println("Getting error: ");
            e.printStackTrace();
            return null;
        }
    }

    public boolean editStudent(int id, String name, String surname, int score) {
        String response;
        String[] responseParams;
        try {
            response = sendMessage("edit###"+id + "###" + name + "###" + surname + "###" + score);

            responseParams = response.split("###");
            if (responseParams[0].equals("Ok")) {
                System.out.println("Ok, edited");
                return true;
            } else {
                System.out.println("Error while editing student: " + responseParams[1]);
                return false;
            }
        } catch (IOException e) {
            System.out.println("Editing error: ");
            e.printStackTrace();
            return false;
        }
    }

    public void stopConnection()
        throws IOException
    {
        in.close();
        out.close();
        clientSocket.close();
    }
}