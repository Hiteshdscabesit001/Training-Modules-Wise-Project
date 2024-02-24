package com.hubspot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
