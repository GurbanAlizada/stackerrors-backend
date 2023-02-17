package com.stackerrors.dtos.response;

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


    private List<ImageDto> imageUrls;


    private UserDto userDto;



    private List<String> tags ;

    private List<UserDto> likedErrorUsers;


    public ErrorDto(int id, String title, String description, String solution,
                    Date creationDate, Date updatedDate, List<ImageDto> imageUrls,
                    UserDto userDto, List<String> tags , List<UserDto> likedErrorUsers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.solution = solution;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.userDto = userDto;
        this.tags = tags;
        this.likedErrorUsers = likedErrorUsers;
        this.imageUrls = imageUrls;
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

    public List<ImageDto> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ImageDto> imageUrls) {
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

    public List<UserDto> getLikedErrorUsers() {
        return likedErrorUsers;
    }

    public void setLikedErrorUsers(List<UserDto> likedErrorUsers) {
        this.likedErrorUsers = likedErrorUsers;
    }
}
