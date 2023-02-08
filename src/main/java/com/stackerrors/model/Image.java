package com.stackerrors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;



@Builder
@Entity
@Table(name = "images")
public class Image implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "image_url")
    private String imageUrl;


    @Column(name = "publishId")
    private String publishId;

    @ManyToOne
    @JoinColumn(name = "question_id" )
    //@JsonIgnore
    private Question question;


    @ManyToOne
    @JoinColumn(name = "comment_id")
    //@JsonIgnore
    private Comment comment;


    @ManyToOne
    @JoinColumn(name = "error_id")
    private Error error;


    @OneToOne
    @JoinColumn(name = "user_id")
    //@JsonIgnore
    private User user ;


    // all args and no args constructors
    public Image(int id, String imageUrl, String publishId, Question question,
                 Comment comment, Error error, User user) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.publishId = publishId;
        this.question = question;
        this.comment = comment;
        this.error = error;
        this.user = user;
    }

    public Image() {
    }


    // getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishId='" + publishId + '\'' +
                ", question=" + question +
                ", comment=" + comment +
                ", error=" + error +
             //   ", user=" + user +
                '}';
    }
}
