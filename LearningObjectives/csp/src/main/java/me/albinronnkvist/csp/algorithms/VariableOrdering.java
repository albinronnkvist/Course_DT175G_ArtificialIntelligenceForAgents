package me.albinronnkvist.csp.algorithms;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.CSP;
import me.albinronnkvist.csp.Variable;

public final class VariableOrdering {

    private VariableOrdering() {
    }

    /**
     * Selects the next unassigned variable statically (in the order they appear in
     * the CSP).
     */
    public static <T> Variable selectUnassignedVariableStatic(CSP<T> csp, Assignment<T> assignment) {
        return getUnassignedVariables(csp, assignment)
            .findFirst()
            .orElse(null);
    }

    /**
     * Selects the next unassigned variable randomly.
     */
    public static <T> Variable selectUnassignedVariableRandom(CSP<T> csp, Assignment<T> assignment) {
        var unassigned = getUnassignedVariables(csp, assignment)
            .collect(Collectors.toList());

        if (unassigned.isEmpty()) {
            return null;
        }

        return unassigned.get(new Random().nextInt(unassigned.size()));
    }

    /**
     * Selects the next unassigned variable using the Minimum-Remaining-Values (MRV) Heuristic
     * heuristic.
     *
     * @param csp        The CSP instance.
     * @param assignment The current partial assignment.
     * @return The unassigned variable with the smallest domain, or null if all
     *         variables are assigned.
     */
    public static <T> Variable selectUnassignedVariableMRV(CSP<T> csp, Assignment<T> assignment) {
        return getUnassignedVariables(csp, assignment)
                .min(Comparator.comparingInt(var -> csp.getDomain(var).getValues().size()))
                .orElse(null);
    }

    /**
     * Selects the next unassigned variable using the Degree Heuristic.
     * Chooses the variable involved in the largest number of constraints.
     */
    public static <T> Variable selectUnassignedVariableDegreeHeuristic(CSP<T> csp, Assignment<T> assignment) {
        return getUnassignedVariables(csp, assignment)
                .max(Comparator.comparingInt(var -> countConstraints(csp, var)))
                .orElse(null);
    }



    private static <T> int countConstraints(CSP<T> csp, Variable variable) {
        return Math.toIntExact(csp.getConstraints().stream()
                .filter(constraint -> constraint.getScope().contains(variable))
                .count());
    }

    private static <T> Stream<Variable> getUnassignedVariables(CSP<T> csp, Assignment<T> assignment) {
        return csp.getVariables().stream()
                .filter(var -> !assignment.isAssigned(var));
    }
}
