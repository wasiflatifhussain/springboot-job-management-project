package com.example.review_service.dao;

import com.example.review_service.model.Review;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDaoImplementation implements ReviewDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Review> getAllReviews() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Review", Review.class).getResultList();
    }

    @Override
    public Review createReview(Review review) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(review);
        return review;
    }

    @Override
    public Review getReviewById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Review.class, id);
    }

    @Override
    public Review updateReview(Long id, Review review) {
        Session session = entityManager.unwrap(Session.class);
        Review reviewToUpdate = session.get(Review.class, id);
        reviewToUpdate.setTitle(review.getTitle());
        reviewToUpdate.setDescription(review.getDescription());
        session.saveOrUpdate(reviewToUpdate);
        return reviewToUpdate;
    }

    @Override
    public boolean deleteReview(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Review review = session.get(Review.class, id);
        if (review == null) {
            return false;
        }
        session.delete(review);
        return true;
    }

    @Override
    public List<Review> fetchReviewsByIds(List<Long> reviewIds) {
        Session session = entityManager.unwrap(Session.class);
        List<Review> reviews = session.createQuery("from Review where id in (:reviewIds)", Review.class)
                .setParameter("reviewIds", reviewIds)
                .getResultList();
        return reviews;
    }
}
