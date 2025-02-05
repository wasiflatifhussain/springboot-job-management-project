package com.example.user_service.controller;

import com.example.user_service.model.Job;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-service")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /users/{id}: get user with id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (userService.getUserById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    // GET /users/get-users-by-ids: get all users for list of ids
    @PostMapping("/users/get-users-by-ids")
    public ResponseEntity<List<User>> getUsersByIds(@RequestBody List<Long> userIds) {
        return new ResponseEntity<>(userService.getUsersByIds(userIds), HttpStatus.OK);
    }

    // POST /users: create new user
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /users/{id}: update user with id
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User userCheck = userService.updateUser(id, user);
            return new ResponseEntity<>(userCheck, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE /users/{id}: delete user with id
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean status = userService.deleteUser(id);
        if (status) {
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not deleted", HttpStatus.NOT_FOUND);
        }
    }

    // POST /user/{id}/job-applications: get all job applications for user with id
    // use jobIdsForJobsApplied to get all jobs applied to
    @GetMapping("/users/{id}/job-applications")
    public ResponseEntity<List<Job>> getJobApplicationsForUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getJobApplicationsForUser(id), HttpStatus.OK);
    }

}
