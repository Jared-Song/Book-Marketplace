package com.rmit.sept.users.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.users.model.Role;
import com.rmit.sept.users.model.User;
import com.rmit.sept.users.model.UserStatus;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // finds a user with the given username
    @Query("SELECT s FROM User s WHERE s.username = ?1")
    User findByUsername(String username);

    // returns true if a user with the given username exists
    @Query("SELECT COUNT(s)>0 FROM User s WHERE s.username = :username")
    boolean usernameExists(String username);

    // set a user's status to enabled
    @Transactional
    @Modifying
    @Query(value = "UPDATE User s SET s.status = :status WHERE s.id = :id", nativeQuery = true)
    public void setUserStatus(@Param("status") UserStatus status, @Param("id") Long id);

    // set a user's role
    @Transactional
    @Modifying
    @Query(value = "UPDATE User s SET s.role = :role WHERE s.id = :id", nativeQuery = true)
    public void setUserRole(@Param("role") Role role, @Param("id") Long id);

    // update a user's details
    @Transactional
    @Modifying
    @Query(value = "UPDATE User s SET s.email = :email, s.username = :username, s.fullName = :fullName, s.address = :address, s.role = :role, s.status = :status, s.rating = :rating, s.ratingNo = :ratingNo WHERE s.id = :id", nativeQuery = true)
    public void updateUser(@Param("email") String email, @Param("username") String username,
            @Param("fullName") String fullName, @Param("address") String address, @Param("role") Role role,
            @Param("status") UserStatus status, @Param("rating") double rating, @Param("ratingNo") int ratingNo,
            @Param("id") Long id);

    // update a user's password
    @Transactional
    @Modifying
    @Query(value = "UPDATE User s SET s.password = :password WHERE s.id = :id", nativeQuery = true)
    public void updateUserPassword(@Param("password") String password, @Param("id") Long id);

    // get a user by their id
    User getById(Long id);

    @Override
    Iterable<User> findAll();
}
