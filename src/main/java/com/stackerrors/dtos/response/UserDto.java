package com.stackerrors.dtos.response;

import lombok.Builder;


@Builder

public class UserDto {

    private int id;

    private String username;

    private String email;

    private String profilePhotoUrl;


    public UserDto(int id, String username, String email, String profilePhotoUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public UserDto() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }
}
