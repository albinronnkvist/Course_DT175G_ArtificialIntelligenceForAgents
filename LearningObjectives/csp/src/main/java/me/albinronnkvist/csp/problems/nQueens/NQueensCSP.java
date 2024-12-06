package me.albinronnkvist.csp.problems.nQueens;

import java.util.ArrayList;
import java.util.List;

import me.albinronnkvist.csp.CSP;
import me.albinronnkvist.csp.Domain;
import me.albinronnkvist.csp.Variable;
import me.albinronnkvist.csp.constraints.BinaryConstraint;

public class NQueensCSP extends CSP<Integer> {
    private int numberOfQueens;

    public NQueensCSP(int numberOfQueens) {
        super();
        this.numberOfQueens = numberOfQueens;
        setupVariablesAndDomains();
        setupConstraints();
    }

    private void setupVariablesAndDomains() {
        for (int row = 0; row < numberOfQueens; row++) {
            // Create a variable for each row (Q0, Q1, ..., Q(n-1))
            var variable = new Variable("Q" + row);

            // The domain for each variable is a list of column indices (0 to n-1)
            List<Integer> domainValues = new ArrayList<>();
            for (int col = 0; col < numberOfQueens; col++) {
                domainValues.add(col);
            }

            addVariable(variable, new Domain<>(domainValues));
        }
    }

    private void setupConstraints() {
        var variables = getVariables();

        // Outer Loop (i): Iterates over each variable (queen) in the CSP.
        // Inner Loop (j): Starts at i + 1 to ensure each pair of variables is considered exactly once.
        for (int i = 0; i < variables.size(); i++) {
            for (int j = i + 1; j < variables.size(); j++) {
                // The two variables (queens) being constrained in this iteration.
                Variable var1 = variables.get(i);
                Variable var2 = variables.get(j);

                // The rows of the variables
                int row1 = i;
                int row2 = j;
                
                // col1 and col2 are column values assigned to var1 and var2
                addConstraint(new BinaryConstraint<>(var1, var2, (col1, col2) -> {
                    boolean inSameColumn = col1.equals(col2);
                    boolean inSameDiagonal = Math.abs(row1 - row2) == Math.abs(col1 - col2);

                    return !inSameColumn && !inSameDiagonal;
                }));
            }
        }
    }
}