package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Role;
import com.rmit.sept.bk_loginservices.model.Status;
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

    @Query("SELECT COUNT(s)>0 FROM User s WHERE s.username = :username")
    boolean usernameExists(String username);

    // @Transactional
    // @Modifying
    // @Query("UPDATE User s SET s.username = :username, s.password = :password,
    // s.email = :email, s.fullName = :fullName, s.address = :address WHERE s.id =
    // :id")
    // public void updateUser(@Param("username") String username, @Param("password")
    // String password,
    // @Param("email") String email, @Param("fullName") String fullName,
    // @Param("address") String address,
    // @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User s SET s.email = :email, s.username = :username, s.fullName = :fullName, s.password = :password, s.address = :address, s.role = :role, s.status = :status, s.rating = :rating, s.ratingNo = :ratingNo WHERE s.id = :id")
    public void updateUser(@Param("email") String email, @Param("username") String username,
            @Param("fullName") String fullName, @Param("password") String password, @Param("address") String address,
            @Param("role") Role role, @Param("status") Status status, @Param("rating") double rating,
            @Param("ratingNo") int ratingNo, @Param("id") Long id);

    User getById(Long id);

    @Override
    Iterable<User> findAll();
}
