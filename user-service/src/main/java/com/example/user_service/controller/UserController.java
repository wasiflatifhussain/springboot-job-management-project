package com.example.user_service.controller;

import com.example.user_service.model.*;
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

    // POST /users/{id}/job-applications: get all job applications for user with id
    // use jobIdsForJobsApplied to get all jobs applied to
    @GetMapping("/users/{id}/job-applications")
    public ResponseEntity<List<Job>> getJobApplicationsForUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getJobApplicationsForUser(id), HttpStatus.OK);
    }

    // GET /user/get-company/{companyId}: get company information with help of company id
    @GetMapping("/users/get-company/{companyId}")
    public ResponseEntity<CompanyWrapper> getCompanyInfo(@PathVariable Long companyId) {
        System.out.println("Company id: " + companyId);
        return new ResponseEntity<CompanyWrapper>(userService.getCompanyInfo(companyId), HttpStatus.OK);
    }

    // GET /user/get-company-reviews/{companyId}: get company reviews with help of company id
    @GetMapping("/users/get-company-reviews/{companyId}")
    public ResponseEntity<List<Review>> getCompanyReviews(@PathVariable Long companyId) {
        return new ResponseEntity<>(userService.getCompanyReviews(companyId), HttpStatus.OK);
    }

    // GET /user/get-available-jobs : get all available jobs
    @GetMapping("/users/get-available-jobs")
    public ResponseEntity<List<Job>> getAvailableJobs() {
        return new ResponseEntity<>(userService.getAvailableJobs(), HttpStatus.OK);
    }

    // GET /user/get-job-info/{jobId}: get job information with help of job id
    @GetMapping("/users/get-job-info/{jobId}")
    public ResponseEntity<Job> getJobInfo(@PathVariable Long jobId) {
        return new ResponseEntity<>(userService.getJobById(jobId), HttpStatus.OK);
    }

    // GET /user/get-company-by-name/{companyName}: get company information with help of company name
    @GetMapping("/users/get-company-by-name/{companyName}")
    public ResponseEntity<CompanyWrapper> getCompanyByName(@PathVariable String companyName) {
        return new ResponseEntity<>(userService.getCompanyByName(companyName), HttpStatus.OK);
    }

    // GET /user/get-jobs-by-name/{jobName}: get job information with help of job name
    @GetMapping("/users/get-jobs-by-name")
    public ResponseEntity<List<Job>> getJobsByName(@RequestParam String jobName) {
        return new ResponseEntity<>(userService.getJobsByName(jobName), HttpStatus.OK);
    }

    // POST /users/leave-review/{userId}/{companyId}: leave review for company with help of user id and company id
    @PostMapping("/users/leave-review/{userId}/{companyId}")
    public ResponseEntity<Review> leaveReview(@PathVariable Long userId, @PathVariable Long companyId, @RequestBody Review review) {
        return new ResponseEntity<>(userService.leaveReview(userId, companyId, review), HttpStatus.OK);
    }

    // POST /user/apply-job/{userId}/{jobId}: apply job for user with help of user id and job id
    @PostMapping("/users/apply-job/{userId}/{jobId}")
    public ResponseEntity<Job> applyJob(@PathVariable Long userId, @PathVariable Long jobId) {
        return new ResponseEntity<>(userService.applyForJob(userId, jobId), HttpStatus.OK);
    }

    // POST /user/withdraw-application/{userId}/{jobId}: withdraw application for user with help of user id and job id
    @PostMapping("/users/withdraw-application/{userId}/{jobId}")
    public ResponseEntity<String> withdrawApplicant(@PathVariable Long userId, @PathVariable Long jobId) {
        String status = userService.withdrawApplicant(userId, jobId);
        System.out.println("Status message: " + status);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
