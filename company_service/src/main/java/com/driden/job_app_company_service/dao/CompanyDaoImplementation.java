package com.driden.job_app_company_service.dao;

import com.driden.job_app_company_service.model.Company;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDaoImplementation implements CompanyDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Company> getAllCompanies() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Company", Company.class).getResultList();
    }

    @Override
    public Company getCompanyById(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Company found = session.get(Company.class, id);
        if (found == null) {
            return null;
        } else {
            return found;
        }
    }

    @Override
    public Company createCompany(Company company) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(company);
        return company;
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        Session session = entityManager.unwrap(Session.class);
        Company companyToUpdate = session.get(Company.class, id);
        if (companyToUpdate == null) {
            return null;
        }
        else {
            companyToUpdate.setName(company.getName());
            companyToUpdate.setDescription(company.getDescription());
            companyToUpdate.setJobIds(company.getJobIds());
            companyToUpdate.setReviewIds(company.getReviewIds());
            session.saveOrUpdate(companyToUpdate);
            return companyToUpdate;
        }
    }

    @Override
    public boolean deleteCompany(Long id) {
        Session session = entityManager.unwrap(Session.class);
        Company company = session.get(Company.class, id);
        if (company != null) {
            session.delete(company);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteJobIdFromCompany(Long companyId, Long jobId) {
        Session session = entityManager.unwrap(Session.class);
        Company company = session.get(Company.class, companyId);
        if (company != null) {
            List<Long> jobIds = company.getJobIds();
            if (jobIds.contains(jobId)) {
                jobIds.remove(jobId);
                company.setJobIds(jobIds);
                session.saveOrUpdate(company);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
