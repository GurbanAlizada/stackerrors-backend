package com.stackerrors.service;


import com.stackerrors.dtos.request.LoginRequest;
import com.stackerrors.dtos.request.ResetPasswordRequest;
import com.stackerrors.dtos.response.TokenResponseDto;
import com.stackerrors.exception.ErrorCode;
import com.stackerrors.exception.GenericException;
import com.stackerrors.mapper.UserDtoConvertor;
import com.stackerrors.model.User;
import com.stackerrors.repository.UserRepository;
import com.stackerrors.util.StringUtil;
import com.stackerrors.util.TokenGenerator;
import com.stackerrors.util.VerificationCodeGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class AuthService {



    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;
    private final UserDtoConvertor userDtoConvertor;
    private final EmailService emailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserService userService,
                       UserRepository userRepository, TokenGenerator tokenGenerator,
                       AuthenticationManager authenticationManager,
                       UserDtoConvertor userDtoConvertor, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager;
        this.userDtoConvertor = userDtoConvertor;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public TokenResponseDto login(LoginRequest request){

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername() ,
                            request.getPassword())
            );

            return TokenResponseDto.builder()
                    .tokens(tokenGenerator.generateToken(authentication))
                    .userDto(userDtoConvertor.convertToUserDto(userService.findUserByUsername(request.getUsername())))
                    .build();

        }catch (final Exception exception){

            throw GenericException.builder()
                    .errorMessage("Istifadechi melumatlari yanlisdir : " + exception.getMessage())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .errorCode(ErrorCode.USER_NOT_FOUND)
                    .build();
        }


    }



    public void sendForgotPasswordEmail(String email , HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        User user = userRepository.findByEmail(email).orElseThrow(
                ()->GenericException.builder()
                        .errorMessage("Istifadechi tapilmadi")
                        .errorCode(ErrorCode.USER_NOT_FOUND)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build()
        );



        String token = StringUtil.generateToken();

        String url = "http://localhost:8080" + request.getRequestURI() + "/reset-password?token=" + token;

        System.out.println(url);
        updateUserForgotPasswordToken(user , token);

        emailService.sendEmail( email , url);
    }





    @Transactional
    protected User updateUserForgotPasswordToken(User user, String token) {
        user.setForgotPasswordToken(token);
        user.setForgotPasswordTokenExpireIn(new Date(System.currentTimeMillis() + 1000*60*3)); // 3 deqiqe
        return userRepository.save(user);
    }



    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        userRepository.findByForgotPasswordToken(resetPasswordRequest.getToken())
                .map(user -> updateUserPassword(user, resetPasswordRequest.getPassword()))
                .orElseThrow(()->GenericException.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("Bele bir reset password token yoxdur")
                        .build());

    }




    private User updateUserPassword(User user, String password) {
        if(user.getForgotPasswordTokenExpireIn().before(new Date(System.currentTimeMillis()))){
            throw GenericException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage("Tokenin vaxti bitmisdir")
                    .build();
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }








    public void sendEmailVerificationCode(String email) throws MessagingException, UnsupportedEncodingException {

        int verificationCode = VerificationCodeGenerator.generateVerificationCode();


        userRepository.findByEmail(email)
                .map(user -> updateEmailVerificationCode(user, verificationCode))
                .orElseThrow(()->GenericException.builder()
                        .errorCode(ErrorCode.USER_NOT_FOUND)
                        .errorMessage("Bu mailde bir kullanici bulunmuyor")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build());



        emailService.sendEmailVerificationMail(verificationCode, email);
    }


    @Transactional
    protected User updateEmailVerificationCode(User user, Integer verificationCode) {
        user.setEmailVerificationCode(verificationCode);
        user.setEmailVerificationCodeExpireIn(new Date(System.currentTimeMillis() + 1000*60*3));
        return userRepository.save(user);
    }





    public void verifyEmail(Integer verificationCode) {
        userRepository.findByEmailVerificationCode(verificationCode)
                .map(this::verifyUserEmail)
                .orElseThrow(
                        ()->GenericException
                        .builder()
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .errorMessage("Bu verification code yanlisdir veya vaxti kecmisdir")
                        .build());
    }




    private User verifyUserEmail(User user) {
        if(user.getEmailVerificationCodeExpireIn().before(new Date())){
            throw  GenericException.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .errorMessage("Bu verification code yanlisdir veya vaxti kecmisdir")
                    .build();
        }

        user.setEmailVerified(true);
        return userRepository.save(user);
    }







    protected User getAuthenticatedUser(){
        String username = ( (UserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findUserByUsername(username);
        return user;
    }




}
