package com.example.user_service.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.List;

@Entity
@Table(name = "tbl_company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;

    @Column(columnDefinition = "json")
    private String jobIds;  // Store job IDs as JSON string

    @Column(columnDefinition = "json")
    private String reviewIds;  // Store review IDs as JSON string

    public Company() {
    }

    public Company(Long id, String name, String description, List<Long> jobIds, List<Long> reviewIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        setJobIds(jobIds);  // Convert List to JSON
        setReviewIds(reviewIds);  // Convert List to JSON
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    // Convert JSON string to List<Long>
    public List<Long> getJobIds() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jobIds, new TypeReference<List<Long>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convert List<Long> to JSON string
    public void setJobIds(List<Long> jobIds) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.jobIds = mapper.writeValueAsString(jobIds);
        } catch (IOException e) {
            e.printStackTrace();
            this.jobIds = "[]"; // Default empty array
        }
    }

    // Convert JSON string to List<Long>
    public List<Long> getReviewIds() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(reviewIds, new TypeReference<List<Long>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convert List<Long> to JSON string
    public void setReviewIds(List<Long> reviewIds) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.reviewIds = mapper.writeValueAsString(reviewIds);
        } catch (IOException e) {
            e.printStackTrace();
            this.reviewIds = "[]"; // Default empty array
        }
    }

}
