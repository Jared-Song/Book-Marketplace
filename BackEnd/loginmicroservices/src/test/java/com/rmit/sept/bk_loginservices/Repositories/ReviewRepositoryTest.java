package com.rmit.sept.bk_loginservices.Repositories;

import com.rmit.sept.bk_loginservices.model.Review;
import com.rmit.sept.bk_loginservices.model.User;
import com.rmit.sept.bk_loginservices.services.MapValidationErrorService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@SpringBootTest
@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
@Transactional
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    MockMvc mockMvc;

    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.reviewRepository).build();

    }
    @Test
    @Rollback(true)
    @DisplayName("Test: findNonExistingReview [Pass]")
    public void findNonExistingReview() {

        User user = new User();
        user.setId(1L);
        user.setRatingTotal(3);

        user.setFullName("Kowa Ato");

        Review review = new Review();
        review.setId(1L);

        review.setReviewer(user);
        reviewRepository.save(review);

        Review findReview = reviewRepository.findById(review.getId()).orElse(null);
        Assert.assertEquals(findReview, null);
    }


    @Test
    @Rollback
    @DisplayName("Test: addReview [Pass]")
    public void testAddReview() {
        Review review = new Review();
        review.setId(1L);
        reviewRepository.save(review);

        Review findReview = reviewRepository.findById(1L).orElse(review);
        assertNotNull(findReview);

        Review review1 = new Review();
        review1.setId(2L);
        reviewRepository.save(review1);

        Review findRequest2 = reviewRepository.findById(2L).orElse(review);
        assertNotNull(findRequest2);
    }

}