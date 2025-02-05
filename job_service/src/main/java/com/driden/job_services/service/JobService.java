package com.driden.job_services.service;

import com.driden.job_services.model.Job;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface JobService {
    List<Job> getAllJobs();
    Job getJobById(Long id);
    Job createJob(Job job);
    Job updateJob(Long id, Job job);
    boolean deleteJob(Long id);
    List<Job> getJobsByCompanyId(Long companyId);
    List<Job> fetchJobsByIds(List<Long> jobIds);

}
