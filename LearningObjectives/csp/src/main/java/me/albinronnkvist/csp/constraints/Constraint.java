package me.albinronnkvist.csp.constraints;

import me.albinronnkvist.csp.Assignment;

public interface Constraint<T> {
    boolean isSatisfied(Assignment<T> assignment);
}
