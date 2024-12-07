package me.albinronnkvist.csp.algorithms;

import java.util.*;

import me.albinronnkvist.csp.Assignment;
import me.albinronnkvist.csp.CSP;
import me.albinronnkvist.csp.Variable;
import me.albinronnkvist.csp.constraints.BinaryConstraint;
import me.albinronnkvist.csp.constraints.Constraint;
import me.albinronnkvist.csp.utils.Pair;

public class AC3<T> {

    /**
     * Runs the AC-3 algorithm on the given CSP to enforce arc consistency.
     *
     * @param csp The CSP instance to process.
     * @return True if the CSP is arc-consistent and a solution may exist.
     *         False if a domain is emptied during processing, indicating no solution is possible.
     */
    public boolean run(CSP<T> csp) {
        Queue<Pair<Variable, Variable>> queue = initializeArcs(csp);

        while (!queue.isEmpty()) {
            var arc = queue.poll();
            var x = arc.x();
            var y = arc.y();

            if (revise(csp, x, y)) {
                if (csp.getDomain(x).getValues().isEmpty()) {
                    return false;
                }

                for (Constraint<T> constraint : csp.getConstraints()) {
                    if (constraint instanceof BinaryConstraint) {
                        var binaryConstraint = (BinaryConstraint<T>) constraint;
                        if (binaryConstraint.getVar2().equals(x) && !binaryConstraint.getVar1().equals(y)) {
                            queue.add(new Pair<>(binaryConstraint.getVar1(), x));
                        }
                    }
                }
            }
        }

        return true;
    }

    private Queue<Pair<Variable, Variable>> initializeArcs(CSP<T> csp) {
        Queue<Pair<Variable, Variable>> queue = new LinkedList<>();

        for (Constraint<T> constraint : csp.getConstraints()) {
            if (constraint instanceof BinaryConstraint) {
                var binaryConstraint = (BinaryConstraint<T>) constraint;
                queue.add(new Pair<>(binaryConstraint.getVar1(), binaryConstraint.getVar2()));
                queue.add(new Pair<>(binaryConstraint.getVar2(), binaryConstraint.getVar1()));
            }
        }

        return queue;
    }

    /**
     * Revises the domain of variable x to enforce consistency with variable y.
     *
     * @param csp The CSP instance.
     * @param x   The variable whose domain is being revised.
     * @param y   The other variable in the arc.
     * @return True if the domain of x was revised.
     */
    private boolean revise(CSP<T> csp, Variable x, Variable y) {
        boolean revised = false;
        var domainX = new ArrayList<>(csp.getDomain(x).getValues());
        var domainY = csp.getDomain(y).getValues();

        for (var domainValueX : domainX) {
            boolean hasSupport = false;

            for (var domainValueY : domainY) {
                if (isConsistent(csp, x, domainValueX, y, domainValueY)) {
                    hasSupport = true;
                    break;
                }
            }
            
            if (!hasSupport) {
                csp.getDomain(x).getValues().remove(domainValueX);
                revised = true;
            }
        }

        return revised;
    }

    private boolean isConsistent(CSP<T> csp, Variable x, T domainValueX, Variable y, T domainValueY) {
        for (Constraint<T> constraint : csp.getConstraints()) {
            if (constraint instanceof BinaryConstraint) {
                var binaryConstraint = (BinaryConstraint<T>) constraint;
                boolean constraintTargetsPair = (binaryConstraint.getVar1().equals(x) && binaryConstraint.getVar2().equals(y)) ||
                    (binaryConstraint.getVar1().equals(y) && binaryConstraint.getVar2().equals(x));
                
                if (constraintTargetsPair) {
                    var tempAssignment = new Assignment<T>();
                    tempAssignment.assign(x, domainValueX);
                    tempAssignment.assign(y, domainValueY);

                    if (!constraint.isSatisfied(tempAssignment)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}

