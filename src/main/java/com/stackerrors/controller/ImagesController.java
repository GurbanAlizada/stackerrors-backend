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


//    //   @PreAuthorize("#post.user.username == authentication.name")
//    @DeleteMapping("/deleteQuestionImage/{imageId}")
//    public void deleteQuestionImage(
//            @PathVariable("imageId") int imageId) throws IOException {
//        service.deleteQuestionImage(imageId);
//    }
//
//
//    @DeleteMapping("/deleteErrorImage/{imageId}")
//    public void deleteErrorImage(
//            @PathVariable("imageId") int imageId) throws IOException {
//        service.deleteErrorImage(imageId);
//    }
//
//    @DeleteMapping("/deleteCommentImage/{imageId}")
//    public void deleteCommentImage(
//            @PathVariable("imageId") int imageId) throws IOException {
//        service.deleteCommemtImage(imageId);
//    }
//



    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> getImage(@PathVariable("id") int id){
        return ResponseEntity.ok(service.getImage(id));
    }



}
