package se.miun.dt175g.octi.client.utils;

public class TimeLimitChecker {
    private final long startTime;
    private final long timeLimitMillis;
    private final long endTime;

    public TimeLimitChecker(long timeLimitMillis, long bufferMillis) {
        this.startTime = System.currentTimeMillis();
        this.timeLimitMillis = timeLimitMillis;
        this.endTime = this.startTime + timeLimitMillis - bufferMillis;
    }

    public boolean isTimeUp() {
        return System.currentTimeMillis() > endTime;
    }

    public long getRemainingTime() {
        return timeLimitMillis - (System.currentTimeMillis() - startTime);
    }
}
