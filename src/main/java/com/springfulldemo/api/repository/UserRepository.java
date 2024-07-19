package com.springfulldemo.api.repository;

import com.springfulldemo.api.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends
        JpaRepository<User, Integer>,
        PagingAndSortingRepository<User, Integer>,
        JpaSpecificationExecutor<User> {
    User findByLogin(String login);

    Boolean existsByLoginAndIdIsNot(String login, Integer id);

    @Query(value = " " +
            " select schema_name as schema from information_schema.schemata " +
            " where schema_name = :name limit 1", nativeQuery = true)
    String findSchemaByName(@Param("name") String name);
}
