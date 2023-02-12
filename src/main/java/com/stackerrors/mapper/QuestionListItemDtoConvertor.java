package com.stackerrors.mapper;


import com.stackerrors.dtos.response.QuestionListItemDto;
import com.stackerrors.model.Question;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class QuestionListItemDtoConvertor {


    private final UserDtoConvertor userDtoConvertor;

    public QuestionListItemDtoConvertor(UserDtoConvertor userDtoConvertor) {
        this.userDtoConvertor = userDtoConvertor;
    }


    public QuestionListItemDto convertToQuestionListItemDto(Question question){

        return QuestionListItemDto.builder()
                .questionId(question.getId())
                .text(question.getDescription())
                .title(question.getTitle())
                .tagNames(question.getTags()
                        .stream()
                        .map(n->n.getTagName())
                        .collect(Collectors.toList()))
                .creationDate(question.getCreationDate())
                .updatedDate(question.getUpdateDate())
                .answerCount(question.getComments().size())
                .views(question.getViews())
                .userDto(userDtoConvertor.convertToUserDto(question.getUser()))
                .build();
    }


}
