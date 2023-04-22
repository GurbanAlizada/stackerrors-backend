package com.stackerrors.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private int id;

    private String username;

    private String email;

    private String profilePhotoUrl;


}
