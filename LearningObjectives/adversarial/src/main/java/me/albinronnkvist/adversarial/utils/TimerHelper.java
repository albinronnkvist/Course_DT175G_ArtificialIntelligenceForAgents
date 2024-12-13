package me.albinronnkvist.adversarial.utils;

public class TimerHelper {
    private final long startTime;
    private final long timeLimitMillis;

    public TimerHelper(long timeLimitMillis) {
        this.startTime = System.currentTimeMillis();
        this.timeLimitMillis = timeLimitMillis;
    }

    public boolean isTimeUp() {
        return System.currentTimeMillis() - startTime > timeLimitMillis;
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public long getRemainingTime() {
        return Math.max(0, timeLimitMillis - getElapsedTime());
    }
}

