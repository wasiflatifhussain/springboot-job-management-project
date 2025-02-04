package com.driden.job_services.dao;

import com.driden.job_services.model.Job;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobDaoImplementation implements JobDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Job> getAllJobs() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Job", Job.class).getResultList();
    }

    @Override
    public Job getJobById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Job.class, id);
    }

    @Override
    public Job createJob(Job job) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(job);
        return job;
    }

    @Override
    public Job updateJob(Long id, Job job) {
        Session session = entityManager.unwrap(Session.class);
        Job jobToUpdate = session.get(Job.class, id);
        jobToUpdate.setTitle(job.getTitle());
        jobToUpdate.setDescription(job.getDescription());
        jobToUpdate.setMinSalary(job.getMinSalary());
        jobToUpdate.setMaxSalary(job.getMaxSalary());
        jobToUpdate.setLocation(job.getLocation());
        jobToUpdate.setCompany(job.getCompany());
        session.saveOrUpdate(jobToUpdate);
        return jobToUpdate;
    }

    @Override
    public boolean deleteJob(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Job job = session.get(Job.class, id);
        if (job == null) {
            return false;
        }
        session.delete(job);
        return true;
    }

    @Override
    public List<Job> getJobsByCompanyName(String companyName) {
        Session session = entityManager.unwrap(Session.class);
        List<Job> jobs = session.createQuery("from Job where company = :companyName", Job.class)
                .setParameter("companyName", companyName)
                .getResultList();
        return jobs;
    }
}
