package com.driden.job_services.dao;

import com.driden.job_services.model.Job;

import java.util.List;

public interface JobDao {
    List<Job> getAllJobs();
    Job getJobById(Long id);
    Job createJob(Job job);
    Job updateJob(Long id, Job job);
    boolean deleteJob(Long id);
    List<Job> getJobsByCompanyName(String companyName);
}
