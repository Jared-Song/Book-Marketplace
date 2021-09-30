package com.rmit.sept.browsing.Repositories;

import com.rmit.sept.browsing.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // finds a user with the given username
    @Query("SELECT s FROM User s WHERE s.username = ?1")
    User findByUsername(String username);

    // get a user by their id
    User getById(Long id);

    @Override
    Iterable<User> findAll();
}
