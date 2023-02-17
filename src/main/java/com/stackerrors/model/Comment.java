package com.stackerrors.model;

import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Builder
@Entity
@Table(name = "comments")
public class Comment implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;


    @Column(name = "is_verified")
    private boolean isVerified;


    @Column(name = "creation_date")
    private Date creationDate;

    private Date updateDate;


    @OneToMany(mappedBy = "comment" , cascade = {CascadeType.PERSIST , CascadeType.MERGE,  CascadeType.REMOVE})
    private List<Image> commentImages;



    @ManyToMany
    @JoinTable(
            name = "comment_likes",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedCommentUsers ;


    // all and no args constructors
    public Comment(int id, String text, User user, Question question,
                   boolean isVerified, Date creationDate, Date updateDate,
                   List<Image> commentImages, List<User> likedUsers) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.question = question;
        this.isVerified = isVerified;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.commentImages = commentImages;
        this.likedCommentUsers = likedUsers;
    }

    public Comment() {
    }


    // getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<Image> getCommentImages() {
        return commentImages;
    }

    public void setCommentImages(List<Image> commentImages) {
        this.commentImages = commentImages;
    }

    public List<User> getLikedUsers() {
        return likedCommentUsers;
    }

    public void setLikedUsers(List<User> likedUsers) {
        this.likedCommentUsers = likedUsers;
    }
}
