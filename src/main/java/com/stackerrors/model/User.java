package com.stackerrors.model;

import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;



@Builder
@Entity
@Table(name = "users")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","questions"})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username" , unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email" , unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "creation_date")
    private Date creationDate;

    @OneToOne(
            mappedBy = "user",
            cascade = { CascadeType.PERSIST ,
                    CascadeType.MERGE ,
                    CascadeType.REMOVE }
    )
    private Image image;


    @OneToMany(mappedBy = "user"  )
    //@JsonIgnore
    private List<Question> questions;

    @OneToMany(mappedBy = "user" )
    //@JsonIgnore
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
    //@JsonIgnore
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


    // constructors
    public User(int id, String username, String password,
                String email, Role role, Date creationDate, Image image,
                List<Question> questions, List<Error> errors,
                List<Question> likesQuestions, List<Question> dissLikedQuestions,
                List<Error> likedErrors, List<Comment> comments,
                List<Comment> likes, String forgotPasswordToken,
                Date forgotPasswordTokenExpireIn, boolean isEmailVerified,
                Integer emailVerificationCode, Date emailVerificationCodeExpireIn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.creationDate = creationDate;
        this.image = image;
        this.questions = questions;
        this.errors = errors;
        this.likesQuestions = likesQuestions;
        this.dissLikedQuestions = dissLikedQuestions;
        this.likedErrors = likedErrors;
        this.comments = comments;
        this.likes = likes;
        this.forgotPasswordToken = forgotPasswordToken;
        this.forgotPasswordTokenExpireIn = forgotPasswordTokenExpireIn;
        this.isEmailVerified = isEmailVerified;
        this.emailVerificationCode = emailVerificationCode;
        this.emailVerificationCodeExpireIn = emailVerificationCodeExpireIn;
    }


    public User() {
    }


    // getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public List<Question> getLikesQuestions() {
        return likesQuestions;
    }

    public void setLikesQuestions(List<Question> likesQuestions) {
        this.likesQuestions = likesQuestions;
    }

    public List<Question> getDissLikedQuestions() {
        return dissLikedQuestions;
    }

    public void setDissLikedQuestions(List<Question> dissLikedQuestions) {
        this.dissLikedQuestions = dissLikedQuestions;
    }

    public List<Error> getLikedErrors() {
        return likedErrors;
    }

    public void setLikedErrors(List<Error> likedErrors) {
        this.likedErrors = likedErrors;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getLikes() {
        return likes;
    }

    public void setLikes(List<Comment> likes) {
        this.likes = likes;
    }

    public String getForgotPasswordToken() {
        return forgotPasswordToken;
    }

    public void setForgotPasswordToken(String forgotPasswordToken) {
        this.forgotPasswordToken = forgotPasswordToken;
    }

    public Date getForgotPasswordTokenExpireIn() {
        return forgotPasswordTokenExpireIn;
    }

    public void setForgotPasswordTokenExpireIn(Date forgotPasswordTokenExpireIn) {
        this.forgotPasswordTokenExpireIn = forgotPasswordTokenExpireIn;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public Integer getEmailVerificationCode() {
        return emailVerificationCode;
    }

    public void setEmailVerificationCode(Integer emailVerificationCode) {
        this.emailVerificationCode = emailVerificationCode;
    }

    public Date getEmailVerificationCodeExpireIn() {
        return emailVerificationCodeExpireIn;
    }

    public void setEmailVerificationCodeExpireIn(Date emailVerificationCodeExpireIn) {
        this.emailVerificationCodeExpireIn = emailVerificationCodeExpireIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
