package cv8;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Reader {
    private ArrayList<Student> students;

    public Reader() throws IOException, ClassNotFoundException {
        students = new ArrayList<>();
        try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream("output.txt"))) {
            students = (ArrayList<Student>) reader.readObject();
        }
    }

    public void print() {
        students.stream().forEach(student -> System.out.println(student));
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
}
