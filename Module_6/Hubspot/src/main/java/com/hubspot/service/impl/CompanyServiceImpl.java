package com.hubspot.service.impl;

import com.hubspot.dto.CompanyDto;
import com.hubspot.entity.Company;
import com.hubspot.repository.CompanyRepository;
import com.hubspot.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    //    for creating/add new company
    @Override
    public void createCompany(CompanyDto companyDto) throws IOException {
        Company company = new Company();

        company.setCompanyName(companyDto.getCompanyName());
        company.setPhoneNumber(companyDto.getPhoneNumber());
        company.setAddAdmin(companyDto.getAddAdmin());
        // company.setCompanyLogo(file.getBytes());
        company.setPoc(companyDto.getPoc());
        company.setPrimaryPageColor(companyDto.getPrimaryPageColor());
        company.setSecondaryPageColor(companyDto.getSecondaryPageColor());
        company.setContractTerm(companyDto.getContractTerm());
        company.setCustomerType(companyDto.getCustomerType());
        company.setActiveMediaLimit(companyDto.getActiveMediaLimit());
        company.setMediaCredit(companyDto.getMediaCredit());
        company.setContractUser(companyDto.getContractUser());

        companyRepository.save(company);

    }

    // for image upload
    @Override
    public void saveImage(MultipartFile file) throws IOException {
        Company company = new Company();
        company.setCompanyLogo(file.getBytes());
        companyRepository.save(company);
    }

    // fetch company details by id
    @Override
    public Optional<Company> fetchCompany(Long id) {
        return companyRepository.findById(id);

    }

    // fetch all company details
    @Override
    public List<Company> fetchAllCompanies() {
        return companyRepository.findAll();
    }

    // update company details by id
    @Override
    public Optional<Company> updateCompanyDetails(Long id) {
        return companyRepository.findById(id);
    }

    // delete company by id
    @Override
    public Optional<Company> deleteCompany(Long id) {
        return companyRepository.findById(id);
    }


}
