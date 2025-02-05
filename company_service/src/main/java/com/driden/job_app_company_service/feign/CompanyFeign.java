package com.driden.job_app_company_service.feign;

import com.driden.job_app_company_service.model.Job;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="job-service")
public interface CompanyFeign {

    // get all jobs for the company
    @PostMapping("/job-service/jobs/company/fetch-jobs-by-ids")
    public ResponseEntity<List<Job>> fetchJobsByIds(@RequestBody List<Long> jobIds);

    // add a new job post for the company
    @PostMapping("/job-service/jobs")
    public ResponseEntity<Job> createJob(@RequestBody Job job);

    // update a job post for the company
    @PutMapping("/job-service/jobs/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job);

    // delete a job post for the company
    @DeleteMapping("/job-service/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id);

}
