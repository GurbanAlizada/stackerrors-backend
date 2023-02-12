package com.stackerrors.service;

import com.stackerrors.adapters.impl.AwsServiceImpl;
import com.stackerrors.adapters.inter.CloudServiceInter;
import com.stackerrors.dtos.request.RegisterUserRequest;
import com.stackerrors.dtos.response.UserDetailsDto;
import com.stackerrors.exception.ErrorCode;
import com.stackerrors.exception.GenericException;
import com.stackerrors.mapper.UserDetailsDtoConvertor;
import com.stackerrors.model.Image;
import com.stackerrors.model.Role;
import com.stackerrors.model.User;
import com.stackerrors.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CloudServiceInter cloudServiceInter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AwsServiceImpl awsServiceImpl;
    private final UserDetailsDtoConvertor userDetailsDtoConvertor;


    public UserService(UserRepository userRepository,
                       @Qualifier("cloudinaryServiceImpl") CloudServiceInter cloudServiceInter,
                       BCryptPasswordEncoder bCryptPasswordEncoder, AwsServiceImpl awsServiceImpl, UserDetailsDtoConvertor userDetailsDtoConvertor) {
        this.userRepository = userRepository;
        this.cloudServiceInter = cloudServiceInter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.awsServiceImpl = awsServiceImpl;
        this.userDetailsDtoConvertor = userDetailsDtoConvertor;
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
                .creationDate(new Date(System.currentTimeMillis()))
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







    public UserDetailsDto findByUserId(int userId){

        User user = findById(userId);
        UserDetailsDto result = userDetailsDtoConvertor.convert(user);
        return result;
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
