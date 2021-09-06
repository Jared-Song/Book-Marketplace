package com.rmit.sept.bk_loginservices.Repositories;

import com.rmit.sept.bk_loginservices.model.BookReview;
import org.springframework.data.repository.CrudRepository;

public interface BookReviewRepository extends CrudRepository<BookReview, Long> {

    public Iterable<Long> findBookReviewByID(Long id);

    public Iterable<Long> findBookBeingReviewedBefore(Long id);

    public Iterable<Long> findBookReviewedAfter(Long id);

    public Iterable<Long> findAllBy();

}
