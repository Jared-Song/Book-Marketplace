
package com.rmit.sept.bk_loginservices.Repositories;

import com.rmit.sept.bk_loginservices.model.BookReview;
import com.rmit.sept.bk_loginservices.model.UserReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReviewRepository extends CrudRepository<UserReview, Long> {

    @Query(value = "SELECT q FROM USER_REVIEW WHERE q.id = ?", nativeQuery = true)
    public Iterable<UserReview> findBookReviewByID(Long id);

    @Override
    Iterable<UserReview> findAll();

    @Query(value = "SELECT * FROM USER_REVIEW ORDER BY rating ASC", nativeQuery = true)
    Iterable<UserReview> sortRatingByAsc();

    @Query(value = "SELECT * FROM USER_REVIEW ORDER BY rating DESC", nativeQuery = true)
    Iterable<UserReview> sortRatingByDesc();
}

