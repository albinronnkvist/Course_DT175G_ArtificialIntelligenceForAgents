package me.albinronnkvist.csp.problems;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import me.albinronnkvist.csp.algorithms.AC3;
import me.albinronnkvist.csp.algorithms.BacktrackingSearch;
import me.albinronnkvist.csp.problems.coloringAustralia.ColoringAustraliaCSP;

public class ColoringAustraliaTests {
    @Test
    public void RunAC3_With_WA_RestrictiveSetup_ShouldPruneDomainsAndEnsureArcConsistency() {
        var csp = new ColoringAustraliaCSP();

        // Restrict WA domain to only Red
        var waVariable = csp.getVariable("WA");
        csp.getDomain(waVariable).getValues().clear();
        csp.getDomain(waVariable).getValues().add("Red");

        var ac3 = new AC3<String>();
        boolean isArcConsistent = ac3.run(csp);

        assertThat(isArcConsistent).isTrue();

        // Assert that no domain is empty
        csp.getVariables().forEach(variable -> {
            assertThat(csp.getDomain(variable).getValues())
                    .isNotEmpty();
        });

        // Assert that the domains are pruned correctly
        assertThat(csp.getDomain(csp.getVariable("NT")).getValues())
                .doesNotContain("Red");

        assertThat(csp.getDomain(csp.getVariable("SA")).getValues())
                .doesNotContain("Red");

        System.out.println("Domains after AC-3:");
        csp.getVariables().forEach(variable -> {
            System.out.println(variable.getName() + ": " + csp.getDomain(variable).getValues());
        });
    }

    @Test
    public void RunAC3_With_WA_And_NT_RestrictiveSetup_ShouldPruneDomainsAndEnsureArcConsistency() {
        var csp = new ColoringAustraliaCSP();

        // Restrict WA domain to only Red
        var waVariable = csp.getVariable("WA");
        csp.getDomain(waVariable).getValues().clear();
        csp.getDomain(waVariable).getValues().add("Red");

        // Restrict NT domain to only Red and Green
        var ntVariable = csp.getVariable("NT");
        csp.getDomain(ntVariable).getValues().clear();
        csp.getDomain(ntVariable).getValues().add("Red");
        csp.getDomain(ntVariable).getValues().add("Green");

        var ac3 = new AC3<String>();
        boolean isArcConsistent = ac3.run(csp);

        assertThat(isArcConsistent).isTrue();

        // Assert that no domain is empty
        csp.getVariables().forEach(variable -> {
            assertThat(csp.getDomain(variable).getValues())
                    .isNotEmpty();
        });

        // Assert that the domains are pruned correctly
        assertThat(csp.getDomain(csp.getVariable("NT")).getValues())
                .doesNotContain("Red")
                .doesNotContain("Blue");

        assertThat(csp.getDomain(csp.getVariable("SA")).getValues())
                .doesNotContain("Red")
                .doesNotContain("Green");

        assertThat(csp.getDomain(csp.getVariable("Q")).getValues())
                .doesNotContain("Green")
                .doesNotContain("Blue");

        assertThat(csp.getDomain(csp.getVariable("NSW")).getValues())
                .doesNotContain("Red")
                .doesNotContain("Blue");

        assertThat(csp.getDomain(csp.getVariable("V")).getValues())
                .doesNotContain("Green")
                .doesNotContain("Blue");

        System.out.println("Domains after AC-3:");
        csp.getVariables().forEach(variable -> {
            System.out.println(variable.getName() + ": " + csp.getDomain(variable).getValues());
        });
    }

    @Test
    public void BacktrackingSearch_ShouldSolveColoringAustralia() {
        var csp = new ColoringAustraliaCSP();

        var solver = new BacktrackingSearch<String>();
        var solution = solver.solve(csp);

        assertThat(solution.isSolution(csp.getVariables(), csp.getConstraints())).isTrue();

        csp.getVariables().forEach(variable -> {
            System.out.println(variable.getName() + ": " + solution.getValue(variable));
        });
    }

    // TODO: make sure it hits nogoods
    @Test
    public void BacktrackingSearch_WithConstraintLearning_ShouldSolveColoringAustralia() {
        var csp = new ColoringAustraliaCSP();

        var waVariable = csp.getVariable("WA");
        csp.getDomain(waVariable).getValues().clear();
        csp.getDomain(waVariable).getValues().add("Red");

        var ntVariable = csp.getVariable("NT");
        csp.getDomain(ntVariable).getValues().clear();
        csp.getDomain(ntVariable).getValues().add("Green");
        csp.getDomain(ntVariable).getValues().add("Blue");

        var qVariable = csp.getVariable("Q");
        csp.getDomain(qVariable).getValues().clear();
        csp.getDomain(qVariable).getValues().add("Green");
        csp.getDomain(qVariable).getValues().add("Blue");

        var solver = new BacktrackingSearch<String>();
        var solution = solver.solve(csp);

        assertThat(solver.nogoods.size()).isGreaterThan(0);

        if (solution != null) {
            assertThat(solution.isSolution(csp.getVariables(), csp.getConstraints())).isTrue();

            csp.getVariables().forEach(variable -> {
                System.out.println(variable.getName() + ": " + solution.getValue(variable));
            });
        } else {
            System.out.println("No solution found.");
        }
    }
}
