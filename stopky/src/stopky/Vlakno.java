package stopky;

import java.time.Duration;
import java.time.LocalTime;

public class Vlakno extends Thread {

    private LocalTime startTime;
    private Duration currentTime;
    private boolean wait = false;
    private LocalTime pausedTime;
    private LocalTime resumedTime;

    public Vlakno(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            if(wait) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignore) {}
            } else {
                currentTime = Duration.between(startTime, LocalTime.now());
            }
        }
        System.err.println("Preruseno");
    }

    public Duration getCurrentTime() {
        return currentTime;
    }

    public boolean isWait() {
        return wait;
    }

    public void setWait() {
        wait = !wait;
    }

    public void setPausedTime(LocalTime pausedTime) {
        this.pausedTime = pausedTime;
    }

    public void setResumedTime(LocalTime resumedTime) {
        this.resumedTime = resumedTime;
    }

    public void calculatePauseDuration() {
        Duration pauseDuration = Duration.between(pausedTime, resumedTime);
        //System.out.println("pause duration: " + pauseDuration);
        startTime = startTime.plus(Duration.ofHours(pauseDuration.toHoursPart()));
        startTime = startTime.plus(Duration.ofMinutes(pauseDuration.toMinutesPart()));
        startTime = startTime.plus(Duration.ofSeconds(pauseDuration.toSecondsPart()));
        startTime = startTime.plus(Duration.ofMillis(pauseDuration.toMillisPart()));
        //System.out.println("new start time: " + startTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if(getCurrentTime().toHoursPart() != 0)
            sb.append(getCurrentTime().toHoursPart()).append(":");
        if(getCurrentTime().toMinutesPart() != 0)
            sb.append(getCurrentTime().toMinutesPart()).append(":");
        if(getCurrentTime().toSecondsPart() != 0)
            sb.append(getCurrentTime().toSecondsPart()).append(":");
        if(getCurrentTime().toMillisPart() != 0)
            sb.append(getCurrentTime().toMillisPart());

        return sb.toString();
    }
}
