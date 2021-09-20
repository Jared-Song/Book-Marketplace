package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Request;
import com.rmit.sept.bk_loginservices.model.RequestType;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends CrudRepository<Request, Long> {

    // delete a pending book request, which is called when a book is deleted
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Request s where s.objectId = :objectId AND s.requestType = :requestType", nativeQuery = true)
    public void deletePendingBookRequest(@Param("objectId") Long objectId,
            @Param("requestType") RequestType requestType);

    // returns true if a request with the given parameters exists
    @Query("SELECT COUNT(*)>0 FROM Request s WHERE s.objectId = :objectId AND s.requestType = :requestType")
    boolean requestExists(@Param("objectId") Long objectId, @Param("requestType") RequestType requestType);

    @Override
    Iterable<Request> findAll();
}
