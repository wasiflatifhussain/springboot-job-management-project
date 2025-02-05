package com.example.user_service.feign;

import com.example.user_service.model.Job;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="job-service")
public interface JobFeign {

    @PostMapping("/job-service/jobs/company/fetch-jobs-by-ids")
    public ResponseEntity<List<Job>> fetchJobsByIds(@RequestBody List<Long> jobIds);
}
