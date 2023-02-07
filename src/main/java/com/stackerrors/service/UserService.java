package com.stackerrors.service;

import com.stackerrors.adapters.inter.CloudServiceInter;
import com.stackerrors.dtos.request.RegisterUserRequest;
import com.stackerrors.dtos.request.UploadProfilePhotoRequest;
import com.stackerrors.exception.ErrorCode;
import com.stackerrors.exception.GenericException;
import com.stackerrors.model.Image;
import com.stackerrors.model.Role;
import com.stackerrors.model.User;
import com.stackerrors.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CloudServiceInter cloudServiceInter;
    private final ImageService imageService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository,
                       CloudServiceInter cloudServiceInter,
                       ImageService imageService,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.cloudServiceInter = cloudServiceInter;
        this.imageService = imageService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void registerUser(RegisterUserRequest request){


        if(loadUser(request.getUsername()) != null){
            throw GenericException.builder()
                    .errorMessage("Bu istifadechi adi onsuzda istifade edilir")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorCode(ErrorCode.ALREADY_EXISTS_USERNAME)
                    .build();
        }


        User user = User.builder()
                .creationDate(LocalDate.now())
                .email(request.getEmail())
                .username(request.getUsername())
                .role(Role.USER)
                .build();

        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));



        // form-data-dan profilPhoto tickle geldikde null pointer atir
        if(request.getProfilePhoto() != null) {
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



    public User findUserByUsername(String username){

        User fromDb  = userRepository.findByUsername(username);

        if (fromDb != null){
            return fromDb;
        }else {
            throw GenericException.builder()
                    .errorCode(ErrorCode.USER_NOT_FOUND)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .errorMessage("Istifadechi tapilmadi")
                    .build();
        }
    }



    @Transactional
    public void uploadProfilePhoto(UploadProfilePhotoRequest request) throws IOException {


        //User user = authService.getAuthenticatedUser();


        User user = null;

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



    public User findByUserId(int userId){

        User user = findById(userId);
        return user;
    }





    protected User findById(int id){
        return userRepository.findById(id)
                .orElseThrow(
                        ()->
                                GenericException.builder()
                                .errorCode(ErrorCode.USER_NOT_FOUND)
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .errorMessage("user not found")
                                .build()
                );
    }



    private User loadUser(String username){
        return userRepository.findByUsername(username);
    }


}
