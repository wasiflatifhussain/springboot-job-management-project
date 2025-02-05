package com.driden.job_app_company_service.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.List;

@Entity
@Table(name = "tbl_jobsdb")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Long companyId;

    @Column(columnDefinition = "json")
    private String applicantIds;  // Store applicant IDs as JSON string

    public Job() {
    }

    public Job(Long id, String location, String maxSalary, String minSalary, String description, String title, Long companyId, List<Long> applicantIds) {
        this.id = id;
        this.location = location;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
        this.description = description;
        this.title = title;
        this.companyId = companyId;
        setApplicantIds(applicantIds);  // Convert List to JSON
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    // Convert JSON string to List<Long>
    public List<Long> getApplicantIds() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(applicantIds, new TypeReference<List<Long>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convert List<Long> to JSON string
    public void setApplicantIds(List<Long> applicantIds) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.applicantIds = mapper.writeValueAsString(applicantIds);
        } catch (IOException e) {
            e.printStackTrace();
            this.applicantIds = "[]"; // Default empty array
        }
    }
}
