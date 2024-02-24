package com.hubspot.service;

import com.hubspot.dto.PixelDto;
import com.hubspot.entity.Pixel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IPixelService {
    void addNewLink(PixelDto pixelDto) throws IOException;

    Optional<Pixel> fetchPixel(Long id);

    List<Pixel> fetchAllPixels();

    boolean updatePixel(PixelDto pixelDto, Long id);

    boolean deletePixel(Long id);
}
