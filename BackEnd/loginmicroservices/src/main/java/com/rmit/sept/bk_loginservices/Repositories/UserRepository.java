package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT s FROM User s WHERE s.username = ?1")
    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User s SET s.username = :username, s.password = :password, s.email = :email, s.fullName = :fullName WHERE s.id = :id")
    public void updateUser(@Param("username") String username, @Param("password") String password, 
    @Param("email") String email, @Param("fullName") String fullName, @Param("id") Long id);

    User getById(Long id);

    @Override
    Iterable<User> findAll();
}
