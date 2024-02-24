package com.hubspot.controller;

import com.hubspot.constants.UserConstant;
import com.hubspot.dto.ResponseDto;
import com.hubspot.dto.VideoDto;
import com.hubspot.entity.Video;
import com.hubspot.service.IVideoService2;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("new-hvo-template")
public class VideoController2 {

    @Autowired
    private IVideoService2 iVideoService2;


    @PostMapping("create/new-h1")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> createHvoVideo(@RequestBody VideoDto videoDto) throws IOException {
        iVideoService2.createVideo(videoDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(UserConstant.STATUS_201, UserConstant.MESSAGE_201));
    }

    @PostMapping("upload/image-h1")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> fileUpload(@RequestParam("image") MultipartFile file, @RequestParam("video") MultipartFile file2) {
        try {
            iVideoService2.saveImage(file, file2);
            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    @PutMapping("update/image-h1")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateImage(@RequestParam("image") MultipartFile file, @RequestParam("video") MultipartFile file2) {
        try {
            iVideoService2.updateImage(file, file2);
            return ResponseEntity.ok("Image updated successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    @GetMapping("fetch/video-h1")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Optional<Video>> fetchVideo(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9])",
                                                              message = "Id Number must be different digits")

                                                      Long id) {
        Optional<Video> video = iVideoService2.fetchVideo(id);
        return ResponseEntity.status(HttpStatus.OK).body(video);

    }

    @GetMapping("fetchAll/videos-h1")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUB_ADMIN')")
    public ResponseEntity<Video> fetchAllVideos() {

        try {

            List<Video> list = iVideoService2.fetchAllVideos();

            if (list.isEmpty()) {
                return new ResponseEntity("Video details are not present..", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(list, HttpStatus.FOUND);
            }


        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity("This is Bad Request..", HttpStatus.BAD_REQUEST);

        }

    }

    @PutMapping("update/video-h1/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> updateVideoDetail(@Valid @RequestBody VideoDto videoDto, @PathVariable Long id) {
        boolean isUpdated = iVideoService2.updateVideo(videoDto, id);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstant.STATUS_200, UserConstant.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstant.STATUS_500, UserConstant.MESSAGE_500));
        }
    }

    @DeleteMapping("delete/video-h1")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> deleteUserDetail(@RequestParam
                                                        @Pattern(regexp = "(^$|[0-9])",
                                                                message = "Id Number must be different digits")
                                                        Long id) {
        boolean isDeleted = iVideoService2.deleteVideo(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstant.STATUS_200, UserConstant.STATUS_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstant.STATUS_500, UserConstant.MESSAGE_500));
        }
    }
}
