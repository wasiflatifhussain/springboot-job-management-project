package com.example.review_service.controller;

import com.example.review_service.model.Review;
import com.example.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review-service")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // GET /reviews: get all reviews
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    // POST /reviews: create new review
    @PostMapping("/reviews")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        try {
            Review newReview = reviewService.createReview(review);
            return new ResponseEntity<>(newReview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /review/{id}: update review with id
    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        try {
            Review reviewCheck = reviewService.updateReview(id, review);
            return new ResponseEntity<>(reviewCheck, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE /review/{id}: delete review with id
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        boolean status = reviewService.deleteReview(id);
        if (status) {
            return new ResponseEntity<>("Review deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Review not deleted", HttpStatus.NOT_FOUND);
        }
    }

    // GET /review/fetch-reviews-by-ids: get all reviews for list of ids
    @PostMapping("/reviews/fetch-reviews-by-ids")
    public ResponseEntity<List<Review>> fetchReviewsByIds(@RequestBody List<Long> reviewIds) {
        System.out.println(reviewIds);
        return new ResponseEntity<>(reviewService.fetchReviewsByIds(reviewIds), HttpStatus.OK);
    }
}
