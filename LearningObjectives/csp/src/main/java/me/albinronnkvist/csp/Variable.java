package me.albinronnkvist.csp;

public class Variable {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Variable variable = (Variable) obj;
        return this.name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
