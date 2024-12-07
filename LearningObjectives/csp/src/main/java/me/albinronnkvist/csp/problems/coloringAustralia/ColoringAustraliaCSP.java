package me.albinronnkvist.csp.problems.coloringAustralia;

import java.util.Arrays;

import me.albinronnkvist.csp.CSP;
import me.albinronnkvist.csp.Domain;
import me.albinronnkvist.csp.Variable;
import me.albinronnkvist.csp.constraints.BinaryConstraint;

public class ColoringAustraliaCSP extends CSP<String> {
    public ColoringAustraliaCSP() {
        super();
        setupVariablesAndDomains();
        setupConstraints();
    }

    private void setupVariablesAndDomains() {
        var colors = Arrays.asList("Red", "Green", "Blue");
        var states = Arrays.asList("WA", "NT", "SA", "Q", "NSW", "V", "T");

        for (var state : states) {
            addVariable(new Variable(state), new Domain<>(colors));
        }
    }

    private void setupConstraints() {
        addAdjacencyConstraint("WA", "NT");
        addAdjacencyConstraint("WA", "SA");
        addAdjacencyConstraint("NT", "SA");
        addAdjacencyConstraint("NT", "Q");
        addAdjacencyConstraint("SA", "Q");
        addAdjacencyConstraint("SA", "NSW");
        addAdjacencyConstraint("SA", "V");
        addAdjacencyConstraint("Q", "NSW");
        addAdjacencyConstraint("NSW", "V");
    }

    private void addAdjacencyConstraint(String region1, String region2) {
        var var1 = getVariable(region1);
        var var2 = getVariable(region2);

        addConstraint(new BinaryConstraint<>(var1, var2, (color1, color2) -> !color1.equals(color2)));
    }
}
