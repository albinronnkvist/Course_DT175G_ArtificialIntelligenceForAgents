package me.albinronnkvist.csp;

import java.util.ArrayList;
import java.util.List;

public class Domain<T> {
    private List<T> values;

    public Domain(List<T> values) {
        this.values = new ArrayList<>(values);
    }

    public List<T> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Domain<?> domain = (Domain<?>) obj;
        return values.equals(domain.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }
}
