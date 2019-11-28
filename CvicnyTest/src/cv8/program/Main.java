package cv8.program;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws IOException {
        /*LinkedList<Integer> znamky = new LinkedList<>();
        znamky.add(1);
        znamky.add(2);
        znamky.add(3);
        Zak z1 = new Zak("Honza", "Novak", znamky);
        Zak z2 = new Zak();
        System.out.println(z1.toString());
        System.out.println(z2.toString());*/

        DataWorker dw = new DataWorker();
        ArrayList<Zak> zaci = dw.vytvorZaky(10);
        for(Zak zak : zaci)
            System.out.println(zak.toString());

        System.out.println("----------------------------------------------");

        Collections.sort(zaci);

        for(Zak zak : zaci)
            System.out.println(zak.toString());

        System.out.println("----------------------------------------------");

        ArrayList<Zak> zaci2 = dw.nactiZaky("in.txt");
        for(Zak zak : zaci2)
            System.out.println(zak.toString());

        System.out.println("----------------------------------------------");

        ArrayList<Zak> zaci3 = dw.vyhledejZaky(zaci, "etr");
        for(Zak zak : zaci3)
            System.out.println(zak.toString());
    }
}
