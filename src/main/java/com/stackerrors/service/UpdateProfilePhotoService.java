package com.stackerrors.service;


import com.stackerrors.adapters.inter.CloudServiceInter;
import com.stackerrors.dtos.request.UploadProfilePhotoRequest;
import com.stackerrors.model.Image;
import com.stackerrors.model.User;
import com.stackerrors.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Service
public class UpdateProfilePhotoService {


    private final UserRepository userRepository;
    private final AuthService authService;
    private final CloudServiceInter cloudServiceInter;
    private final ImageService imageService;

    public UpdateProfilePhotoService(UserRepository userRepository,
                                     AuthService authService,
                                @Qualifier("cloudinaryServiceImpl") CloudServiceInter cloudServiceInter,
                                     ImageService imageService) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.cloudServiceInter = cloudServiceInter;
        this.imageService = imageService;
    }


    @Transactional
    public void uploadProfilePhoto(UploadProfilePhotoRequest request) throws IOException {


         User user = authService.getAuthenticatedUser();



        if( user.getImage()==null  &&  request.getProfilePhoto() != null){

            Map<String, String> url = cloudServiceInter.uploadImage(request.getProfilePhoto());

            Image image = Image.builder().imageUrl(url.get("secure_url")).build();
            user.setImage(image);

//            Map<String, String> cloudData = cloudServiceInter.uploadImage(request.getProfilePhoto());
//            final String imageUrl = cloudData.get("secure_url");
//            final String publishId = cloudData.get("public_id");
//            Image pp = Image.builder()
//                    .publishId(publishId)
//                    .imageUrl(imageUrl)
//                    .user(user)
//                    .build();
//            user.setImage(pp);
//
//            userRepository.save(user);


        }else if (user.getImage() != null  && request.getProfilePhoto() != null){


          //  awsImageService.deleteImg(1);
            String imageUrl = user.getImage().getImageUrl();
            cloudServiceInter.deleteImage(imageUrl);
            Map<String, String> url = cloudServiceInter.uploadImage(request.getProfilePhoto());
            Image image = Image.builder().imageUrl(url.get("secure_url")).user(user).build();
            user.setImage(image);

//            imageService.deleteImage(user.getImage().getId());
//
//            Map<String, String> cloudData = cloudServiceInter.uploadImage(request.getProfilePhoto());
//            final String imageUrl = cloudData.get("secure_url");
//            final String publishId = cloudData.get("public_id");
//            Image pp = Image.builder()
//                    .publishId(publishId)
//                    .imageUrl(imageUrl)
//                    .user(user)
//                    .build();
//            user.setImage(pp);

        }



        userRepository.save(user);
    }






}
