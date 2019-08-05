package by.java_tutorial.week6.task1.user;

import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDatabase {

    private ArrayList<User> users;
    private String masterPassword;
    private File saveFile;

    public UserDatabase() {

        users = new ArrayList<User>();
        saveFile = new File("users.dtb");

        if (saveFile.exists() && saveFile.canRead()) {

            try (Scanner in = new Scanner(new FileReader(saveFile))) {

                masterPassword = in.nextLine();

                while (in.hasNextLine()) {
                    String login = in.nextLine();
                    String email = in.nextLine();
                    String passwordHash = in.nextLine();
                    String admin = in.nextLine();
                    boolean isAdmin = false;
                    if (admin.equals("y")) {
                        isAdmin = true;
                    }

                    User user = new User(login, email, passwordHash, isAdmin);
                    users.add(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (masterPassword == null) {
                    masterPassword = BCrypt.hashpw("1111", BCrypt.gensalt());
                }
            }


        } else {
            masterPassword = BCrypt.hashpw("1111", BCrypt.gensalt());
        }
    }


    public void saveToFile() {
        if (!saveFile.exists()) {
            try{
                saveFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (saveFile.setWritable(true)) {
            try (PrintWriter out = new PrintWriter(saveFile)) {
                out.println(masterPassword);

                for (var user:users) {
                    out.print(user.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public boolean loginAsMaster(String password) {
        return BCrypt.checkpw(password, masterPassword);
    }

    public boolean checkLogin(String login) {
        for (var user: users) {
            if (user.getLogin().equals(login)) {
                return false;
            }
        }
        return true;
    }

    public boolean addNewUser(User newUser) {
        for (var user: users) {
            if (user.getLogin().equals(newUser.getLogin())) {
                return false;
            }
        }
        users.add(newUser);
        return true;
    }


    public User login(String login, String password) {
        for (var user: users) {
            if (user.login(login, password)) {
                return user;
            }
        }
        return null;
    }

}
