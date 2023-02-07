package com.stackerrors.controller;

import com.stackerrors.dtos.request.RegisterUserRequest;
import com.stackerrors.dtos.request.UploadProfilePhotoRequest;
import com.stackerrors.model.User;
import com.stackerrors.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/register" ,
            consumes = {"multipart/form-data" },
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("permitAll()")
    public void register(@Valid @ModelAttribute RegisterUserRequest request){
        userService.registerUser(request);
    }


    @PostMapping(value = "/uploadProfilePhoto" ,
            consumes = {"multipart/form-data" },
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
   // @PreAuthorize("isAuthenticated()")
    public void uploadProfilePhoto(@Valid @ModelAttribute UploadProfilePhotoRequest request) throws IOException {
        userService.uploadProfilePhoto(request);
    }




    @GetMapping("/getUserDetail")
   // @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getUserDetail(int id){
        return ResponseEntity.ok(userService.findByUserId(id));
    }



}
