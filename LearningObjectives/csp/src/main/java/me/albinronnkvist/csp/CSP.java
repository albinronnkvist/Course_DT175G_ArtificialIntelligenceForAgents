package me.albinronnkvist.csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.albinronnkvist.csp.constraints.Constraint;

public class CSP<T> {
    private List<Variable> variables;
    private Map<Variable, Domain<T>> domains;
    private List<Constraint<T>> constraints;

    public CSP() {
        variables = new ArrayList<>();
        domains = new HashMap<>();
        constraints = new ArrayList<>();
    }

    public void addVariable(Variable var, Domain<T> domain) {
        variables.add(var);
        domains.put(var, domain);
    }

    public void addConstraint(Constraint<T> constraint) {
        constraints.add(constraint);
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public Map<Variable, Domain<T>> getDomains() {
        return domains;
    }

    public Domain<T> getDomain(Variable var) {
        return domains.get(var);
    }

    public List<Constraint<T>> getConstraints() {
        return constraints;
    }
}
