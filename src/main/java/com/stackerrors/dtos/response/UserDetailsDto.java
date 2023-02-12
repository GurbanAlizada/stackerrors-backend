package com.stackerrors.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {

    private int id;

    private String username;

    private String email;

    private Date creationDate;

    private List<ErrorDto> errorDtoList;

    private List<QuestionListItemDto> questionListItemDtos;

    private List<CommentDto> commentDtos;










}
