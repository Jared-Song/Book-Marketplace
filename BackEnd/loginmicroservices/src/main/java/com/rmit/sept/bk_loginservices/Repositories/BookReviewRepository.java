
package com.rmit.sept.bk_loginservices.Repositories;

import com.rmit.sept.bk_loginservices.model.BookReview;
import com.rmit.sept.bk_loginservices.model.UserReview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookReviewRepository extends CrudRepository<BookReview, Long> {

    @Query(value = "SELECT q FROM BOOK_REVIEW WHERE q.id = ?", nativeQuery = true)
    Optional<BookReview> findById(Long id);

    @Query(value = "SELECT * FROM BOOK_REVIEW", nativeQuery = true)
    Iterable<BookReview> getAll();

    @Query(value = "SELECT * FROM BOOK_REVIEW ORDER BY rating ASC", nativeQuery = true)
    Iterable<UserReview> sortRatingByAsc();

    @Query(value = "SELECT * FROM BOOK_REVIEW ORDER BY rating DESC", nativeQuery = true)
    Iterable<UserReview> sortRatingByDesc();
}

