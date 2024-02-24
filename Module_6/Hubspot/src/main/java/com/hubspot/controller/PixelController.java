package com.hubspot.controller;

import com.hubspot.constants.UserConstant;
import com.hubspot.dto.PixelDto;
import com.hubspot.dto.ResponseDto;
import com.hubspot.dto.VideoDto;
import com.hubspot.entity.Pixel;
import com.hubspot.entity.Video;
import com.hubspot.service.IPixelService;
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
@RequestMapping("add/pixel")
public class PixelController {

    @Autowired
    private IPixelService iPixelService;

    @PostMapping("link")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> addNewLink(@RequestBody PixelDto pixelDto) throws IOException {
        iPixelService.addNewLink(pixelDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(UserConstant.STATUS_201, UserConstant.MESSAGE_201));
    }

    @GetMapping("getPixel/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Optional<Pixel>> fetchVideo(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9])",
                                                              message = "Id Number must be different digits")

                                                      Long id) {
        Optional<Pixel> pixel = iPixelService.fetchPixel(id);
        return ResponseEntity.status(HttpStatus.OK).body(pixel);

    }

    @GetMapping("getAll/Pixels")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUB_ADMIN')")
    public ResponseEntity<Video> fetchAllVideos() {

        try {

            List<Pixel> list = iPixelService.fetchAllPixels();

            if (list.isEmpty()) {
                return new ResponseEntity("Pixel details are not present..", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(list, HttpStatus.FOUND);
            }


        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity("This is Bad Request..", HttpStatus.BAD_REQUEST);

        }

    }

    @PutMapping("updatePixel/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> updatePixelDetail(@Valid @RequestBody PixelDto pixelDto, @PathVariable Long id) {
        boolean isUpdated = iPixelService.updatePixel(pixelDto, id);
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
    public ResponseEntity<ResponseDto> deletePixelDetail(@RequestParam
                                                         @Pattern(regexp = "(^$|[0-9])",
                                                                 message = "Id Number must be different digits")
                                                         Long id) {
        boolean isDeleted = iPixelService.deletePixel(id);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstant.STATUS_200, UserConstant.STATUS_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstant.STATUS_500, UserConstant.MESSAGE_500));
        }
    }
}
