package com.stackerrors.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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

}
