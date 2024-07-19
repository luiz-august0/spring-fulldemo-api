package com.springfulldemo.api.infrastructure.specs.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

public interface IPredicateSpec {

    Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Path path, Object fieldValue);

}
