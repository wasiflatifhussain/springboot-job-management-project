package com.driden.job_app_company_service.feign;

import com.driden.job_app_company_service.model.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="review-service")
public interface ReviewFeign {
    @PostMapping("/review-service/reviews/fetch-reviews-by-ids")
    public ResponseEntity<List<Review>> fetchReviewsByIds(@RequestBody List<Long> reviewIds);
}
