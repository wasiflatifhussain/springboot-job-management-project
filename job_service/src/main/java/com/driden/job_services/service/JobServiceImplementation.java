package com.driden.job_services.service;

import com.driden.job_services.dao.JobDao;
import com.driden.job_services.model.Job;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImplementation implements JobService {

    @Autowired
    private JobDao jobDao;

    private List<Job> jobs = new ArrayList<>();

    @Transactional
    @Override
    public List<Job> getAllJobs() {
        // return via response entity
        return jobDao.getAllJobs();
    }

    @Transactional
    @Override
    public Job getJobById(Long id) {
        // search for id in jobs and return job
        Job getJob = jobDao.getJobById(id);
        if (getJob == null) {
            return null;
        } else {
            return getJob;
        }
    }

    @Transactional
    @Override
    public Job createJob(Job job) {
        return jobDao.createJob(job);
    }

    @Transactional
    @Override
    public Job updateJob(Long id, Job job) {
        Job updateJob = jobDao.updateJob(id, job);
        if (updateJob != null) {
            return updateJob;
        }
        else {
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteJob(Long id) {
        boolean deleteJob = jobDao.deleteJob(id);
        if (deleteJob) {
            return true;
        }
        else {
            return false;
        }
    }

    @Transactional
    @Override
    public List<Job> getJobsByCompanyId(Long companyId) {
        return jobDao.getJobsByCompanyId(companyId);
    }

    @Transactional
    @Override
    public List<Job> fetchJobsByIds(List<Long> jobIds) {
        return jobDao.fetchJobsByIds(jobIds);
    }

    @Transactional
    @Override
    public List<Job> getJobsByName(String name) {
        return jobDao.getJobsByName(name);
    }

    @Transactional
    @Override
    public Job processNewApplicant(Long jobId, Long userId) {
        Job job = jobDao.getJobById(jobId);
        if (job == null) {
            return null;
        }
        List<Long> applicantIds = job.getApplicantIds();
        if (applicantIds == null) {
            applicantIds = new ArrayList<>();
        }
        applicantIds.add(userId);
        job.setApplicantIds(applicantIds);
        return jobDao.updateJob(jobId, job);
    }

    @Transactional
    @Override
    public Job withdrawApplicant(Long jobId, Long userId) {
        Job job = jobDao.getJobById(jobId);
        if (job == null) {
            return null;
        }
        List<Long> applicantIds = job.getApplicantIds();
        if (applicantIds == null) {
            return null;
        }
        if (!applicantIds.contains(userId)) {
            return null;
        }
        applicantIds.remove(userId);
        job.setApplicantIds(applicantIds);
        return jobDao.updateJob(jobId, job);
    }
}
