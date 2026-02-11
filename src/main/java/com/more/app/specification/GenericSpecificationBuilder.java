package com.more.app.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecificationBuilder<T> {

    private final List<Specification<T>> specs = new ArrayList<>();

    public GenericSpecificationBuilder<T> equals(String field, Object value) {
        if (Objects.nonNull(value)) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get(field), value));
        }
        return this;
    }

    public GenericSpecificationBuilder<T> like(String field, String value) {
        if (value != null && !value.isBlank()) {
            specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get(field)),
                            "%" + value.toLowerCase() + "%"));
        }
        return this;
    }

    public GenericSpecificationBuilder<T> range(String field,
                                                Comparable<?> from,
                                                Comparable<?> to) {

        if (from != null) {
            specs.add((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get(field), (Comparable) from));
        }

        if (to != null) {
            specs.add((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get(field), (Comparable) to));
        }

        return this;
    }

    public Specification<T> build() {

        if (specs.isEmpty()) {
            return null;
        }

        Specification<T> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}

