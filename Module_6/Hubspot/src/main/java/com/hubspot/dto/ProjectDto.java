package com.hubspot.dto;

import lombok.Data;

@Data
public class ProjectDto {

    private Long id;

    private String projectName;

    private String user;

    private String active;

    private String sent;

    private String template;

    private String ctr;
}
