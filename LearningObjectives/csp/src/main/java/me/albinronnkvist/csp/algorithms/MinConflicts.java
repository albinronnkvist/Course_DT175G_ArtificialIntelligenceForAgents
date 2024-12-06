package me.albinronnkvist.csp.algorithms;

import java.util.*;
import java.util.stream.Collectors;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.CSP;
import me.albinronnkvist.csp.Variable;
import me.albinronnkvist.csp.constraints.BinaryConstraint;
import me.albinronnkvist.csp.constraints.Constraint;

public class MinConflicts<T> {
    private Random random = new Random();

    /**
     * Solves the CSP using the Min-Conflicts heuristic.
     *
     * @param csp      The CSP instance to solve.
     * @param maxSteps The maximum number of steps before stopping.
     * @return A solution (complete and consistent assignment), or null if no
     *         solution is found within maxSteps.
     */
    public Assignment<T> solve(CSP<T> csp, int maxSteps) {
        var assignment = initializeRandomCompleteAssignment(csp);

        for (int step = 0; step < maxSteps; step++) {
            if (assignment.isSolution(csp.getVariables(), csp.getConstraints())) {
                return assignment;
            }

            var conflictingVariables = getConflictingVariables(csp, assignment);
            if (!conflictingVariables.isEmpty()) {
                var conflictedVariable = conflictingVariables.get(random.nextInt(conflictingVariables.size()));

                T minConflictValue = findMinConflictValue(csp, assignment, conflictedVariable);
                assignment.assign(conflictedVariable, minConflictValue);
            }
        }

        return null;
    }

    private Assignment<T> initializeRandomCompleteAssignment(CSP<T> csp) {
        var assignment = new Assignment<T>();

        for (var variable : csp.getVariables()) {
            var domainValues = csp.getDomain(variable).getValues();

            T randomValue = domainValues.get(random.nextInt(domainValues.size()));
            assignment.assign(variable, randomValue);
        }

        return assignment;
    }

    private List<Variable> getConflictingVariables(CSP<T> csp, Assignment<T> assignment) {
        List<Variable> conflictingVariables = new ArrayList<>();
    
        for (Constraint<T> constraint : csp.getConstraints()) {
            if(constraint.isSatisfied(assignment)) {
               continue; 
            }

            var variablesFromConstraint = getVariablesFromConstraint(constraint);

            for (Variable variable : variablesFromConstraint) {
                if (conflictingVariables.contains(variable)) {
                    continue;
                }

                conflictingVariables.add(variable);
            }
        }
    
        return conflictingVariables;
    }
    
    private List<Variable> getVariablesFromConstraint(Constraint<T> constraint) {
        List<Variable> variables = new ArrayList<>();

        if (constraint instanceof BinaryConstraint) {
            BinaryConstraint<T> binary = (BinaryConstraint<T>) constraint;
            variables.add(binary.getVar1());
            variables.add(binary.getVar2());
        }

        return variables;
    }

    private T findMinConflictValue(CSP<T> csp, Assignment<T> assignment, Variable variable) {
        var domainValues = csp.getDomain(variable).getValues();
        var conflictCounts = new HashMap<T, Integer>();

        // Evaluate conflicts for each value in the domain
        for (T value : domainValues) {
            assignment.assign(variable, value);
            int conflicts = countConflicts(csp, assignment);
            conflictCounts.put(value, conflicts);
        }

        // Find the value with the minimum conflicts
        int minConflicts = Collections.min(conflictCounts.values());
        List<T> bestValues = conflictCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == minConflicts)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Return a randomly chosen value among those with minimum conflicts
        return bestValues.get(random.nextInt(bestValues.size()));
    }

    private int countConflicts(CSP<T> csp, Assignment<T> assignment) {
        return (int) csp.getConstraints().stream()
                .filter(constraint -> !constraint.isSatisfied(assignment))
                .count();
    }
}
