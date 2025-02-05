package com.driden.job_app_company_service.dao;

import com.driden.job_app_company_service.model.Company;

import java.util.List;

public interface CompanyDao {
    List<Company> getAllCompanies();
    Company getCompanyById(Long id);
    Company createCompany(Company company);
    Company updateCompany(Long id, Company company);
    boolean deleteCompany(Long id);
    boolean deleteJobIdFromCompany(Long companyId, Long jobId);
}
