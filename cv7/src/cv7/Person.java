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
        for (Time time : times) {
            sb.append(" ").append(time.toString());
        }
        return sb.toString();
    }

    public Time getBestTime() {
        Time best_time = new Time(times.get(0).getHours(), times.get(0).getMinutes(), times.get(0).getSeconds());

        for(int i = 1; i < times.size(); i++) {
            if(best_time.getHours() > times.get(i).getHours()) {
                best_time.setHours(times.get(i).getHours());
                best_time.setMinutes(times.get(i).getMinutes());
                best_time.setSeconds(times.get(i).getSeconds());
            }
            else if((best_time.getHours() == times.get(i).getHours()) && (best_time.getMinutes() > times.get(i).getMinutes())) {
                best_time.setHours(times.get(i).getHours());
                best_time.setMinutes(times.get(i).getMinutes());
                best_time.setSeconds(times.get(i).getSeconds());
            }
            else if((best_time.getHours() == times.get(i).getHours()) &&
                    (best_time.getMinutes() == times.get(i).getMinutes()) &&
                    (best_time.getSeconds() > times.get(i).getSeconds())) {
                best_time.setHours(times.get(i).getHours());
                best_time.setMinutes(times.get(i).getMinutes());
                best_time.setSeconds(times.get(i).getSeconds());
            }
        }
        return best_time;
    }
}
