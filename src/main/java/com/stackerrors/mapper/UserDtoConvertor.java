package com.stackerrors.mapper;

import com.stackerrors.dtos.response.UserDto;
import com.stackerrors.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConvertor {


    public UserDto convertToUserDto(User user){
        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .id(user.getId())
                .build();

        // NullPointer Exception
        if (user.getImage() != null){
            userDto.setProfilePhotoUrl(user.getImage().getImageUrl());
        }

        return userDto;
    }


}
