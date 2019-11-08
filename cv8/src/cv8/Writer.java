package cv8;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Writer {
    ArrayList<Student> students;

    public Writer(ArrayList<Student> students) {
        this.students = students;
    }

    public void writeToFile() throws IOException {
        try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("output.txt"))) {
            writer.writeObject(students);
        }
    }
}
