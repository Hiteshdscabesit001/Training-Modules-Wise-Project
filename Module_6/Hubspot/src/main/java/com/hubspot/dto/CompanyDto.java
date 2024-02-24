package com.hubspot.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
public class CompanyDto {

    private String companyName;

    private String phoneNumber;

    private String addAdmin;

    private String poc;

    @Lob
    @Column(name = "companyLogo", columnDefinition = "LONGBLOB")
    private byte[]  companyLogo;

    private String primaryPageColor;

    private String secondaryPageColor;

    private String contractTerm;

    @CreationTimestamp
    private LocalDate contractDate;

    @UpdateTimestamp
    private LocalDate contractEndDate;

    private String customerType;

    private String contractUser;

    private String mediaCredit;

    private String activeMediaLimit;
}
