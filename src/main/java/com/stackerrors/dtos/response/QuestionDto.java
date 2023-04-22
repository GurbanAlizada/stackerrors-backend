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
public class QuestionDto {

    private int questionId;

    private String title;

    private String text;

    private List<String> tagNames ;

    private boolean isActive;

    private boolean answered;

    private List<QuestionCommentDto> comments;

    private List<ImageDto> images;

    private Date creationDate;

    private Date updateDate;

    private Integer views;

    private int answerCount;

    private UserDto userDto;


    private int likeCount;

    private int dissLikeCount;


}
