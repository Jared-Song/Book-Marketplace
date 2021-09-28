
package com.rmit.sept.bk_loginservices.Repositories;

import com.rmit.sept.bk_loginservices.model.Review;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query(value = "SELECT q FROM USER_REVIEW WHERE q.id = ?", nativeQuery = true)
    public Iterable<Review> findBookReviewByID(Long id);

    @Override
    Iterable<Review> findAll();

    @Query(value = "SELECT * FROM USER_REVIEW ORDER BY rating ASC", nativeQuery = true)
    Iterable<Review> sortRatingByAsc();

    @Query(value = "SELECT * FROM USER_REVIEW ORDER BY rating DESC", nativeQuery = true)
    Iterable<Review> sortRatingByDesc();
}

