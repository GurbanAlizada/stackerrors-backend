package com.stackerrors.controller;


import com.stackerrors.dtos.request.LoginRequest;
import com.stackerrors.dtos.request.ResetPasswordRequest;
import com.stackerrors.dtos.response.TokenResponseDto;
import com.stackerrors.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequest request){

        return ResponseEntity.ok(authService.login(request));
    }



    @PostMapping("/forgotPassword")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email , HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        authService.sendForgotPasswordEmail(email , request);
        return  ResponseEntity.status(HttpStatus.OK).body("Check your mail");
    }



    @PostMapping("/resetPassword")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?>  resetPassword(@RequestBody @Valid ResetPasswordRequest request){
        authService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }


    @PostMapping("/sendVerificationCode")
    @PreAuthorize("isAuthenticated()")
    //   @PreAuthorize("#post.user.username == authentication.name")
    public ResponseEntity<?> sendVerificationCode(String email) throws MessagingException, UnsupportedEncodingException {
         authService.sendEmailVerificationCode(email);
         return ResponseEntity.status(HttpStatus.OK).body("Check your mail");
    }

    @GetMapping("/verifyEmail")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> verifyEmail(@RequestParam("verificationCode") Integer verificationCode){
         authService.verifyEmail(verificationCode);
         return ResponseEntity.status(HttpStatus.OK).body("Your account has been verified");
    }

    

}
