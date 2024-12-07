package me.albinronnkvist.csp.algorithms;

import java.util.*;
import java.util.stream.Collectors;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.CSP;
import me.albinronnkvist.csp.Variable;

// TODO: implement Conflict-Directed Backjumping
public class BacktrackingSearch<T> {
    public List<Map<Variable, T>> nogoods = new ArrayList<>();

    public Assignment<T> solve(CSP<T> csp) {
        return backtrack(new Assignment<>(), csp);
    }

    private Assignment<T> backtrack(Assignment<T> assignment, CSP<T> csp) {
        printAssignments("Current assignment", assignment);

        if (isNogood(assignment)) {
            printAssignments("Detected nogood", assignment);
            return null;
        }

        if (assignment.isSolution(csp.getVariables(), csp.getConstraints())) {
            return assignment;
        }

        var variable = VariableOrdering.selectUnassignedVariableMRV(csp, assignment);

        for (var value : ValueOrdering.orderDomainValuesLCV(variable, assignment, csp)) {
            assignment.assign(variable, value);
            System.out.println("Trying: " + variable.getName() + " = " + value);

            if (assignment.isConsistent(csp.getConstraints())) {
                var backupDomains = backupDomains(csp);

                if (Inference.maintainArcConsistency(csp, variable, assignment)) {
                    var result = backtrack(assignment, csp);
                    if (result != null) {
                        return result;
                    }
                }

                restoreDomains(csp, backupDomains);
            }

            learnFromFailure(assignment);
            assignment.unassign(variable);
            System.out.println("Backtracking on: " + variable.getName());
        }

        return null;
    }

    private Map<Variable, List<T>> backupDomains(CSP<T> csp) {
        var savedDomains = new HashMap<Variable, List<T>>();

        for (var variable : csp.getVariables()) {
            savedDomains.put(variable, new ArrayList<>(csp.getDomain(variable).getValues()));
        }

        return savedDomains;
    }

    private void restoreDomains(CSP<T> csp, Map<Variable, List<T>> savedDomains) {
        for (var entry : savedDomains.entrySet()) {
            csp.getDomain(entry.getKey()).getValues().clear();
            csp.getDomain(entry.getKey()).getValues().addAll(entry.getValue());
        }
    }

    // Constraint learning
    private void learnFromFailure(Assignment<T> assignment) {
        printAssignments("Learning from failure", assignment);
        nogoods.add(new HashMap<>(assignment.getVariableAssignments()));
    }

    private boolean isNogood(Assignment<T> assignment) {
        return nogoods.stream()
            .anyMatch(nogood -> nogood.entrySet().stream()
                .filter(entry -> assignment.getValue(entry.getKey()) != null)
                .allMatch(entry -> assignment.getValue(entry.getKey()).equals(entry.getValue()))
            );
    }

    private void printAssignments(String title, Assignment<T> assignment) {
        System.out.println(title + ": " + assignment.getVariableAssignments().entrySet().stream()
            .map(entry -> entry.getKey().getName() + " = " + entry.getValue())
            .collect(Collectors.joining(", ")));
    }
}

