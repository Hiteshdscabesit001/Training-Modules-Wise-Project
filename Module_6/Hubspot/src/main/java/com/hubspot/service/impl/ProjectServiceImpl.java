package com.hubspot.service.impl;

import com.hubspot.dto.ProjectDto;
import com.hubspot.entity.Project;
import com.hubspot.exception.UserAlreadyExistsException;
import com.hubspot.repository.ProjectRepository;
import com.hubspot.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Add New Project
    @Override
    public void addNewProject(ProjectDto projectDto) throws IOException {

        Project project = new Project();
        Optional<Project> optionalVideo = projectRepository.findById(projectDto.getId());

        if (optionalVideo.isPresent()) {
            throw new UserAlreadyExistsException("Project already with given video id");
        }

        Project savedVideo = projectRepository.save(project);
        projectRepository.save(createNewUser(savedVideo));

    }

    // Video created
    private Project createNewUser(Project project) {
        Project newProject = new Project();

        newProject.setProjectName(project.getProjectName());
        newProject.setUser(project.getUser());
        newProject.setSent(project.getSent());
        newProject.setSent(project.getSent());
        newProject.setActive(project.getActive());
        newProject.setCtr(project.getCtr());

        return newProject;
    }

    // get project by id
    @Override
    public Optional<Project> fetchProject(Long id) {
        return projectRepository.findById(id);
    }

    // get all projects details
    @Override
    public List<Project> fetchAllProjects() {
        return projectRepository.findAll();
    }

    // update project details by id
    @Override
    public boolean updateProject(ProjectDto projectDto, Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setProjectName(projectDto.getProjectName());
            project.setUser(projectDto.getUser());
            project.setSent(projectDto.getSent());
            project.setActive(projectDto.getActive());
            project.setTemplate(projectDto.getTemplate());
            project.setCtr(projectDto.getCtr());
        }
        return true;
    }

    @Override
    public boolean deleteProject(Long id) {
        projectRepository.findById(id);
        return true;
    }


}
