package com.hubspot.controller;

import com.hubspot.constants.UserConstant;
import com.hubspot.dto.PixelDto;
import com.hubspot.dto.ProjectDto;
import com.hubspot.dto.ResponseDto;
import com.hubspot.entity.Pixel;
import com.hubspot.entity.Project;
import com.hubspot.entity.Video;
import com.hubspot.service.IProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("add/project")
public class ProjectController {

    @Autowired
    private IProjectService iProjectService;

    @PostMapping("createProject")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> addNewProject(@RequestBody ProjectDto projectDto) throws IOException {
        iProjectService.addNewProject(projectDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(UserConstant.STATUS_201, UserConstant.MESSAGE_201));
    }

    @GetMapping("getProject/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Optional<Project>> fetchProjectById(@RequestParam
                                                              @Pattern(regexp = "(^$|[0-9])",
                                                                      message = "Id Number must be different digits")

                                                              Long id) {
        Optional<Project> project = iProjectService.fetchProject(id);
        return ResponseEntity.status(HttpStatus.OK).body(project);

    }

    @GetMapping("getAll/Project")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUB_ADMIN')")
    public ResponseEntity<Project> fetchAllProjects() {

        try {

            List<Project> list = iProjectService.fetchAllProjects();

            if (list.isEmpty()) {
                return new ResponseEntity("Project details are not present..", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(list, HttpStatus.FOUND);
            }


        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity("This is Bad Request..", HttpStatus.BAD_REQUEST);

        }

    }

    @PutMapping("updateProject/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> updateProjectDetail(@Valid @RequestBody ProjectDto projectDto, @PathVariable Long id) {
        boolean isUpdated = iProjectService.updateProject(projectDto, id);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstant.STATUS_200, UserConstant.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstant.STATUS_500, UserConstant.MESSAGE_500));
        }
    }

    @DeleteMapping("deletePixel/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> deleteProjectDetail(@RequestParam
                                                           @Pattern(regexp = "(^$|[0-9])",
                                                                   message = "Id Number must be different digits")
                                                           Long id) {
        boolean isDeleted = iProjectService.deleteProject(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstant.STATUS_200, UserConstant.STATUS_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstant.STATUS_500, UserConstant.MESSAGE_500));
        }
    }

}
