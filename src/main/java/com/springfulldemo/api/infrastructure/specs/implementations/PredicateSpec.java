package com.springfulldemo.api.infrastructure.specs.implementations;

import com.springfulldemo.api.infrastructure.specs.interfaces.IPredicateSpec;
import jakarta.persistence.criteria.CriteriaBuilder;

public class PredicateSpec {

    public static IPredicateSpec getPredicateSpecEquals() {
        return CriteriaBuilder::equal;
    }

    public static IPredicateSpec getPredicateSpecNotEquals() {
        return CriteriaBuilder::notEqual;
    }

    public static IPredicateSpec getPredicateSpecGreaterOrEquals() {
        return (criteriaBuilder, path, fieldValue) -> criteriaBuilder.greaterThanOrEqualTo(path, (Comparable) fieldValue);
    }

    public static IPredicateSpec getPredicateSpecLessOrEquals() {
        return (criteriaBuilder, path, fieldValue) -> criteriaBuilder.lessThanOrEqualTo(path, (Comparable) fieldValue);
    }

    public static IPredicateSpec getPredicateSpecLike() {
        return (criteriaBuilder, path, fieldValue) -> criteriaBuilder.like(criteriaBuilder.upper(path), "%" + fieldValue.toString().toUpperCase() + "%");
    }

}
