package cv7;

import java.util.LinkedList;

public class Person {
    private String name;
    private String surname;
    private LinkedList<Time> times;

    public Person(String name, String surname, LinkedList<Time> times) {
        this.name = name;
        this.surname = surname;
        this.times = new LinkedList<>();
        this.times = times;
    }

    public String getName() {
        return name + " " + surname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        for(int i = 0; i < times.size(); i++) {
            sb.append(" " + times.get(i).getHours() + ":" + times.get(i).getMinutes() + ":" + times.get(i).getSeconds());
        }
        return sb.toString();
    }
}
