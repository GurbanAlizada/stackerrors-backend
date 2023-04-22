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
public class QuestionListItemDto {

    private int questionId;

    private String title;

    private String text;

    private List<String> tagNames ;

    private Date creationDate;

    private Date updatedDate;

    private Integer views;

    private int answerCount;

    private UserDto userDto;


   }
