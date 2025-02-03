package com.driden.job_app_springboot.service;

import com.driden.job_app_springboot.model.Job;

import java.util.List;

public interface JobService {
    List<Job> getAllJobs();
    Job getJobById(Long id);
    Job createJob(Job job);
    Job updateJob(Long id, Job job);
    void deleteJob(Long id);
    String getCompanyForJob(Long id);

}
