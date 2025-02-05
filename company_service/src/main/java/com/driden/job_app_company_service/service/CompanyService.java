package com.driden.job_app_company_service.service;

import com.driden.job_app_company_service.model.Company;
import com.driden.job_app_company_service.model.Job;
import com.driden.job_app_company_service.model.Review;
import com.driden.job_app_company_service.model.User;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyById(Long id);
    Company createCompany(Company company);
    Company updateCompany(Long id, Company company);
    boolean deleteCompany(Long id);

    List<Job> getJobsByCompanyId(Long companyId);
    Job updateJob(Long companyId, Long id, Job job);
    boolean deleteJob(Long companyId, Long id);
    Job createJob(Long companyId, Job job);

    List<Review> getReviewsByCompanyId(Long companyId);
    List<User> viewApplicationsForJob(Long companyId, Long jobId);

    Company getCompanyByName(String name);
}
