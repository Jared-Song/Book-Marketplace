package com.rmit.sept.users.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.users.model.Request;
import com.rmit.sept.users.model.RequestType;

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
    @Query(value = "DELETE FROM Request s where s.objectId = :objectId AND s.requestType = :requestType", nativeQuery = true)
    public void deletePendingBookRequest(@Param("objectId") Long objectId,
            @Param("requestType") RequestType requestType);

    // delete requests with a given user id, which is called when a user is deleted
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Requests s where s.user_Id = :user_Id", nativeQuery = true)
    public void deleteByUserId(@Param("user_Id") Long userId);        

    // returns true if a request with the given parameters exists
    @Query(value = "SELECT COUNT(*)>0 FROM Request s WHERE s.objectId = :objectId AND s.requestType = :requestType", nativeQuery = true)
    boolean requestExists(@Param("objectId") Long objectId, @Param("requestType") RequestType requestType);

    @Override
    Iterable<Request> findAll();
}
