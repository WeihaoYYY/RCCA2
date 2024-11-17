package com.example.rcca2.Repository;


import com.example.rcca2.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user a WHERE a.name = :name", nativeQuery = true)
    public Optional<User> getByName(@Param("name") String name);

    @Query(value = "SELECT role FROM user a WHERE a.name = :name", nativeQuery = true)
    public String findRoleByName(@Param("name") String name);

/*    @Query(value = "SELECT a FROM user a WHERE " +
            "(a.answer = :type) AND" +
            "(a.name LIKE lower(CONCAT('%', :keyword, '%')))")
    //SELECT * FROM administrators WHERE (answer = "two") AND (name LIKE lower("%a%"))
    List<User> search(
            @Param("type") String type,
            @Param("keyword") String keyword);*/
}
