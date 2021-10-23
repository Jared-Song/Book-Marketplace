
package com.rmit.sept.reviews.Repositories;

import com.rmit.sept.reviews.model.Review;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Override
    Iterable<Review> findAll();
}
