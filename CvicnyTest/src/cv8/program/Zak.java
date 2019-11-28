package cv8.program;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Zak implements IPrumer, Serializable, Comparable<Zak> {
    static private int pocet;
    private int id;
    private String jmeno;
    private String prijmeni;
    private LinkedList<Integer> znamky;

    public Zak() {
        this("Pepa", "Novak", new LinkedList<Integer>());
    }

    public Zak(String jmeno, String prijmeni, LinkedList<Integer> znamky) {
        id = pocet++;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.znamky = znamky;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    /*@Override
            public double vypoctiPrumer() {
                //return znamky.stream().collect(Collectors.averagingInt(znamky.));
                return 0.0;
            }*/
    @Override
    public double vypoctiPrumer() {
        int sum = 0;
        for (int i = 0; i < znamky.size(); i++) {
            sum += znamky.get(i);
        }
        return (double)sum/znamky.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ").append(jmeno).append(" ").append(prijmeni).append(" Znamky: ");

        Iterator<Integer> iterator = znamky.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append(", ");
        }
        sb.append("Prumer: " + vypoctiPrumer());
        return sb.toString();
    }

    @Override
    public int compareTo(Zak zak) {
        return (int)(this.vypoctiPrumer() * 10) - (int)(zak.vypoctiPrumer()*10);
    }
}
