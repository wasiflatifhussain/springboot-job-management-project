package com.example.user_service.dao;

import com.example.user_service.model.Job;
import com.example.user_service.model.User;

import java.util.List;

public interface UserDao {
    User getUserById(Long id);
    List<User> getUsersByIds(List<Long> userIds);
    User createUser(User user);
    User updateUser(Long id, User user);
    boolean deleteUser(Long id);
}
