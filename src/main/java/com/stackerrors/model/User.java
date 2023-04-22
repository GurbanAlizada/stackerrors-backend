package com.stackerrors.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;




@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "creation_date")
    private Date creationDate;


    @OneToOne(mappedBy = "user")
    private Image image;


    @OneToMany(mappedBy = "user")
    private List<Question> questions;

    @OneToMany(mappedBy = "user")
    private List<Error> errors;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;


    @ManyToMany(mappedBy = "likedUsers")
    private List<Question> likesQuestions;


    @ManyToMany(mappedBy = "dissLikedUsers")
    private List<Question> dissLikedQuestions;


    @ManyToMany(mappedBy = "likedErrorUsers")
    private List<Error> likedErrors;


    @ManyToMany(mappedBy = "likedCommentUsers")
    private List<Comment> likes;


    @Column(name = "forgot_password_token")
    private String forgotPasswordToken;

    @Column(name = "forgot_password_token_expire_in")
    private Date forgotPasswordTokenExpireIn;

    @Column(name = "is_email_verified", columnDefinition = "boolean not null default false")
    private boolean isEmailVerified;

    @Column(name = "email_verification_code")
    private Integer emailVerificationCode;

    @Column(name = "email_verification_code_expire_in")
    private Date emailVerificationCodeExpireIn;

}
