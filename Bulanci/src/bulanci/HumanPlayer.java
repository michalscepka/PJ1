package bulanci;

public class HumanPlayer extends Player {

    private int score = 0;
    private int deaths = 0;

    public HumanPlayer(String name, GameMap map, int direction) {
        super(name, map, direction);
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
}
