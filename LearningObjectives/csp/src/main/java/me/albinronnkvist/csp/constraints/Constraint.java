package me.albinronnkvist.csp.constraints;

import java.util.List;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.Variable;

public interface Constraint<T> {
    boolean isSatisfied(Assignment<T> assignment);
    List<Variable> getScope(); 
}
