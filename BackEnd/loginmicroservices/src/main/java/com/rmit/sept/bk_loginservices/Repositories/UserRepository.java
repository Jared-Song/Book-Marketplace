package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Role;
import com.rmit.sept.bk_loginservices.model.UserStatus;
import com.rmit.sept.bk_loginservices.model.User;

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

    // update a user's details
    @Transactional
    @Modifying
    @Query(value = "UPDATE User s SET s.email = :email, s.username = :username, s.fullName = :fullName, s.password = :password, s.address = :address, s.role = :role, s.userStatus = :userStatus, s.rating = :rating, s.ratingNo = :ratingNo WHERE s.id = :id", nativeQuery = true)
    public void updateUser(@Param("email") String email, @Param("username") String username,
            @Param("fullName") String fullName, @Param("password") String password, @Param("address") String address,
            @Param("role") Role role, @Param("userStatus") UserStatus userStatus, @Param("rating") double rating,
            @Param("ratingNo") int ratingNo, @Param("id") Long id);

    // get a user by their id
    User getById(Long id);

    @Override
    Iterable<User> findAll();
}
