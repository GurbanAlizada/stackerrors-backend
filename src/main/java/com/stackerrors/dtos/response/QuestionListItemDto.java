package com.stackerrors.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Date;
import java.util.List;


@Builder
public class QuestionListItemDto {

    private int questionId;

    private String title;

    private String text;

    private List<String> tagNames ;

    private Date creationDate;

    private Date updatedDate;

    private Integer views;

    private int answerCount;

    private UserDto userDto;


    public QuestionListItemDto(int questionId, String title, String text,
                               List<String> tagNames, Date creationDate,
                               Date updatedDate, Integer views,
                               int answerCount, UserDto userDto) {
        this.questionId = questionId;
        this.title = title;
        this.text = text;
        this.tagNames = tagNames;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.views = views;
        this.answerCount = answerCount;
        this.userDto = userDto;
    }

    public QuestionListItemDto() {
    }


    // getter and setter
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
