package com.hubspot.controller;

import com.hubspot.dto.CompanyDto;
import com.hubspot.entity.Company;
import com.hubspot.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("Add-Company")
public class CompanyController {

    @Autowired
    private ICompanyService iCompanyService;

    @PostMapping("create-company")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createCompany(@RequestBody CompanyDto companyDto) {
        try {
            iCompanyService.createCompany(companyDto);
            return ResponseEntity.ok("Company Added successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add company.");
        }
    }

    @PostMapping("upload")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {
        try {
            iCompanyService.saveImage(file);
            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }


    @GetMapping("fetch-company/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> fetchCompany(@RequestParam("id") Long id) {
        try {
            iCompanyService.fetchCompany(id);
            return ResponseEntity.ok("Company Details fetch Successfully!!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch Company Details.");
        }

    }

    @GetMapping("fetch-all-company")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUB_ADMIN')")
    public ResponseEntity<Company> fetchAllUsers() {

        try {

            List<Company> list = iCompanyService.fetchAllCompanies();

            if (list.isEmpty()) {
                return new ResponseEntity("User details are not present..", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(list, HttpStatus.FOUND);
            }


        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity("This is Bad Request..", HttpStatus.BAD_REQUEST);

        }

    }

    @PutMapping("update-company-detail/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateCompanyDetails(@RequestParam("id") Long id) {
        try {
            iCompanyService.updateCompanyDetails(id);
            return ResponseEntity.ok("Company Details updated Successfully!!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Company Details.");
        }
    }

    @DeleteMapping("delete-company/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteCompany(@RequestParam("id") Long id) {
        try {
            iCompanyService.deleteCompany(id);
            return ResponseEntity.ok("Delete Company Successfully!!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete Company.");
        }
    }


}
