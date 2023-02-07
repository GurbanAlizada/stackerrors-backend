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
public class QuestionListItemDto {

    private int questionId;

    private String title;

    private String text;

    private List<String> tagNames ;

    private Date creationDate;

    private Integer views;

    private int answerCount;

    private UserDto userDto;



}
