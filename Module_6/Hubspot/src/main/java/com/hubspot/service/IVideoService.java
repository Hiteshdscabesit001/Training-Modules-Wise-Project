package com.hubspot.service;

import com.hubspot.dto.VideoDto;
import com.hubspot.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IVideoService {
    void createElement(VideoDto videoDto) throws IOException;

    Optional<Video> fetchVideo(Long id);

    List<Video> fetchAllVideos();


    boolean updateVideo(VideoDto videoDto, Long id);

    boolean deleteVideo(Long id);


    void saveImage(MultipartFile file, MultipartFile file2) throws IOException;

    void updateImage(MultipartFile file, MultipartFile file2) throws IOException;
}
