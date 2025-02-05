package com.driden.job_app_company_service.service;

import com.driden.job_app_company_service.dao.CompanyDao;
import com.driden.job_app_company_service.feign.CompanyFeign;
import com.driden.job_app_company_service.model.Company;
import com.driden.job_app_company_service.model.Job;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImplementation implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyFeign companyFeign;

    @Transactional
    @Override
    public List<Company> getAllCompanies() {
        return companyDao.getAllCompanies();
    }

    @Transactional
    @Override
    public Company getCompanyById(Long id) {
        return companyDao.getCompanyById(id);
    }

    @Transactional
    @Override
    public Company createCompany(Company company) {
        return companyDao.createCompany(company);
    }

    @Transactional
    @Override
    public Company updateCompany(Long id, Company company) {
        return companyDao.updateCompany(id, company);
    }

    @Transactional
    @Override
    public boolean deleteCompany(Long id) {
        return companyDao.deleteCompany(id);
    }

    @Transactional
    @Override
    public List<Job> getJobsByCompanyId(Long companyId) {
        // first get list of job ids from CompanyDao
        // then make request to job-service to get job by id
        List<Long> jobIds = companyDao.getCompanyById(companyId).getJobIds();
        System.out.println("jobIds: " + jobIds);
        System.out.println("jobIds size: " + jobIds.size());

        ResponseEntity<List<Job>> response = companyFeign.fetchJobsByIds(jobIds);
        return response.getBody(); // Extract List<Job> from ResponseEntity
    }

    @Transactional
    @Override
    public Job updateJob(Long companyId, Long jobId, Job job) {
        // first get list of job ids from CompanyDao
        // then search if jobId is in list
        // if it is, make request to job-service to update job
        // if not, return null

        Company company = companyDao.getCompanyById(companyId);
        List<Long> jobIds = company.getJobIds();
        if (!jobIds.contains(jobId)) {
            return null;
        }

        // reset company name in job to prevent malicious changes
        job.setCompanyId(company.getId());

        ResponseEntity<Job> response = companyFeign.updateJob(jobId, job);
        if (response.getBody() == null) {
            return null;
        }
        return response.getBody();
    }

    @Transactional
    @Override
    public boolean deleteJob(Long companyId, Long id) {
        // first get list of job ids from CompanyDao
        // then search if jobId is in list
        // if it is, make request to job-service to delete job
        // if not, return false

        List<Long> jobIds = companyDao.getCompanyById(companyId).getJobIds();
        if (!jobIds.contains(id)) {
            return false;
        }

        ResponseEntity<String> response = companyFeign.deleteJob(id);
        if (response.getBody().equals("Job deleted")) {
            // remove job id from company jobids list
            boolean deleteFromJobList = companyDao.deleteJobIdFromCompany(companyId, id);
            if (!deleteFromJobList) {
                System.out.println("Job id not deleted from company job list");
                return false;
            }
            System.out.println("Job id deleted from company job list and jobs db");
            return true;
        }
        System.out.println("Job not deleted from jobs db");
        return false;
    }

    @Transactional
    @Override
    public Job createJob(Long companyId, Job job) {
        // check if company exists
        // if it does, make request to job-service to create job
        // if not, return null
        Company company = companyDao.getCompanyById(companyId);

        // reset company name in job to prevent malicious changes
        job.setCompanyId(company.getId());

        if (company == null) {
            System.out.println("Company does not exist");
            return null;
        }
        ResponseEntity<Job> response = companyFeign.createJob(job);
        if (response.getBody() == null) {
            return null;
        }
        return response.getBody();
    }


}
