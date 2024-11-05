package se.miun.dt175g.fibonacci;

import java.util.Random;

public class ManualTests {
        public static boolean testCorrectness(Fibonacci fib) {
        return (baseCase(fib) && generalCase(fib));
    }

    // Flaky tests 
    // Also, they use the same instance of Fibonacci
    public static boolean testComplexity(Fibonacci fib) {
        getAverageTime(fib, 100);

        long time1 = getAverageTime(fib, 200);
        long time2 = getAverageTime(fib, 400);
        return approxEqual((time1 / 200L), (time2 / 400L), 0.5D);
    }

    public static void test(Fibonacci fib) {
        boolean ok = true;
        if (!testCorrectness(fib)) {
            ok = false;
            System.out.println("Your implementation does not produce the correct values.");
        }
        if (!testComplexity(fib)) {
            ok = false;
            System.out.println("Your implementation doesn't seem to be linear.");
        }
        if (ok)
            System.out.println("OK");
    }

    private static boolean baseCase(Fibonacci fib) {
        return (fib.fib(0) == 0L && fib.fib(1) == 1L);
    }

    private static boolean generalCase(Fibonacci fib) {
        Random rng = new Random();
        int n = rng.nextInt(100) + 2;
        for (int i = 0; i < 10; i++) {
            if (fib.fib(n) != fib.fib(n - 1) + fib.fib(n - 2))
                return false;
        }
        return true;
    }

    private static long getAverageTime(Fibonacci fib, int arg) {
        int iterations = 1000;
        long sum = 0L;
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            fib.fib(arg);
            sum += System.nanoTime() - start;
        }
        return sum / iterations;
    }

    private static boolean approxEqual(double x, double y, double e) {
        double ratio = Math.max(x, y) / Math.min(x, y);
        return (ratio <= e + 1.0D);
    }
}
