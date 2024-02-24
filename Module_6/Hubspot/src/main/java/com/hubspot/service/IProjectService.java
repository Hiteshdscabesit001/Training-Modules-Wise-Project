package com.hubspot.service;

import com.hubspot.dto.ProjectDto;
import com.hubspot.entity.Project;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IProjectService {
    void addNewProject(ProjectDto projectDto) throws IOException;

    Optional<Project> fetchProject(Long id);

    List<Project> fetchAllProjects();

    boolean updateProject(ProjectDto projectDto, Long id);

    boolean deleteProject(Long id);
}
