package me.albinronnkvist.csp.algorithms;

import java.util.Comparator;
import java.util.List;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.CSP;
import me.albinronnkvist.csp.Variable;

public final class ValueOrdering {
    private ValueOrdering() {
    }

    /**
     * Order domain values using the LCV heuristic.
     */
    public static <T> List<T> orderDomainValuesLCV(Variable variable, Assignment<T> assignment, CSP<T> csp) {
        return csp.getDomain(variable).getValues().stream()
            .sorted(Comparator.comparingInt(value -> countConflicts(variable, value, assignment, csp)))
            .toList();
    }
    
    private static <T> int countConflicts(Variable variable, T value, Assignment<T> assignment, CSP<T> csp) {
        assignment.assign(variable, value); // Temporarily assign the value

        int conflicts = Math.toIntExact(csp.getConstraints().stream()
                .filter(constraint -> !constraint.isSatisfied(assignment))
                .count());
        
        assignment.unassign(variable); // Undo the assignment
        
        return conflicts;
    }
}
