package me.albinronnkvist.csp.constraints;

import java.util.function.BiPredicate;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.Variable;

public class BinaryConstraint<T> implements Constraint<T> {
    private Variable var1;
    private Variable var2;
    private BiPredicate<T, T> condition;

    public BinaryConstraint(Variable var1, Variable var2, BiPredicate<T, T> condition) {
        this.var1 = var1;
        this.var2 = var2;
        this.condition = condition;
    }

    @Override
    public boolean isSatisfied(Assignment<T> assignment) {
        if (!assignment.isAssigned(var1) || !assignment.isAssigned(var2)) {
            return true;
        }

        T value1 = assignment.getValue(var1);
        T value2 = assignment.getValue(var2);
        return condition.test(value1, value2);
    }

    public Variable getVar1() {
        return var1;
    }

    public Variable getVar2() {
        return var2;
    }
}