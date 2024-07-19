package com.springfulldemo.api.infrastructure.specs.builders;

import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.specs.enums.EnumSpecification;
import com.springfulldemo.api.model.entities.AbstractEntity;
import com.springfulldemo.api.utils.Utils;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SpecificationBuilder {
    public static <Entity extends AbstractEntity> Specification<Entity> toSpec(Map<String, Object> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            filters.forEach((filterKey, filterValue) -> {
                if (filterKey.contains(":")) {
                    String prefix = filterKey.substring(filterKey.indexOf(":"), filterKey.lastIndexOf(":") + 1);
                    String fieldName = filterKey.substring(0, filterKey.indexOf(":"));
                    EnumSpecification specification = EnumSpecification.getEnumByPrefix(prefix);

                    try {
                        createSpecification(predicates, filterValue, fieldName, root, criteriaBuilder, specification);
                    } catch (Exception e) {
                        throw new ApplicationGenericsException(e.getMessage());
                    }
                }
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static <Entity extends AbstractEntity> void createSpecification(List<Predicate> predicates,
                                                                            Object fieldValue,
                                                                            String fieldName,
                                                                            Root<Entity> root,
                                                                            CriteriaBuilder criteriaBuilder,
                                                                            EnumSpecification specification) {
        try {
            if (Utils.isNotEmpty(fieldValue)) {
                Path path = getPathFromField(fieldName, root);

                Predicate predicate = PredicateBuilder.createPredicate(criteriaBuilder, path, fieldValue, specification);

                if (Utils.isNotEmpty(predicate)) {
                    predicates.add(predicate);
                }
            }
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    private static <Entity extends AbstractEntity> Path getPathFromField(String fieldName, Root<Entity> root) {
        List<String> fields = Arrays.stream(fieldName.split("\\.")).toList();

        if (fields.size() <= 1) {
            return root.get(fieldName);
        } else {
            return getPathJoinFromFields(fields, root);
        }

    }

    private static <Entity extends AbstractEntity> Path getPathJoinFromFields(List<String> fields, Root<Entity> root) {
        Join<Entity, Entity> join = root.join(fields.getFirst(), JoinType.LEFT);

        for (String field : fields) {
            if (!fields.getFirst().equals(field)) {
                if (fields.getLast().equals(field)) {
                    return join.get(field);
                } else {
                    join = join.join(field, JoinType.LEFT);
                }
            }
        }

        return join.get(fields.getLast());
    }
}
