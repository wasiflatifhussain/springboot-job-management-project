package com.example.user_service.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.List;

@Entity
@Table(name = "tbl_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String email;
    private String education;
    private String experience;
    private Long currentCompanyId;

    @Column(columnDefinition = "json")
    private String jobIdsForJobsApplied;  // Store job IDs as JSON string

    public User() {
    }

    public User(Long id, String name, String email, String education, String experience, Long currentCompanyId, List<Long> jobIdsForJobsApplied) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.education = education;
        this.experience = experience;
        this.currentCompanyId = currentCompanyId;
        setJobIdsForJobsApplied(jobIdsForJobsApplied);  // Convert List to JSON
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    // Convert JSON string to List<Long>
    public List<Long> getJobIdsForJobsApplied() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jobIdsForJobsApplied, new TypeReference<List<Long>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convert List<Long> to JSON string
    public void setJobIdsForJobsApplied(List<Long> jobIdsForJobsApplied) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.jobIdsForJobsApplied = mapper.writeValueAsString(jobIdsForJobsApplied);
        } catch (IOException e) {
            e.printStackTrace();
            this.jobIdsForJobsApplied = "[]"; // Default empty array
        }
    }

    public Long getCurrentCompanyId() {
        return currentCompanyId;
    }

    public void setCurrentCompanyId(Long currentCompanyId) {
        this.currentCompanyId = currentCompanyId;
    }
}
