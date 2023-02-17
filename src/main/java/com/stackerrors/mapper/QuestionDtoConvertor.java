package com.stackerrors.mapper;


import com.stackerrors.dtos.response.QuestionCommentDto;
import com.stackerrors.dtos.response.QuestionDto;
import com.stackerrors.model.Comment;
import com.stackerrors.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionDtoConvertor {


    private final UserDtoConvertor userDtoConvertor;
    private final ImageDtoConvertor imageDtoConvertor;

    public QuestionDtoConvertor(UserDtoConvertor userDtoConvertor,
                                ImageDtoConvertor imageDtoConvertor) {
        this.userDtoConvertor = userDtoConvertor;
        this.imageDtoConvertor = imageDtoConvertor;
    }


    public QuestionDto convertToQuestionDto(Question question){
        return  QuestionDto.builder()
                .answered(question.isAnswered())
                .creationDate(question.getCreationDate())
                .updateDate(question.getUpdateDate())
                .isActive(question.isActive())
                .questionId(question.getId())
                .text(question.getDescription())
                .images(question.getQuestionImages()
                        .stream()
                        .map(n->imageDtoConvertor.convertToImageDto(n))
                        .collect(Collectors.toList()))
                .comments(convertToQuestionCommentDtoList(question.getComments()))
                .tagNames(question.getTags()
                        .stream()
                        .map(n->n.getTagName())
                        .collect(Collectors.toList()))
                .title(question.getTitle())
                .answerCount(question.getComments().size())
                .views(question.getViews())
                .likeCount(question.getLikedUsers().size())
                .dissLikeCount(question.getDissLikedUsers().size())
                .updateDate(question.getUpdateDate())
                .userDto(userDtoConvertor.convertToUserDto(question.getUser()))
                .build();
    }






    public List<QuestionCommentDto> convertToQuestionCommentDtoList(List<Comment> comments){
        return comments
                .stream()
                .map(n->QuestionCommentDto.builder()
                        .id(n.getId())
                        .commentImages(n.getCommentImages()
                                .stream()
                                .map(m->imageDtoConvertor.convertToImageDto(m))
                                .collect(Collectors.toList()))
                        .text(n.getText())
                        .isVerified(n.isVerified())
                        .creationDate(n.getCreationDate())
                        .updatedDate(n.getUpdateDate())
                        .likedCount(n.getLikedUsers().size())
                        .user(userDtoConvertor.convertToUserDto(n.getUser()))
                        .build())
                .collect(Collectors.toList());
    }





}
