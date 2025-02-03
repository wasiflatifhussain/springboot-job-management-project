package com.driden.job_app_springboot.job;

import com.driden.job_app_springboot.model.Job;
import com.driden.job_app_springboot.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    // GET /api/jobs/{id}: get job with id
    @GetMapping("/jobs/{id}")
    public Job getJobById(Long id) {
        return jobService.getJobById(id);

    }

    // POST /api/jobs: create new job
    @PostMapping("/jobs")
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    // PUT /api/jobs/{id}: update job with id
    @PutMapping("/jobs/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody Job job) {
        return jobService.updateJob(id, job);
    }

    // DELETE /api/jobs/{id}: delete job with id
    @DeleteMapping("/jobs/{id}")
    public void deleteJob(Long id) {
        jobService.deleteJob(id);
    }

    // GET /api/jobs/{id}/company: get company for job with id
    @GetMapping("/jobs/{id}/company")
    public String getCompanyForJob(Long id) {
        return jobService.getCompanyForJob(id);
    }
}
