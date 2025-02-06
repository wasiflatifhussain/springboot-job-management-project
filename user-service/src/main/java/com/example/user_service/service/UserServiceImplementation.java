package com.example.user_service.service;

import com.example.user_service.dao.UserDao;
import com.example.user_service.feign.CompanyFeign;
import com.example.user_service.feign.JobFeign;
import com.example.user_service.feign.ReviewFeign;
import com.example.user_service.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JobFeign jobFeign;

    @Autowired
    private CompanyFeign companyFeign;

    @Autowired
    private ReviewFeign reviewFeign;

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public List<User> getUsersByIds(List<Long> userIds) {
        return userDao.getUsersByIds(userIds);
    }

    @Transactional
    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Transactional
    @Override
    public User updateUser(Long id, User user) {
        return userDao.updateUser(id, user);
    }

    @Transactional
    @Override
    public boolean deleteUser(Long id) {
        return userDao.deleteUser(id);
    }

    @Transactional
    @Override
    public List<Job> getJobApplicationsForUser(Long userId) {
        User user = userDao.getUserById(userId);
        if (user == null) {
            return null;
        }

        List<Long> jobsApplied = user.getJobIdsForJobsApplied();
        ResponseEntity<List<Job>> jobs = jobFeign.fetchJobsByIds(jobsApplied);
        return jobs.getBody();
    }

    @Transactional
    @Override
    public CompanyWrapper getCompanyInfo(Long userId) {
        ResponseEntity<Company> response = companyFeign.getCompanyById(userId);
        Company company = response.getBody();

        if (company == null) {
            return null;
        }

        List<Long> jobIds = company.getJobIds();
        List<Long> reviewIds = company.getReviewIds();

        ResponseEntity<List<Job>> jobs = jobFeign.fetchJobsByIds(jobIds);
        List<Job> jobList = jobs.getBody();

        ResponseEntity<List<Review>> reviews = reviewFeign.fetchReviewsByIds(reviewIds);
        List<Review> reviewList = reviews.getBody();

        return new CompanyWrapper(company, jobList, reviewList);
    }

    @Transactional
    @Override
    public List<Review> getCompanyReviews(Long companyId) {
        ResponseEntity<Company> response = companyFeign.getCompanyById(companyId);
        Company company = response.getBody();

        if (company == null) {
            return null;
        }

        List<Long> reviewIds = company.getReviewIds();
        ResponseEntity<List<Review>> reviews = reviewFeign.fetchReviewsByIds(reviewIds);
        return reviews.getBody();
    }

    @Transactional
    @Override
    public List<Job> getAvailableJobs() {
        return jobFeign.getAllJobs().getBody();
    }

    @Transactional
    @Override
    public Job getJobById(Long jobId) {
        Job job = jobFeign.getJobById(jobId).getBody();
        if (job == null) {
            return null;
        }
        return job;
    }

    @Transactional
    @Override
    public CompanyWrapper getCompanyByName(String companyName) {
        ResponseEntity<Company> response = companyFeign.getCompanyByName(companyName);
        Company company = response.getBody();

        if (company == null) {
            return null;
        }

        List<Long> jobIds = company.getJobIds();
        List<Long> reviewIds = company.getReviewIds();

        ResponseEntity<List<Job>> jobs = jobFeign.fetchJobsByIds(jobIds);
        List<Job> jobList = jobs.getBody();

        ResponseEntity<List<Review>> reviews = reviewFeign.fetchReviewsByIds(reviewIds);
        List<Review> reviewList = reviews.getBody();

        return new CompanyWrapper(company, jobList, reviewList);
    }

    @Transactional
    @Override
    public List<Job> getJobsByName(String jobName) {
        return jobFeign.getJobsByName(jobName).getBody();
    }

    @Transactional
    @Override
    public Review leaveReview(Long userId, Long companyId, Review review) {
        User user = userDao.getUserById(userId);
        if (user == null) {
            return null;
        }
        if (user.getCurrentCompanyId().equals(companyId)) {
            review.setCompanyId(companyId);
            ResponseEntity<Review> response = reviewFeign.createReview(review);
            if (response.getBody() == null) {
                return null;
            }
                return response.getBody();
        }
        System.out.println("User is not employed by company");
        return null;
    }

    @Transactional
    @Override
    public Job applyForJob(Long userId, Long jobId) {
        User user = userDao.getUserById(userId);
        if (user == null) {
            return null;
        }
        Job job = jobFeign.getJobById(jobId).getBody();
        if (job == null) {
            return null;
        }
        if (job.getCompanyId().equals(user.getCurrentCompanyId())) {
            return null;
        }

        // update user's jobIdsForJobsApplied
        List<Long> jobIds = user.getJobIdsForJobsApplied();
        if (jobIds.contains(jobId)) {
            System.out.println("User has already applied for this job");
            return null;
        }


        jobIds.add(jobId);
        user.setJobIdsForJobsApplied(jobIds);
        userDao.updateUser(userId, user);

        // update job's applicantIds
        ResponseEntity<Job> response = jobFeign.processNewApplicant(jobId, userId);

        return response.getBody();
    }

    @Transactional
    @Override
    public String withdrawApplicant(Long userId, Long jobId) {
        User user = userDao.getUserById(userId);
        if (user == null) {
            return null;
        }
        Job job = jobFeign.getJobById(jobId).getBody();
        if (job == null) {
            return null;
        }

        List<Long> jobIds = user.getJobIdsForJobsApplied();
        if (!jobIds.contains(jobId)) {
            return "User has not applied for this job";
        }

        jobIds.remove(jobId);
        user.setJobIdsForJobsApplied(jobIds);
        userDao.updateUser(userId, user);

        // update job's applicantIds
        ResponseEntity<Job> response = jobFeign.withdrawApplicant(jobId, userId);
        if (response.getBody() == null) {
            return null;
        }
        else {
            return "Application withdrawn";
        }

    }
}
