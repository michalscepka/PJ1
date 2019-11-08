package cv8;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ExerciseClass {
    private ArrayList<Student> students;

    public ExerciseClass(ArrayList<Student> students) {
        this.students = students;
    }

    public void print() {
        students.stream().forEach(student -> System.out.println(student));
    }

    public double getMinimalPoints() {
        int min = students.get(0).getPoints();
        for (Student student : students) {
            if (min > student.getPoints()) {
                min = student.getPoints();
            }
        }
        return min;
    }

    public double getAveragePoints() {
        return students.stream().collect(Collectors.averagingInt(Student::getPoints));
    }

    public String getAllNames() {
        return students.stream().map(Student::getFullName).collect(Collectors.joining(", "));
    }
}
