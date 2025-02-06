package com.example.user_service.feign;

import com.example.user_service.model.Job;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="job-service")
public interface JobFeign {

    @PostMapping("/job-service/jobs/company/fetch-jobs-by-ids")
    public ResponseEntity<List<Job>> fetchJobsByIds(@RequestBody List<Long> jobIds);

    @GetMapping("/job-service/jobs")
    public ResponseEntity<List<Job>> getAllJobs();

    @GetMapping("/job-service/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id);

    @GetMapping("/job-service/jobs/get-jobs-by-name")
    public ResponseEntity<List<Job>> getJobsByName(@RequestParam String jobName);

    @PutMapping("/job-service/jobs/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job);

    @PostMapping("/job-service/jobs/{jobId}/new-applicant/{userId}")
    public ResponseEntity<Job> processNewApplicant(@PathVariable Long jobId, @PathVariable Long userId);

    @PostMapping("/job-service/jobs/{jobId}/withdraw-applicant/{userId}")
    public ResponseEntity<Job> withdrawApplicant(@PathVariable Long jobId, @PathVariable Long userId);

}
