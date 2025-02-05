package com.example.user_service.service;


import com.example.user_service.model.*;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    List<User> getUsersByIds(List<Long> userIds);
    User createUser(User user);
    User updateUser(Long id, User user);
    boolean deleteUser(Long id);
    List<Job> getJobApplicationsForUser(Long userId);

    CompanyWrapper getCompanyInfo(Long userId);
    List<Review> getCompanyReviews(Long companyId);
    List<Job> getAvailableJobs();
    Job getJobById(Long jobId);
    CompanyWrapper getCompanyByName(String companyName);

    List<Job> getJobsByName(String jobName);

    Review leaveReview(Long userId, Long companyId, Review review);
}
