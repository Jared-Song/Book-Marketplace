
package com.rmit.sept.reviews.Repositories;

import com.rmit.sept.reviews.model.Review;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query(value = "SELECT q FROM review WHERE review_id = ?", nativeQuery = true)
    public Iterable<Review> findReviewByID(Long id);

    @Override
    //@Query(value = "SELECT * FROM review", nativeQuery = true)
    Iterable<Review> findAll();

    @Query(value = "SELECT * FROM review ORDER BY rating ASC", nativeQuery = true)
    Iterable<Review> sortRatingByAsc();

    @Query(value = "SELECT * FROM review ORDER BY rating DESC", nativeQuery = true)
    Iterable<Review> sortRatingByDesc();
}

