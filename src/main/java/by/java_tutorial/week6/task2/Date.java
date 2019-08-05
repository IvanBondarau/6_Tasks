package by.java_tutorial.week6.task2;

import java.util.Objects;

public class Date {
    int day;
    int month;
    int year;

    private static int monthLength[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final Date DEFAULT = new Date(1, 1, 1970);

    public static boolean validateDate(int day, int month, int year) {

        if (year < 0 || month < 1 || day < 1) {
            return false;
        }

        int leapYear = 0;
        if (year % 400 == 0 || (year % 4 == 1 && year % 100 != 0)) {
            leapYear = 1;
        }

        if (month > 12) {
            return false;
        }

        int daysInMonth = monthLength[month - 1] + (month == 2 ? leapYear : 0);

        if (day <= daysInMonth) {
            return true;
        } else {
            return false;
        }
    }

    public static Date parse(String date) {

        if (date.matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d")) {

            int day = Integer.parseInt(date.substring(0, 2));
            int month = Integer.parseInt(date.substring(3, 5));
            int year = Integer.parseInt(date.substring(6));

            if (Date.validateDate(day, month, year)) {
                return new Date(day, month, year);
            } else {
                return null;
            }

        } else {
            return null;
        }
    }


    public Date(int day, int month, int year) {

        if (!Date.validateDate(day, month, year)) {
            this.day = DEFAULT.day;
            this.month = DEFAULT.month;
            this.year = DEFAULT.year;
        } else {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    public boolean set(int day, int month, int year) {

        if (!Date.validateDate(day, month, year)) {
            this.day = DEFAULT.day;
            this.month = DEFAULT.month;
            this.year = DEFAULT.year;
            return false;
        } else {
            this.day = day;
            this.month = month;
            this.year = year;
            return true;
        }
    }

    public boolean set(String date) {
        Date parsed = Date.parse(date);
        if (parsed == null) {
            this.day = DEFAULT.day;
            this.month = DEFAULT.month;
            this.year = DEFAULT.year;
            return false;
        } else {
            this.day = parsed.day;
            this.month = parsed.day;
            this.year = parsed.year;
            return true;
        }

    }

    @Override
    public String toString() {
        return String.format("%02d.%02d.%04d", this.day, this.month, this.year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return day == date.day &&
                month == date.month &&
                year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    public boolean greaterOrEqual(Date date) {
        if (this.year > date.year) {
            return true;
        }
        if (this.year < date.year) {
            return false;
        }

        if (this.month > date.month) {
            return true;
        }
        if (this.month < date.month) {
            return false;
        }

        if (this.day < date.day) {
            return false;
        }

        return true;
    }

    public int compareTo(Date date) {

        if (this.year > date.year) {
            return 1;
        }
        if (this.year < date.year) {
            return -1;
        }

        if (this.month > date.month) {
            return 1;
        }
        if (this.month < date.month) {
            return -1;
        }

        if (this.day > date.day) {
            return 1;
        }

        if (this.day < date.day) {
            return -1;
        }

        return 0;
    }
}
