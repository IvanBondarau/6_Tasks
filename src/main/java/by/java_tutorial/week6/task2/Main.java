package by.java_tutorial.week6.task2;

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

    public static Note getNote() {
        Note note = new Note();
        System.out.println("Enter note topic: ");
        String topic = in.nextLine();
        note.setTopic(topic);

        System.out.println("Enter date: ");
        String date = in.nextLine();
        while (!note.setDate(date)) {
            System.out.println("Invalid input. Please, try again");
            date = in.nextLine();
        }

        System.out.println("Enter email: ");
        String email = in.nextLine();
        while (!note.setEmail(email)) {
            System.out.println("Invalid input. Please, try again");
            email = in.nextLine();
        }

        System.out.println("Enter note text. To finish enter ###");
        StringBuilder text = new StringBuilder("");
        String newLine = "";
        while (!(newLine = in.nextLine()).equals("###")) {
            text.append(newLine);
            text.append("\n");
        }

        String textStr = text.toString();
        while (textStr.endsWith("\n")) {
            textStr = textStr.substring(0, textStr.length() - 1);
        }

        note.setText(textStr);

        return note;
    }

    public static void filter(Notebook notebook) {
        NoteMatcher matcher = new NoteMatcher();

        System.out.println("Enter topic: (leave empty to skip)");
        String topic = in.nextLine();
        matcher.setTopic(topic);

        System.out.println("Enter minimum note date: (leave empty to skip)");
        String minDate = in.nextLine();
        while (!minDate.equals("") && Date.parse(minDate) == null) {
            System.out.println("Invalid input! Please, try again");
            minDate = in.nextLine();
        }
        matcher.setMinDate(Date.parse(minDate));

        System.out.println("Enter maximum note date: (leave empty to skip)");
        String maxDate = in.nextLine();
        while (!maxDate.equals("") && Date.parse(maxDate) == null) {
            System.out.println("Invalid input! Please, try again");
            maxDate = in.nextLine();
        }
        matcher.setMaxDate(Date.parse(maxDate));

        System.out.println("Enter email: (leave empty to skip)");
        String email = in.nextLine();
        while (!email.equals("") && !Note.validate(email)) {
            System.out.println("Invalid input! Please, try again");
            email = in.nextLine();
        }
        matcher.setEmail(email);

        System.out.println("Enter the words that should be in the text through the gap (leave empty to skip)");
        String words = in.nextLine();
        matcher.setWords(words);

        notebook.filter(matcher);
    }

    public static void sort(Notebook notebook) {
        System.out.println("Enter sort parameter");
        System.out.println("1. Topic");
        System.out.println("2. Date");
        System.out.println("3. Email");
        int choice = getInt(1, 3);

        if (choice == 1) {
            notebook.sortSelected(Note.topicComparator);
        } else if (choice == 2) {
            notebook.sortSelected(Note.dateComparator);
        } else if (choice == 3) {
            notebook.sortSelected(Note.emailComparator);
        }
    }

    public static void main(String[] args) {
        in = new Scanner(System.in);

        Notebook notebook = new Notebook();

        boolean exit = false;
        while (!exit) {
            System.out.println("Total notes: " + notebook.numOfNotes());
            System.out.println("1. Add new note");
            System.out.println("2. Filter");
            System.out.println("3. Print all");
            System.out.println("4. Exit");
            int choice = getInt(1, 4);

            if (choice == 1) {
                Note note = getNote();
                notebook.addNote(note);

            } else if (choice == 2) {

                int choice2 = 1;

                do {
                    if (choice2 == 1) {
                        filter(notebook);
                    } else if (choice2 == 2) {
                        sort(notebook);
                    } else if (choice2 == 3) {
                        notebook.printSelected();
                    }

                    System.out.println();
                    System.out.println("Selected: " + notebook.numOfSelected());
                    System.out.println("1. New filter");
                    System.out.println("2. Sort filtered");
                    System.out.println("3. Print filtered");
                    System.out.println("4. Flush");
                    choice2 = getInt(1, 4);

                } while (choice2 != 4);

                notebook.flush();

            } else if (choice == 3) {
                notebook.printAll();
            } else {
                exit = true;
            }
        }

        notebook.saveToFile();

    }
}
