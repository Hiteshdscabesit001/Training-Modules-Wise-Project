package com.hubspot.service.impl;

import com.hubspot.dto.PixelDto;
import com.hubspot.entity.Pixel;
import com.hubspot.exception.UserAlreadyExistsException;
import com.hubspot.repository.PixelRepository;
import com.hubspot.service.IPixelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class IPixelServiceImpl implements IPixelService {

    @Autowired
    private PixelRepository pixelRepository;


    // for add new link
    @Override
    public void addNewLink(PixelDto pixelDto) throws IOException {

        Pixel pixel = new Pixel();
        Optional<Pixel> optionalPixel = pixelRepository.findById(pixelDto.getId());

        if (optionalPixel.isPresent()) {
            throw new UserAlreadyExistsException("Pixel already with given video id");
        }

        Pixel savedPixel = pixelRepository.save(pixel);
        pixelRepository.save(createNewUser(savedPixel));

    }

    // Video created
    private Pixel createNewUser(Pixel pixel) throws IOException {
        Pixel newPixel = new Pixel();

        newPixel.setUrl(pixel.getUrl());
        newPixel.setUrlName(pixel.getUrlName());

        return newPixel;
    }

    //get pixel by id

    @Override
    public Optional<Pixel> fetchPixel(Long id) {
        return pixelRepository.findById(id);
    }

    // get all pixels
    @Override
    public List<Pixel> fetchAllPixels() {
        return pixelRepository.findAll();
    }

    // update pixel details
    @Override
    public boolean updatePixel(PixelDto pixelDto, Long id) {
        Optional<Pixel> optionalPixel = pixelRepository.findById(id);

        if (optionalPixel.isPresent()) {
            Pixel pixel = optionalPixel.get();
            pixel.setUrlName(pixelDto.getUrlName());
            pixel.setUrl(pixelDto.getUrl());
        }
        return true;
    }

    // delete pixel by id
    @Override
    public boolean deletePixel(Long id) {
        pixelRepository.findById(id);
        return true;
    }


}
