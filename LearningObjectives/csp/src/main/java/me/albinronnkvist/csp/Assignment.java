package me.albinronnkvist.csp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.albinronnkvist.csp.constraints.Constraint;

public class Assignment<T> {
    private Map<Variable, T> variableAssignments;

    public Assignment() {
        this.variableAssignments = new HashMap<>();
    }
    
    public void assign(Variable var, T value) {
        variableAssignments.put(var, value);
    }
    
    public void unassign(Variable var) {
        variableAssignments.remove(var);
    }

    public boolean isAssigned(Variable var) {
        return variableAssignments.containsKey(var);
    }

    public T getValue(Variable var) {
        return variableAssignments.get(var);
    }

    public Map<Variable, T> getVariableAssignments() {
        return variableAssignments;
    }

    public boolean isComplete(List<Variable> variables) {
        return variableAssignments.keySet().containsAll(variables);
    }

    public boolean isConsistent(List<Constraint<T>> constraints) {
        for (Constraint<T> constraint : constraints) {
            if (!constraint.isSatisfied(this)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSolution(List<Variable> variables, List<Constraint<T>> constraints) {
        return isComplete(variables) && isConsistent(constraints);
    }
}
