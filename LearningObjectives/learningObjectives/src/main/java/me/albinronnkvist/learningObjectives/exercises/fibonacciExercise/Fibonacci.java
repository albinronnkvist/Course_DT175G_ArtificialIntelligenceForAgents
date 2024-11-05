package me.albinronnkvist.learningObjectives.exercises.fibonacciExercise;

public interface Fibonacci {
    /*
     * Calculates the nth Fibonacci number, Fₙ, defined as
     * F₀ = 0, F₁ = 1, Fₙ = Fₙ₋₁ + Fₙ₋₂.
     */
    long fib(int n);
    int getRecursiveCalls();
}
