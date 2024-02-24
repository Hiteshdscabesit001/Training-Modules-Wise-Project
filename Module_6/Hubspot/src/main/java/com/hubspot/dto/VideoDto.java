package com.hubspot.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
public class VideoDto {

    private String templateName;

    private String type;

    @CreationTimestamp
    private LocalDate createdOn;

    private String categories;

    private String sent;

    private Long active;

    private String staticUrl;

    private String dynamicUrl;

    private String header;

    private String bannerText;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Lob
    @Column(name = "video", columnDefinition = "LONGBLOB")
    private byte[] video;
}
