package me.albinronnkvist.csp.problems;

import org.junit.jupiter.api.Test;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.Variable;
import me.albinronnkvist.csp.algorithms.MinConflicts;
import me.albinronnkvist.csp.problems.nQueens.NQueensCSP;

import static org.assertj.core.api.Assertions.assertThat;

public class NQueensTests {
    @Test
    public void SolveNQueens_WithMinConflicts() {
        int numberOfQueens = 4;
        var nQueensCSP = new NQueensCSP(numberOfQueens);

        var solver = new MinConflicts<Integer>();
        var solution = solver.solve(nQueensCSP, 1000);

        // Assertions
        assertThat(solution).isNotNull();
        assertThat(solution.isSolution(nQueensCSP.getVariables(), nQueensCSP.getConstraints())).isTrue();

        printBoard(solution, numberOfQueens);
    }

    private void printBoard(Assignment<Integer> solution, int numberOfQueens) {
        for (int row = 0; row < numberOfQueens; row++) {
            int col = solution.getValue(new Variable("Q" + row));
            for (int c = 0; c < numberOfQueens; c++) {
                if (c == col) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
