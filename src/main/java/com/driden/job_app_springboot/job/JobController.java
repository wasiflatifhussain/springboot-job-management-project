package com.driden.job_app_springboot.job;

import com.driden.job_app_springboot.model.Job;
import com.driden.job_app_springboot.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class JobController {

    @Autowired
    private JobService jobService;

    // GET /jobs: get all jobs
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs() {
        return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
    }

    // GET /api/jobs/{id}: get job with id
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        if (jobService.getJobById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(job, HttpStatus.OK);
        }

    }

    // POST /api/jobs: create new job
    @PostMapping("/jobs")
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        try {
            Job newJob = jobService.createJob(job);
            return new ResponseEntity<>(newJob, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /api/jobs/{id}: update job with id
    @PutMapping("/jobs/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {
        try {
            Job jobCheck = jobService.updateJob(id, job);
            return new ResponseEntity<>(jobCheck, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE /api/jobs/{id}: delete job with id
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        boolean status = jobService.deleteJob(id);
        if (status) {
            return new ResponseEntity<>("Job deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Job not deleted", HttpStatus.NOT_FOUND);
        }
    }

    // GET /api/jobs/{id}/company: get company for job with id
    @GetMapping("/jobs/{id}/company")
    public ResponseEntity<String> getCompanyForJob(@PathVariable Long id) {
        String company = jobService.getCompanyForJob(id);
        if (company == "None") {
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(company, HttpStatus.OK);
        }
    }
}
