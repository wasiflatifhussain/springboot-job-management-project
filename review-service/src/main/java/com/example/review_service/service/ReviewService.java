package com.example.review_service.service;

import com.example.review_service.model.Review;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();
    Review createReview(Review review);
    Review updateReview(Long id, Review review);
    boolean deleteReview(Long id);
    List<Review> fetchReviewsByIds(List<Long> reviewIds);
}
