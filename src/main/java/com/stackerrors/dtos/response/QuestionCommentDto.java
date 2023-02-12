package com.stackerrors.dtos.response;


import lombok.Builder;

import java.util.Date;
import java.util.List;


@Builder
public class QuestionCommentDto {

    private int id;

    private String text;

    private UserDto user;

    private boolean isVerified;

    private Date creationDate;

    private Date updatedDate;

    private List<ImageDto> commentImages;

    private Integer likedCount;


    public QuestionCommentDto(int id, String text, UserDto user, boolean isVerified,
                              Date creationDate, Date updatedDate,
                              List<ImageDto> commentImages, Integer likedCount) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.isVerified = isVerified;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.commentImages = commentImages;
        this.likedCount = likedCount;
    }


    public QuestionCommentDto() {
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<ImageDto> getCommentImages() {
        return commentImages;
    }

    public void setCommentImages(List<ImageDto> commentImages) {
        this.commentImages = commentImages;
    }

    public Integer getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(Integer likedCount) {
        this.likedCount = likedCount;
    }
}
