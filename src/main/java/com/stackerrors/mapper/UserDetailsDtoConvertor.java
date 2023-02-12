package com.stackerrors.mapper;


import com.stackerrors.dtos.response.UserDetailsDto;
import com.stackerrors.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDetailsDtoConvertor {

    private final CommentDtoConvertor commentDtoConvertor;
    private final ErrorDtoConvertor errorDtoConvertor;
    private final QuestionListItemDtoConvertor questionListItemDtoConvertor;

    public UserDetailsDtoConvertor(CommentDtoConvertor commentDtoConvertor, ErrorDtoConvertor errorDtoConvertor, QuestionListItemDtoConvertor questionListItemDtoConvertor) {
        this.commentDtoConvertor = commentDtoConvertor;
        this.errorDtoConvertor = errorDtoConvertor;
        this.questionListItemDtoConvertor = questionListItemDtoConvertor;
    }

    public UserDetailsDto convert(User user){
        return UserDetailsDto.builder()
                .creationDate(user.getCreationDate())
                .email(user.getEmail())
                .username(user.getUsername())
                .commentDtos(user.getComments()
                        .stream()
                        .map(n->commentDtoConvertor.convertToCommentDto(n))
                        .collect(Collectors.toList()))
                .errorDtoList(user.getErrors()
                        .stream()
                        .map(n->errorDtoConvertor.convertToErrorDto(n))
                        .collect(Collectors.toList()))
                .questionListItemDtos(user.getQuestions()
                        .stream()
                        .map(n->questionListItemDtoConvertor.convertToQuestionListItemDto(n))
                        .collect(Collectors.toList()))
                .build();
    }


}
