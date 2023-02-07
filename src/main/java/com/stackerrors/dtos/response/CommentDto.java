package com.stackerrors.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {


    private int id;

    private String text;

    private UserDto user;


    private int questionId;


    private String questionTitle;


    private boolean isVerified;

    private Date creationDate;


    private List<ImageDto> commentImages;


    private Integer likeCount;

    //private List<UserDto> likeUser;


}
