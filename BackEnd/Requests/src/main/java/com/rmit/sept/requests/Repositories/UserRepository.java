package com.rmit.sept.requests.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.requests.model.Role;
import com.rmit.sept.requests.model.User;
import com.rmit.sept.requests.model.UserStatus;

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

    // set a user's status to enabled
    @Transactional
    @Modifying
    @Query("UPDATE User SET status = :status WHERE user_id = :id")
    public void setUserStatus(@Param("status") UserStatus status, @Param("id") Long id);

    // set a user's role
    @Transactional
    @Modifying
    @Query("UPDATE User SET role = :role WHERE user_id = :id")
    public void setUserRole(@Param("role") Role role, @Param("id") Long id);

    // update a user's details
    @Transactional
    @Modifying
    @Query(value = "UPDATE Users SET email = :email, username = :username, full_Name = :fullName, address = :address WHERE user_id = :id", nativeQuery = true)
    public void updateUser(@Param("email") String email, @Param("username") String username,
            @Param("fullName") String fullName, @Param("address") String address, @Param("id") Long id);

    // get a user by their id
    User getById(Long id);

    @Override
    Iterable<User> findAll();
}
