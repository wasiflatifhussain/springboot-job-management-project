package com.driden.job_app_springboot.service;

import com.driden.job_app_springboot.model.Job;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImplementation implements JobService {
    private List<Job> jobs = new ArrayList<>();

    @Override
    public List<Job> getAllJobs() {
        return jobs;
    }

    @Override
    public Job getJobById(Long id) {
        return jobs.get(0);
    }

    @Override
    public Job createJob(Job job) {
        jobs.add(job);
        return job;
    }

    @Override
    public Job updateJob(Long id, Job job) {
        jobs.set(0, job);
        return job;
    }

    @Override
    public void deleteJob(Long id) {
        jobs.remove(0);
    }

    @Override
    public String getCompanyForJob(Long id) {
        return "Company";
    }
}
