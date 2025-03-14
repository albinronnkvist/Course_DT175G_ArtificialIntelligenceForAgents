package se.miun.dt175g.octi.client.utils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeLimitCheckerTests {
    @Test
    public void isTimeUp_shouldReturnFalse_beforeTimeLimitWithoutBuffer() throws InterruptedException {
        var timeLimitChecker = new TimeLimitChecker(500, 0);

        Thread.sleep(400);
        boolean result = timeLimitChecker.isTimeUp();

        assertThat(result).isFalse();
    }

    @Test
    public void isTimeUp_shouldReturnTrue_whenTimeLimitExceededWithoutBuffer() throws InterruptedException {
        var timeLimitChecker = new TimeLimitChecker(500, 0);

        Thread.sleep(510);
        boolean result = timeLimitChecker.isTimeUp();

        assertThat(result).isTrue();
    }

    @Test
    public void isTimeUp_shouldReturnFalse_beforeTimeLimitWithBuffer() throws InterruptedException {
        var timeLimitChecker = new TimeLimitChecker(500, 100);

        Thread.sleep(390);
        boolean result = timeLimitChecker.isTimeUp();

        assertThat(result).isFalse();
    }

    @Test
    public void isTimeUp_shouldReturnTrue_whenTimeLimitExceededWithBuffer() throws InterruptedException {
        var timeLimitChecker = new TimeLimitChecker(500, 100);

        Thread.sleep(410);
        boolean result = timeLimitChecker.isTimeUp();

        assertThat(result).isTrue();
    }
}
