package com.stackerrors.dtos.response;

import lombok.Builder;

import java.util.Date;
import java.util.List;


@Builder
public class QuestionDto {

    private int questionId;

    private String title;

    private String text;

    private List<String> tagNames ;

    private boolean isActive;

    private boolean answered;

    private List<QuestionCommentDto> comments;

    private List<ImageDto> images;

    private Date creationDate;

    private Date updateDate;

    private Integer views;

    private int answerCount;

    private UserDto userDto;


    private int likeCount;

    private int dissLikeCount;


    public QuestionDto(int questionId, String title, String text,
                       List<String> tagNames, boolean isActive, boolean answered,
                       List<QuestionCommentDto> comments, List<ImageDto> images,
                       Date creationDate, Date updateDate, Integer views,
                       int answerCount, UserDto userDto, int likeCount , int dissLikeCount) {
        this.questionId = questionId;
        this.title = title;
        this.text = text;
        this.tagNames = tagNames;
        this.isActive = isActive;
        this.answered = answered;
        this.comments = comments;
        this.images = images;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.views = views;
        this.answerCount = answerCount;
        this.userDto = userDto;
        this.likeCount = likeCount;
        this.dissLikeCount = dissLikeCount;
    }


    public QuestionDto() {
    }


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public List<QuestionCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<QuestionCommentDto> comments) {
        this.comments = comments;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
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

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }


    public int getDissLikeCount() {
        return dissLikeCount;
    }

    public void setDissLikeCount(int dissLikeCount) {
        this.dissLikeCount = dissLikeCount;
    }
}
