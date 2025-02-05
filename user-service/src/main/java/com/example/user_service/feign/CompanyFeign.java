package com.example.user_service.feign;

import com.example.user_service.model.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="company-service")
public interface CompanyFeign {
    @GetMapping("/company-service/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id);

    @GetMapping("/company-service/companies/get-company-by-name/{companyName}")
    public ResponseEntity<Company> getCompanyByName(@PathVariable String companyName);
}
