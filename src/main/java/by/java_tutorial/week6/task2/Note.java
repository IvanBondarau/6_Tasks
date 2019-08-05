package by.java_tutorial.week6.task2;

import java.io.Serializable;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Note  implements Serializable {

    private String topic;
    private Date date;
    private String email;
    private String text;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static Comparator<Note> topicComparator = new Comparator<Note>() {

        public int compare(Note note1, Note note2) {
            return note1.getTopic().compareTo(note2.getTopic());
        }
    };

    public static Comparator<Note> dateComparator = new Comparator<Note>() {

        public int compare(Note note1, Note note2) {
            return note1.getDate().compareTo(note2.getDate());
        }
    };

    public static Comparator<Note> emailComparator = new Comparator<Note>() {

        public int compare(Note note1, Note note2) {
            return note1.getEmail().compareTo(note2.getEmail());
        }
    };

    public Note() {
        setTopic("");
        setDate("");
        setEmail("");
        setText("");
    }

    public Note(String topic, String date, String email, String text) {
        setTopic(topic);
        setDate(date);
        setEmail(email);
        setText(text);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getDate() {
        return date;
    }

    public boolean setDate(String date) {
        Date parsed = Date.parse(date);
        if (parsed == null) {
            this.date = Date.DEFAULT;
            return false;
        } else {
            this.date = parsed;
            return true;
        }
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        if (validate(email)) {
            this.email = email;
            return true;
        } else {
            this.email = "";
            return false;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note\n" +
                "Topic: " + topic + "\n" +
                "Date: " + date + "\n" +
                "email: " + email + "\n" +
                text + "\n";
    }

}
