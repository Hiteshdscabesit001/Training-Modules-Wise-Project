package com.hubspot.service.impl;

import com.hubspot.dto.VideoDto;
import com.hubspot.entity.Video;
import com.hubspot.exception.UserAlreadyExistsException;
import com.hubspot.repository.VideoRepository;
import com.hubspot.service.IVideoService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl2 implements IVideoService2 {

    @Autowired
    private VideoRepository videoRepository;

    // create new hvo video
    @Override
    public void createVideo(VideoDto videoDto) throws IOException {
        Video video = new Video();
        Optional<Video> optionalVideo = videoRepository.findById(videoDto.getActive());

        if (optionalVideo.isPresent()) {
            throw new UserAlreadyExistsException("Video already with given video id");
        }

        Video savedVideo = videoRepository.save(video);
        videoRepository.save(createNewUser(savedVideo));

    }


    // Video created
    private Video createNewUser(Video video) throws IOException {
        Video newVideo = new Video();

        newVideo.setTemplateName(video.getTemplateName());
        newVideo.setType(video.getType());
        newVideo.setCategories(video.getCategories());
        newVideo.setSent(video.getSent());
        newVideo.setActive(video.getActive());
        newVideo.setStaticUrl(video.getStaticUrl());
        newVideo.setDynamicUrl(video.getDynamicUrl());
        newVideo.setBannerText(video.getBannerText());
        newVideo.setHeader(video.getHeader());
        return newVideo;
    }

    // for saving image and video
    @Override
    public void saveImage(MultipartFile file, MultipartFile file2) throws IOException {
        Video video = new Video();
        video.setImage(file.getBytes());
        video.setVideo(file2.getBytes());
        videoRepository.save(video);
    }

    // for updating image and video
    @Override
    public void updateImage(MultipartFile file, MultipartFile file2) throws IOException {
        Video video = new Video();
        video.setImage(file.getBytes());
        video.setVideo(file2.getBytes());
        videoRepository.save(video);
    }

    // fetch video details by video id
    public Optional<Video> fetchVideo(@RequestParam Long id) {
        return videoRepository.findById(id);
    }

    // fetch all videos
    public List<Video> fetchAllVideos() {
        return videoRepository.findAll();
    }

    // update video details
    @Override
    public boolean updateVideo(VideoDto videoDto, Long id) {
        Optional<Video> optionalVideo = videoRepository.findById(id);

        if (optionalVideo.isPresent()) {
            Video video = optionalVideo.get();
            video.setCreatedOn(videoDto.getCreatedOn());
            video.setStaticUrl(videoDto.getStaticUrl());
            video.setDynamicUrl(videoDto.getDynamicUrl());
            video.setHeader(videoDto.getHeader());
            video.setBannerText(videoDto.getBannerText());
            video.setImage(videoDto.getImage());
            video.setVideo(videoDto.getVideo());
        }
        return true;
    }

    // delete video
    @Override
    public boolean deleteVideo(Long id) {
        videoRepository.findById(id);
        return true;
    }
}
