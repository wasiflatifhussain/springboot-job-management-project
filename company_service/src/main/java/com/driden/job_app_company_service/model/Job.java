package com.driden.job_app_company_service.model;

import jakarta.persistence.*;

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

    public Job() {
    }

    public Job(Long id, String location, String maxSalary, String minSalary, String description, String title, Long companyId) {
        this.id = id;
        this.location = location;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
        this.description = description;
        this.title = title;
        this.companyId = companyId;
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

}
