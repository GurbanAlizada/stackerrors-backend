package com.stackerrors.service;


import com.stackerrors.adapters.inter.CloudServiceInter;
import com.stackerrors.dtos.request.ChangePasswordRequest;
import com.stackerrors.dtos.request.UploadProfilePhotoRequest;
import com.stackerrors.exception.GenericException;
import com.stackerrors.model.Image;
import com.stackerrors.model.User;
import com.stackerrors.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Service
public class UpdateProfileService {


    private final UserRepository userRepository;
    private final AuthService authService;
    private final CloudServiceInter cloudServiceInter;
    private final ImageService imageService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UpdateProfileService(UserRepository userRepository,
                                AuthService authService,
                                @Qualifier("cloudinaryServiceImpl") CloudServiceInter cloudServiceInter,
                                ImageService imageService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.cloudServiceInter = cloudServiceInter;
        this.imageService = imageService;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void uploadProfilePhoto(UploadProfilePhotoRequest request) throws IOException {


         User user = authService.getAuthenticatedUser();



        if( user.getImage()==null  &&  request.getProfilePhoto() != null){


            Map<String, String> cloudData = cloudServiceInter.uploadImage(request.getProfilePhoto());
            final String imageUrl = cloudData.get("secure_url");
            final String publishId = cloudData.get("public_id");
            Image pp = Image.builder()
                    .publishId(publishId)
                    .imageUrl(imageUrl)
                    .user(user)
                    .build();
            user.setImage(pp);

            userRepository.save(user);


        }else if (user.getImage() != null  && request.getProfilePhoto() != null){

            imageService.deleteImage(user.getImage().getId());

            Map<String, String> cloudData = cloudServiceInter.uploadImage(request.getProfilePhoto());
            final String imageUrl = cloudData.get("secure_url");
            final String publishId = cloudData.get("public_id");
            Image pp = Image.builder()
                    .publishId(publishId)
                    .imageUrl(imageUrl)
                    .user(user)
                    .build();
            user.setImage(pp);

        }



        userRepository.save(user);
    }



    @Transactional
    public void changeUsername(String newUsername){
        User user = authService.getAuthenticatedUser();
        User fromDb = userRepository.findByUsername(newUsername);
        if (fromDb != null){
            throw GenericException.builder()
                    .errorMessage("Bu istifadeci adi onsuzda istifade edilir")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorCode(null)
                    .build();
        }
        user.setUsername(newUsername);
        userRepository.save(user);
    }


    public void changePassword(ChangePasswordRequest request){

        User user = authService.getAuthenticatedUser();
        if (! passwordEncoder.matches(request.getOldPassword() , user.getPassword() )){
            throw GenericException.builder()
                    .errorMessage("Evvelki sifre yanlisdir")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

    }





}
