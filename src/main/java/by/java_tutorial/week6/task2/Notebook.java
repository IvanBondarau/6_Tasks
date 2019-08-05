package by.java_tutorial.week6.task2;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Notebook {

    private ArrayList<Note> notes;
    private ArrayList<Note> selectedNotes;
    private File saveFile;
    private static String DELIMITER = "############################";

    public Notebook() {
        notes = new ArrayList<>();

        saveFile = new File("notes.dtb") ;

        if (saveFile.exists() && saveFile.canRead()) {

            try (Scanner in = new Scanner(new FileReader(saveFile))) {

                while (in.hasNextLine()) {
                    String topic = in.nextLine();
                    String date = in.nextLine();
                    String email = in.nextLine();
                    StringBuilder text = new StringBuilder("");
                    String nextLine;
                    while (in.hasNextLine() && !(nextLine = in.nextLine()).equals(DELIMITER)) {
                        text.append(nextLine);
                        text.append("\n");
                    }

                    String textStr = text.toString();
                    while (textStr.endsWith("\n")) {
                        textStr = textStr.substring(0, textStr.length() - 1);
                    }

                    notes.add(new Note(topic, date, email, textStr));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveToFile() {
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (saveFile.setWritable(true)) {
            try (PrintWriter out = new PrintWriter(saveFile)) {
                for (var note : notes) {
                    out.println(note.getTopic());
                    out.println(note.getDate());
                    out.println(note.getEmail());
                    out.println(note.getText());
                    out.println(DELIMITER);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Unable to save file");
        }
    }

    public int numOfNotes() {
        return notes.size();
    }

    public int numOfSelected() {
        return selectedNotes == null ? 0 : selectedNotes.size();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void filter(NoteMatcher matcher) {
        if (selectedNotes == null) {
            selectedNotes = notes;
        }

        ArrayList<Note> filteredNotes = new ArrayList<>();
        for (var note : selectedNotes) {
            if (matcher.match(note)) {
                filteredNotes.add(note);
            }
        }

        selectedNotes = filteredNotes;

    }

    public void sortSelected(Comparator<Note> comparator) {
        Collections.sort(selectedNotes, comparator);
    }

    public void printSelected() {
        for (var note: selectedNotes) {
            System.out.println(note);
            System.out.println(DELIMITER);
        }
    }

    public void flush() {
        selectedNotes = null;
    }

    public void printAll() {
        for (var note: notes) {
            System.out.println(note);
            System.out.println(DELIMITER);
        }

    }


}
