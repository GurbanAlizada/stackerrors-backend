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
public class QuestionCommentDto {


    private int id;

    private String text;

    private UserDto user;

    private boolean isVerified;

    private Date creationDate;

    private Date updatedDate;

    private List<ImageDto> commentImages;

    private Integer likedCount;






}
