package com.example.rcca2.Entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Administrator, Long> {

    @Query(value = "SELECT * FROM administrators a WHERE a.name = :name", nativeQuery = true)
    public Administrator findByName(@Param("name") String name);

    @Query(value = "SELECT role FROM administrators a WHERE a.name = :name", nativeQuery = true)
    public String findRoleByName(@Param("name") String name);

    @Query(value = "SELECT a FROM Administrator a WHERE " +
            "(a.answer = :type) AND" +
            "(a.name LIKE lower(CONCAT('%', :keyword, '%')))")
    //SELECT * FROM administrators WHERE (answer = "two") AND (name LIKE lower("%a%"))
    List<Administrator> search(
            @Param("type") String type,
            @Param("keyword") String keyword);
}
