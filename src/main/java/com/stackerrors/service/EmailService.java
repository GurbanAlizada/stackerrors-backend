package com.stackerrors.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;


@Service
@RequiredArgsConstructor
public class  EmailService {


    private final JavaMailSender mailSender;


    public void sendEmail(String userEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("gurbanalizada01@gmail.com", "Gurban Alizada");
        helper.setTo(userEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }







    public void sendEmailVerificationMail(Integer verificationCode, String userEmail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("gurbanalizada01@gmail.com", "Gurban Alizada");
        helper.setTo(userEmail);

        String subject = "Here's the verfication code";

        String content = "Hello, friend \n" +
                "Your verification code : "+ verificationCode+
                "\nUse this code for verify account";

        helper.setSubject(subject);

        helper.setText(content);

        mailSender.send(message);
    }




}