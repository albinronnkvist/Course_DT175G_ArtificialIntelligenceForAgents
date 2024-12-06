package me.albinronnkvist.csp.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.Variable;

public class HigherOrderConstraint<T> implements Constraint<T> {
    private List<Variable> variables;
    private Predicate<List<T>> condition;

    public HigherOrderConstraint(List<Variable> variables, Predicate<List<T>> condition) {
        this.variables = variables;
        this.condition = condition;
    }

    @Override
    public boolean isSatisfied(Assignment<T> assignment) {
        List<T> values = new ArrayList<>();
        for (Variable var : variables) {
            if (!assignment.isAssigned(var)) {
                return true;
            }

            values.add(assignment.getValue(var));
        }
        
        return condition.test(values);
    }
}
