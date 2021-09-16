package com.rmit.sept.bk_loginservices.Repositories;

import javax.transaction.Transactional;

import com.rmit.sept.bk_loginservices.model.Request;
import com.rmit.sept.bk_loginservices.model.RequestType;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends CrudRepository<Request, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Request s where s.objectId = :objectId AND s.requestType = :requestType")
    public void deletePendingBookRequest(@Param("objectId") Long objectId,
            @Param("requestType") RequestType requestType);

    @Override
    Iterable<Request> findAll();
}
