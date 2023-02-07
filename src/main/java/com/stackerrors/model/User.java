package com.stackerrors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","questions"})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username" , unique = true)
    private String username;

    @Column(name = "password")
    private String password;


    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @OneToOne(
            mappedBy = "user",
            cascade = { CascadeType.PERSIST ,
                    CascadeType.MERGE ,
                    CascadeType.REMOVE }
    )
    //@JsonIgnore
    private Image image;

    @OneToMany(mappedBy = "user" )
    @JsonIgnore
    private List<Question> questions;

    @OneToMany(mappedBy = "user" )
    @JsonIgnore
    private List<Error> errors;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "question_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> likesQuestions;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "question_dissLikes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> dissLikedQuestions;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "errors_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "error_id")
    )
    private List<Error> likedErrors;




    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;




    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "comment_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private List<Comment> likes;


    @Column(name = "forgot_password_token")
    private String forgotPasswordToken;

    @Column(name = "forgot_password_token_expire_in")
    private Date forgotPasswordTokenExpireIn ;

    @Column(name = "is_email_verified", columnDefinition = "boolean not null default false")
    private boolean isEmailVerified;

    @Column(name = "email_verification_code")
    private Integer emailVerificationCode;

    @Column(name = "email_verification_code_expire_in")
    private Date emailVerificationCodeExpireIn ;




    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
