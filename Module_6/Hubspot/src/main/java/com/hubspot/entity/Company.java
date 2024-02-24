package com.hubspot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
