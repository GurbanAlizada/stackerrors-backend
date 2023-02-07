package com.stackerrors.dtos.request;


import lombok.Builder;
import javax.validation.constraints.NotBlank;


@Builder
public class LoginRequest {


    @NotBlank
    private String username;

    @NotBlank
    private String password;


    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {
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

}
