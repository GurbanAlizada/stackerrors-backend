package com.stackerrors.dtos.response;

import com.stackerrors.model.Tag;
import lombok.Builder;

import java.util.Date;
import java.util.List;



@Builder
public class ErrorDto {


    private int id;

    private String title;

    private String description;


    private String solution;


    private Date creationDate;

    private Date updatedDate;


    private List<String> imageUrls;


    private UserDto userDto;



    private List<String> tags ;


    public ErrorDto(int id, String title, String description, String solution,
                    Date creationDate, Date updatedDate, List<String> imageUrls,
                    UserDto userDto, List<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.solution = solution;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.imageUrls = imageUrls;
        this.userDto = userDto;
        this.tags = tags;
    }

    public ErrorDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
