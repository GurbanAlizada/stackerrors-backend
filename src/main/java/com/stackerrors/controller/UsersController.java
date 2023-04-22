package com.stackerrors.controller;

import com.stackerrors.dtos.request.ChangePasswordRequest;
import com.stackerrors.dtos.request.RegisterUserRequest;
import com.stackerrors.dtos.request.UploadProfilePhotoRequest;
import com.stackerrors.service.UpdateProfileService;
import com.stackerrors.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000" , maxAge = 3600)
public class UsersController {

    private final UserService userService;
    private final UpdateProfileService profileService;

    public UsersController(UserService userService, UpdateProfileService profilePhotoService) {
        this.userService = userService;
        this.profileService = profilePhotoService;
    }


    @PostMapping(value = "/register" ,
            consumes = {"multipart/form-data" },
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("permitAll()")
    public void register(@Valid @ModelAttribute RegisterUserRequest request){
        userService.registerUser(request);
    }


    @PostMapping(value = "/uploadProfilePhoto" ,
            consumes = {"multipart/form-data" },
            produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("isAuthenticated()")
    public void uploadProfilePhoto(@Valid @ModelAttribute UploadProfilePhotoRequest request) throws IOException {
        profileService.uploadProfilePhoto(request);
    }


    @PutMapping("/changeUsername")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("isAuthenticated()")
    public void changeUsername(@RequestParam String newUsername){
        profileService.changeUsername(newUsername);
    }


    @PutMapping("/changePassword")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("isAuthenticated()")
    public void changePassword(@RequestBody ChangePasswordRequest request){
        profileService.changePassword(request);
    }





}
