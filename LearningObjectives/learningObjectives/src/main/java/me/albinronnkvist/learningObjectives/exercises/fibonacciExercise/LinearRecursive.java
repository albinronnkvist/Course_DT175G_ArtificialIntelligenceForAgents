package me.albinronnkvist.learningObjectives.exercises.fibonacciExercise;

import java.util.Arrays;

public class LinearRecursive implements Fibonacci {
    private long[] fibonacciMemo = new long[500];
    private int recursiveCalls = 0;

    public LinearRecursive() {
        Arrays.fill(fibonacciMemo, -1);
        fibonacciMemo[0] = 0;
        fibonacciMemo[1] = 1;
    }

    @Override
    public long fib(int n) {
        
        if(n < 2)
        {
            return n;
        }
        
        // Have to do this since the pre-written tests use the same instance.
        if (n >= fibonacciMemo.length) {
            fibonacciMemo = Arrays.copyOf(fibonacciMemo, n + 1);
            Arrays.fill(fibonacciMemo, fibonacciMemo.length, n + 1, -1);
        }

        if (fibonacciMemo[n] != -1) {
            return fibonacciMemo[n];
        }
        
        recursiveCalls++;
        fibonacciMemo[n] = fib(n - 1) + fib(n - 2);

        return fibonacciMemo[n];
    }

    public int getRecursiveCalls() {
        return recursiveCalls;
    }
}

/*
 * Example:
 * fib(0) = 0 (base case)
 * fib(1) = 1 (base case)
 * fib(2) = 1 (calculated from fib(1) + fib(0))
 * fib(3) = 2 (calculated from fib(2) + fib(1))
 * fib(4) = 3 (calculated from fib(3) + fib(2))
 * fib(5) = 5 (final result from fib(4) + fib(3))
 */

/* 
 * SOLUTION 1 (SLOW)
 * Using "return fib(n - 1) + fib(n - 2)" for the recursive case is too slow, with exponential time complexity O(2^n).
 * 
 * The call stack would look like this:
 * fib(5) → returns 5
 * │
 * ├─ fib(4) → returns 3
 * │   │
 * │   ├─ fib(3) → returns 2
 * │   │   │
 * │   │   ├─ fib(2) → returns 1
 * │   │   │   ├─ fib(1) → returns 1
 * │   │   │   └─ fib(0) → returns 0
 * │   │   └─ fib(1) → returns 1
 * │   └─ fib(2) → returns 1
 * └─ fib(3) → returns 2
 *     ├─ fib(2) → returns 1
 *     │   ├─ fib(1) → returns 1
 *     │   └─ fib(0) → returns 0
 *     └─ fib(1) → returns 1
*/

/*
 * SOLUTION 2 (LINEAR) - Memoization Approach
 * The "fibonacciMemo" array stores each computed Fibonacci number, so every fib(n) value is calculated only once.
 * Once fibonacciMemo[n] is computed, subsequent calls to fib(n) return the cached result in constant time (O(1)).
 * For each fib(n), the recursion tree only needs to go as deep as fib(n - 1) and fib(n - 2). 
 * With memoization, this means that each Fibonacci number from fib(2) up to fib(n) is computed exactly once, resulting in a total of O(n) recursive calls.
 */
