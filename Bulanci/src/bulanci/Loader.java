package bulanci;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Loader {
    private ArrayList<HumanPlayer> players;

    public Loader() {
        this.players = new ArrayList<>();
    }

    public void load(String file) throws IOException {
        ArrayList<HumanPlayer> array = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while (line != null) {
                String[] items = line.split(";");
                array.add(new HumanPlayer(items[0], Integer.parseInt(items[1]), Integer.parseInt(items[2])));
                line = reader.readLine();
            }
        }
        this.players = array;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (HumanPlayer player : players) {
            sb.append(player.toString()).append("\n");
        }
        return sb.toString();
    }

    public ArrayList<HumanPlayer> getPlayers() {
        return players;
    }

    public String getAllNames() {
        return players.stream().map(HumanPlayer::getName).collect(Collectors.joining(", "));
    }
}
