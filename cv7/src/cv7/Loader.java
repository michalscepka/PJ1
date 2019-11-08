package cv7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Loader {
    private ArrayList<Person> persons;

    public Loader() {
        this.persons = new ArrayList<>();
    }

    public void load(String file) throws IOException {
        ArrayList<Person> array = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            while(line != null) {
                String[] items = line.split("[ :,]");
                LinkedList<Time> times = new LinkedList<>();

                for(int i = 2; i < items.length; i += 3) {
                    times.add(new Time(Integer.parseInt(items[i]), Integer.parseInt(items[i+1]), Integer.parseInt(items[i+2])));
                }

                array.add(new Person(items[0], items[1], times));
                line = reader.readLine();
            }
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
        this.persons = array;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < persons.size(); i++) {
            s.append(persons.get(i).toString()).append("\n");
        }
        return s.toString();
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }
}
