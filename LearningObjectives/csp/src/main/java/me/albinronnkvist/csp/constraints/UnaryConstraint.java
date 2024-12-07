package me.albinronnkvist.csp.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.Variable;

public class UnaryConstraint<T> implements Constraint<T> {
    private Variable variable;
    private Predicate<T> condition;
    private List<Variable> scope;

    public UnaryConstraint(Variable variable, Predicate<T> condition) {
        this.variable = variable;
        this.condition = condition;

        scope = new ArrayList<Variable>(1);
        scope.add(variable);
    }

    @Override
    public boolean isSatisfied(Assignment<T> assignment) {
        if (!assignment.isAssigned(variable)) {
            return true;
        }

        T value = assignment.getValue(variable);
        return condition.test(value);
    }

    @Override
    public List<Variable> getScope() {
        return scope;
    }
}
