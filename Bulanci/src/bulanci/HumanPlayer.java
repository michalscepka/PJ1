package bulanci;

import java.io.Serializable;

public class HumanPlayer extends Player implements Serializable, Comparable<HumanPlayer> {

    private int score = 0;
    private int deaths = 0;

    public HumanPlayer(String name, int direction) {
        super(name, direction);
    }

    public HumanPlayer(String name, int score, int deaths) {
        super(name);
        this.score = score;
        this.deaths = deaths;
    }

    public int getScore() {
        return score;
    }

    public void addScore() {
        score++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeaths() {
        deaths++;
    }

    @Override
    public String toString() {
        return super.toString() + "; Score: " + score + "; Deaths: " + deaths;
    }

    @Override
    public int compareTo(HumanPlayer humanPlayer) {
        return humanPlayer.getScore() - this.getScore();
    }
}
