package com.driden.job_app_springboot.service;

import com.driden.job_app_springboot.model.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImplementation implements JobService {
    private List<Job> jobs = new ArrayList<>();

    @Override
    public List<Job> getAllJobs() {
        // return via response entity
        return jobs;
    }

    @Override
    public Job getJobById(Long id) {
        // search for id in jobs and return job
        for (Job job : jobs) {
            if (job.getId() == id) {
                System.out.println("Job found");
                return job;
            }
        }
        System.out.println("Job not found");
        return null;
    }

    @Override
    public Job createJob(Job job) {
        jobs.add(job);
        return job;
    }

    @Override
    public Job updateJob(Long id, Job job) {
        for (Job j : jobs) {
            if (j.getId().equals(id)) {
                j.setTitle(job.getTitle());
                j.setDescription(job.getDescription());
                j.setMinSalary(job.getMinSalary());
                j.setMaxSalary(job.getMaxSalary());
                j.setLocation(job.getLocation());
                return j;
            }
        }
        return null;
    }

    @Override
    public boolean deleteJob(Long id) {
        try {
            for (Job j : jobs) {
                if (j.getId().equals(id)) {
                    jobs.remove(j);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getCompanyForJob(Long id) {
        for (Job j : jobs) {
            if (j.getId().equals(id)) {
                return "XYZ Ltd";
            }
        }
        return "Company";
    }
}
