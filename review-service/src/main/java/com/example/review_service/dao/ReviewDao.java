package com.example.review_service.dao;

import com.example.review_service.model.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getAllReviews();
    Review createReview(Review review);
    Review getReviewById(Long id);
    Review updateReview(Long id, Review review);
    boolean deleteReview(Long id);
    List<Review> fetchReviewsByIds(List<Long> reviewIds);
}
