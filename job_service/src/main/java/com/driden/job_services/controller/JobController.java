package com.driden.job_services.controller;

import com.driden.job_services.model.Job;
import com.driden.job_services.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-service")
public class JobController {

    @Autowired
    private JobService jobService;

    // GET /jobs: get all jobs
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
    }

    // GET /jobs/{id}: get job with id
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if (jobService.getJobById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(job, HttpStatus.OK);
        }

    }

    // POST /jobs: create new job
    @PostMapping("/jobs")
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        try {
            Job newJob = jobService.createJob(job);
            return new ResponseEntity<>(newJob, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /jobs/{id}: update job with id
    @PutMapping("/jobs/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
        try {
            Job jobCheck = jobService.updateJob(id, job);
            return new ResponseEntity<>(jobCheck, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE /jobs/{id}: delete job with id
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        boolean status = jobService.deleteJob(id);
        if (status) {
            return new ResponseEntity<>("Job deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Job not deleted", HttpStatus.NOT_FOUND);
        }
    }

    // GET /jobs/company: get all jobs for company
    @GetMapping("/jobs/company/{companyId}")
    public ResponseEntity<List<Job>> getJobsByCompanyId(@PathVariable Long companyId) {
        return new ResponseEntity<>(jobService.getJobsByCompanyId(companyId), HttpStatus.OK);
    }

    // POST /jobs/company/fetch-jobs-by-ids: get all jobs for list of ids
    @PostMapping("/jobs/company/fetch-jobs-by-ids")
    public ResponseEntity<List<Job>> fetchJobsByIds(@RequestBody List<Long> jobIds) {
        System.out.println(jobIds);
        return new ResponseEntity<>(jobService.fetchJobsByIds(jobIds), HttpStatus.OK);
    }

    // GET /jobs/get-jobs-by-name/{jobName}: get all jobs with job name
    @GetMapping("/jobs/get-jobs-by-name")
    public ResponseEntity<List<Job>> getJobsByName(@RequestParam String jobName) {
        return new ResponseEntity<>(jobService.getJobsByName(jobName), HttpStatus.OK);
    }

    // POST /jobs/{jobId}/new-applicant/{userId}: process new applicant for job
    @PostMapping("/jobs/{jobId}/new-applicant/{userId}")
    public ResponseEntity<Job> processNewApplicant(@PathVariable Long jobId, @PathVariable Long userId) {
        return new ResponseEntity<>(jobService.processNewApplicant(jobId, userId), HttpStatus.OK);
    }

    // POST /jobs/{jobId}/new-applicant/{userId}: process new applicant for job
    @PostMapping("/jobs/{jobId}/withdraw-applicant/{userId}")
    public ResponseEntity<Job> withdrawApplicant(@PathVariable Long jobId, @PathVariable Long userId) {
        return new ResponseEntity<>(jobService.withdrawApplicant(jobId, userId), HttpStatus.OK);
    }

}
