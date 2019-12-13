package cv7;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Loader loader = new Loader();
        loader.load("persons.txt");
        System.out.println(loader.toString());

        ArrayList<Person> persons = loader.getPersons();
        FileWriter writer = new FileWriter("best_times.txt");
        for (Person person : persons) {
            writer.write(person.getName() + " " + person.getBestTime() + System.lineSeparator());
        }
        writer.close();
    }
}
