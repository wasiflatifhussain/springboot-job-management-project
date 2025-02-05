package com.example.review_service.service;

import com.example.review_service.dao.ReviewDao;
import com.example.review_service.model.Review;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Transactional
    @Override
    public List<Review> getAllReviews() {
        return reviewDao.getAllReviews();
    }

    @Override
    public Review createReview(Review review) {
        return reviewDao.createReview(review);
    }

    @Override
    public Review updateReview(Long id, Review review) {
        Review reviewCheck = reviewDao.getReviewById(id);
        if (reviewCheck != null) {
            return reviewDao.updateReview(id, review);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean deleteReview(Long id) {
        Review reviewCheck = reviewDao.getReviewById(id);
        if (reviewCheck != null) {
            return reviewDao.deleteReview(id);
        }
        else {
            return false;
        }
    }

    @Override
    public List<Review> fetchReviewsByIds(List<Long> reviewIds) {
        return reviewDao.fetchReviewsByIds(reviewIds);
    }
}
