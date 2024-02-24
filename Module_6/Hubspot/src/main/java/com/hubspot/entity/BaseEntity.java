package com.hubspot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

//    @CreatedDate
//    @Column(updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;

//    @CreatedBy
//    @Column(updatable = false)
//    private String createdBy;

//    @LastModifiedDate
//    @Column(insertable = false)
    @UpdateTimestamp
    private LocalDate updateAt;

//    @LastModifiedBy
//    @Column(insertable = false)
//    private String updatedBy;
}
