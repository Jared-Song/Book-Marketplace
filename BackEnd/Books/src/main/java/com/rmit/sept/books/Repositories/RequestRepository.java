package com.rmit.sept.books.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.books.model.Request;
import com.rmit.sept.books.model.RequestType;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    // delete a pending book request, which is called when a book is deleted
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Requests s where s.objectId = :objectId AND s.requestType = :requestType", nativeQuery = true)
    public void deletePendingBookRequest(@Param("objectId") Long objectId,
            @Param("requestType") RequestType requestType);   
            
    // delete requests with a given user id, which is called when a user is deleted
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Requests s where s.user_Id = :user_Id", nativeQuery = true)
    public void deleteByUserId(@Param("user_Id") Long userId);  

    @Override
    Iterable<Request> findAll();
}
