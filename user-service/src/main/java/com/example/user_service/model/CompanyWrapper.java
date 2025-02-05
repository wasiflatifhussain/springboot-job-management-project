package com.example.user_service.model;

import jakarta.persistence.Entity;
import java.util.List;

public class CompanyWrapper {
    private Company company;
    private List<Job> jobs;
    private List<Review> reviews;

    public CompanyWrapper() {
    }

    public CompanyWrapper(Company company, List<Job> jobs, List<Review> reviews) {
        this.company = company;
        this.jobs = jobs;
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
