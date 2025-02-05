package com.driden.job_app_company_service.controller;


import com.driden.job_app_company_service.model.Company;
import com.driden.job_app_company_service.model.Job;
import com.driden.job_app_company_service.model.Review;
import com.driden.job_app_company_service.model.User;
import com.driden.job_app_company_service.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company-service")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // GET /companies - get all companies
    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    // GET /companies/{id} - get company by id
    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        if (companyService.getCompanyById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(company, HttpStatus.OK);
        }
    }

    // POST /companies - add a new company
    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        try {
            Company newCompany = companyService.createCompany(company);
            if (newCompany == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(newCompany, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /companies/{id} - update company by id
    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        try {
            Company companyCheck = companyService.updateCompany(id, company);
            if (companyCheck == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(companyCheck, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE /companies/{id} - delete company by id
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        try {
            boolean deleted = companyService.deleteCompany(id);
            if (!deleted) {
                return new ResponseEntity<>("Deletion Failed", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Deletion Failed", HttpStatus.NOT_FOUND);
        }
    }

    // POST /companies/{comapnyId}/{jobId} - make a new job post (by working with job service)
    @PostMapping("/companies/create-job/{companyId}")
    public ResponseEntity<Job> createJob(@PathVariable Long companyId, @RequestBody Job job) {
        Job newJob = companyService.createJob(companyId, job);
        if (newJob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(newJob, HttpStatus.OK);
        }
    }

    // GET /companies/{companyId}/jobs - get all jobs for a company (by working with job service)
    @GetMapping("/companies/{companyId}/jobs")
    public ResponseEntity<List<Job>> getJobsByCompanyId(@PathVariable Long companyId) {
        System.out.println("CompanyController.getJobsByCompanyId");
        return new ResponseEntity<>(companyService.getJobsByCompanyId(companyId), HttpStatus.OK);
    }

    // PUT /companies/{companyId}/{jobId} - update a job post (by working with job service)
    @PutMapping("/companies/{companyId}/{jobId}")
    public ResponseEntity<Job> updateJob(@PathVariable Long companyId, @PathVariable Long jobId, @RequestBody Job job) {
        Job updatedJob = companyService.updateJob(companyId, jobId, job);
        if (updatedJob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        }
    }

    // DELETE /companies/{companyId}/{jobId} - delete a job post (by working with job service)
    @DeleteMapping("/companies/{companyId}/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long companyId, @PathVariable Long jobId) {
        boolean deleted = companyService.deleteJob(companyId, jobId);
        if (!deleted) {
            return new ResponseEntity<>("Deletion Failed", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
    }

    // GET /companies/get-all-reviews/{companyId} - get all reviews for a company (by working with review service)
    @GetMapping("/companies/get-all-reviews/{companyId}")
    public ResponseEntity<List<Review>> getAllReviewsByCompanyId(@PathVariable Long companyId) {
        return new ResponseEntity<>(companyService.getReviewsByCompanyId(companyId), HttpStatus.OK);
    }

    // GET /companies/view-applications-for-job/{companyId}/{jobId} - get all applications for a job (by working with job service and user service)
    @GetMapping("/companies/view-applications-for-job/{companyId}/{jobId}")
    public ResponseEntity<List<User>> viewApplicationsForJob(@PathVariable Long companyId, @PathVariable Long jobId) {
        return new ResponseEntity<>(companyService.viewApplicationsForJob(companyId, jobId), HttpStatus.OK);
    }

    // GET /companies/get-company-by-name/{companyName} - get company by name
    @GetMapping("/companies/get-company-by-name/{companyName}")
    public ResponseEntity<Company> getCompanyByName(@PathVariable String companyName) {
        Company company = companyService.getCompanyByName(companyName);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(company, HttpStatus.OK);
        }
    }
}
