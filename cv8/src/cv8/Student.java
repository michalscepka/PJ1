package cv8;

import java.io.Serializable;

public class Student implements Serializable, Comparable<Student> {
    private String name;
    private String surname;
    private int points;

    public Student(String name, String surname, int points) {
        this.name = name;
        this.surname = surname;
        this.points = points;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return name + " " + surname + ": " + points;
    }

    @Override
    public int compareTo(Student student) {
        return this.getPoints() - student.getPoints();
    }
}
