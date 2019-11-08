package cv8;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Main  {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ArrayList<Student> students = new ArrayList<>();

        students.add(new Student("Brendon", "Hartman", 100));
        students.add(new Student("Kaylum", "Goff", 43));
        students.add(new Student("Franco", "Dolan", 65));
        students.add(new Student("Teemo", "Motak", 92));
        students.add(new Student("Honza", "Motacina", 45));

        Writer writer = new Writer(students);
        writer.writeToFile();

        Reader reader = new Reader();
        //reader.print();

        ExerciseClass ec = new ExerciseClass(reader.getStudents());
        //ec.print();
        System.out.println(ec.getMinimalPoints());
        System.out.println(ec.getAveragePoints());
        System.out.println(ec.getAllNames());

        System.out.println(students);
        Collections.sort(students);
        System.out.println(students);
    }
}
