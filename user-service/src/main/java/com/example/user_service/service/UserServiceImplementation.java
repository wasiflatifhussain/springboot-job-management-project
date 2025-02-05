package com.example.user_service.service;

import com.example.user_service.dao.UserDao;
import com.example.user_service.feign.JobFeign;
import com.example.user_service.model.Job;
import com.example.user_service.model.User;
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
}
