package bulanci;


public class HumanPlayer extends Player {

    private int score = 0;

    public HumanPlayer(String name, GameMap map, int direction) {
        super(name, map, direction);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void addScore() {
        score++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; Score: ").append(score);
        return sb.toString();
    }
}
