package com.stackerrors.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;
import java.util.List;


@Builder
public class CommentDto {


    private int id;

    private String text;

    private UserDto user;

    private int questionId;

    private String questionTitle;

    private boolean isVerified;

    private Date creationDate;

    private List<ImageDto> commentImages;

    private Integer likeCount;

    private List<UserDto> likeUser;



    public CommentDto(int id, String text, UserDto user, int questionId,
                      String questionTitle, boolean isVerified, Date creationDate,
                      List<ImageDto> commentImages, Integer likeCount ,
                      List<UserDto> likeUser) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.isVerified = isVerified;
        this.creationDate = creationDate;
        this.commentImages = commentImages;
        this.likeCount = likeCount;
        this.likeUser = likeUser;
    }


    public CommentDto() {
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
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

    public List<ImageDto> getCommentImages() {
        return commentImages;
    }

    public void setCommentImages(List<ImageDto> commentImages) {
        this.commentImages = commentImages;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }


    public List<UserDto> getLikeUser() {
        return likeUser;
    }

    public void setLikeUser(List<UserDto> likeUser) {
        this.likeUser = likeUser;
    }

}
