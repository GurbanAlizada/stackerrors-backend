package com.stackerrors.dtos.response;


import lombok.Builder;
import java.util.Map;

@Builder
public class TokenResponseDto {

    private Map<String ,String> tokens;

    private UserDto userDto;


    public TokenResponseDto(Map<String, String> tokens, UserDto userDto) {
        this.tokens = tokens;
        this.userDto = userDto;
    }

    public TokenResponseDto() {

    }


    // getter and setter
    public Map<String, String> getTokens() {
        return tokens;
    }

    public void setTokens(Map<String, String> tokens) {
        this.tokens = tokens;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }


}
