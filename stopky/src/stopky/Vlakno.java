package stopky;

public class Vlakno extends Thread {

    private long startTime;
    private long currentTime;

    public Vlakno(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void run() {
        while(true) {
            //System.out.println(System.currentTimeMillis() - startTime);
            currentTime = System.currentTimeMillis() - startTime;
            //label.setText(String.valueOf(currentTime));
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                System.err.println("Preruseno");
                return;
            }
        }
    }

    public long getCurrentTime() {
        return currentTime;
    }
}
