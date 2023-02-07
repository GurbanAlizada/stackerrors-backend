package com.stackerrors.controller;


import com.stackerrors.dtos.response.ImageDto;
import com.stackerrors.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@CrossOrigin
public class ImagesController {

    private final ImageService service;


    //   @PreAuthorize("#post.user.username == authentication.name")
    @DeleteMapping("/delete/{imageId}")
    public void deleteImage(
            @PathVariable("imageId") int imageId) throws IOException {
        service.deleteImage( imageId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> getImage(@PathVariable("id") int id){
        return ResponseEntity.ok(service.getImage(id));
    }



}
