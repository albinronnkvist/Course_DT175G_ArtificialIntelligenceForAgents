package me.albinronnkvist.csp.algorithms;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.CSP;
import me.albinronnkvist.csp.Variable;

public final class Inference {
    private Inference() {}

    /**
     * Maintain arc consistency (MAC) using AC-3.
     */
    public static <T> boolean maintainArcConsistency(CSP<T> csp, Variable variable, Assignment<T> assignment) {
        return new AC3<T>().run(csp);
    }
}
