package com.hubspot.service;

import com.hubspot.dto.CompanyDto;
import com.hubspot.entity.Company;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ICompanyService {
    void createCompany(CompanyDto companyDto) throws IOException;

    void saveImage(MultipartFile file) throws IOException;

    Optional<Company> fetchCompany(Long id);

    List<Company> fetchAllCompanies();

    Optional<Company> updateCompanyDetails(Long id);

    Optional<Company> deleteCompany(Long id);
}
