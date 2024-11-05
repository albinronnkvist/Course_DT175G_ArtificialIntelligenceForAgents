package me.albinronnkvist.learningObjectives.exercises;

import me.albinronnkvist.learningObjectives.exercises.fibonacciExercise.Fibonacci;
import me.albinronnkvist.learningObjectives.exercises.fibonacciExercise.LinearRecursive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;

public class FibonacciExerciseTests {

    private Fibonacci fib;

    @BeforeEach
    public void setUp() {
        fib = new LinearRecursive();
    }

    @Test
    public void testBaseCase() {
        assertThat(fib.fib(0))
            .isEqualTo(0L);
        
        assertThat(fib.fib(1))
            .isEqualTo(1L);
    }

    @Test
    public void testGeneralCase() {
        Random rng = new Random();
        int n = rng.nextInt(100) + 2;
        
        boolean allCasesValid = true;
        for (int i = 0; i < 10; i++) {
            if (fib.fib(n) != fib.fib(n - 1) + fib.fib(n - 2)) {
                allCasesValid = false;
                break;
            }
        }
        
        assertThat(allCasesValid).isTrue();
    }

    @Test
    @Disabled("Skipping due to flakiness")
    public void testComplexity() {
        getAverageTime(new LinearRecursive(), 100);
        
        long time1 = getAverageTime(new LinearRecursive(), 200);
        long time2 = getAverageTime(new LinearRecursive(), 400);
        
        assertThat(approxEqual((double) time1 / 200, (double) time2 / 400, 0.7))
            .isTrue();
    }

    private long getAverageTime(Fibonacci fib, int arg) {
        int iterations = 1000;
        long sum = 0L;
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            fib.fib(arg);
            sum += System.nanoTime() - start;
        }
        return sum / iterations;
    }

    private boolean approxEqual(double x, double y, double e) {
        double ratio = Math.max(x, y) / Math.min(x, y);
        return (ratio <= e + 1.0);
    }
}
