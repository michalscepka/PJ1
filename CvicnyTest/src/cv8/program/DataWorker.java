package cv8.program;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class DataWorker {
    private final String[] jmena = { "Petr", "Petra", "Jmeno3", "Jmeno4", "Jmeno5" };
    private final String[] prijmeni = { "Prijmeni1", "Prijmeni2", "Prijmen3", "Prijmeni4", "Prijmeni5" };

    public DataWorker() {

    }

    public ArrayList<Zak> vytvorZaky(int pocetZaku) {
        Random random = new Random();
        ArrayList<Zak> list= new ArrayList<>();
        for(int i = 0; i < pocetZaku; i++) {
            LinkedList<Integer> znamky = new LinkedList<>();
            for(int j = 0; j < 10; j++) {
                znamky.add(random.nextInt(5) + 1);
            }
            list.add(new Zak(jmena[random.nextInt(jmena.length)], prijmeni[random.nextInt(prijmeni.length)], znamky));
        }
        return list;
    }

    public ArrayList<Zak> nactiZaky(String nazevSouboru) throws IOException {
        ArrayList<Zak> array = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nazevSouboru));
            String line = reader.readLine();

            while(line != null) {
                String[] items = line.split("[ ,]");
                LinkedList<Integer> znamky = new LinkedList<>();

                for(int i = 2; i < items.length; i++) {
                    znamky.add(Integer.parseInt(items[i]));
                }

                array.add(new Zak(items[0], items[1], znamky));
                line = reader.readLine();
            }
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
        return array;
    }

    public ArrayList<Zak> vyhledejZaky(ArrayList<Zak> zaci, String podretezec) {
        ArrayList<Zak> array = new ArrayList<>();
        for(Zak zak : zaci) {
            if (zak.getJmeno().contentEquals(podretezec) || zak.getPrijmeni().contentEquals(podretezec)) {
                array.add(zak);
            }
        }
        return array;
    }
}
