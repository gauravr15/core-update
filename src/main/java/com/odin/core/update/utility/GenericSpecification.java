package com.odin.core.update.utility;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

public class GenericSpecification<T> implements Specification<T> {
    private final SearchCriteria criteria;

    public GenericSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, javax.persistence.criteria.CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (!criteria.isValid()) {
            return null; // Skip criteria if the value is invalid (null or blank)
        }

        String operation = criteria.getOperation();
        String key = criteria.getKey();
        Object value = criteria.getValue();

        // Handle Enum types by converting string values to their corresponding enum types
        Field field;
        try {
            field = root.getJavaType().getDeclaredField(key);

            if (field.getType().isEnum() && value instanceof String) {
                Class<? extends Enum> enumType = (Class<? extends Enum>) field.getType();
                value = Enum.valueOf(enumType, (String) value); // Convert the string to the correct enum type
            }
        } catch (NoSuchFieldException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Field not found or cannot convert value for key: " + key, e);
        }

        switch (operation) {
            case ":":
                return builder.equal(root.get(key), value);
            case ">":
                return builder.greaterThan(root.get(key), (Comparable) value);
            case "<":
                return builder.lessThan(root.get(key), (Comparable) value);
            case ">=":
                return builder.greaterThanOrEqualTo(root.get(key), (Comparable) value);
            case "<=":
                return builder.lessThanOrEqualTo(root.get(key), (Comparable) value);
            case "like":
                return builder.like(root.get(key), "%" + value + "%");
            case "in":
                return root.get(key).in(value);
            default:
                return null;
        }
    }
}
