package com.stackerrors.mapper;


import com.stackerrors.dtos.response.CommentDto;
import com.stackerrors.model.Comment;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CommentDtoConvertor {

    private final UserDtoConvertor userDtoConvertor;
    private final ImageDtoConvertor imageDtoConvertor;

    public CommentDtoConvertor(UserDtoConvertor userDtoConvertor, ImageDtoConvertor imageDtoConvertor) {
        this.userDtoConvertor = userDtoConvertor;
        this.imageDtoConvertor = imageDtoConvertor;
    }


    public CommentDto convertToCommentDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .creationDate(comment.getCreationDate())
                .isVerified(comment.isVerified())
                .questionId(comment.getQuestion().getId())
                .questionTitle(comment.getQuestion().getTitle())
                .likeCount(comment.getLikedCommentUsers().size())
                .likeUser(comment.getLikedCommentUsers()
                        .stream()
                        .map(n->userDtoConvertor.convertToUserDto(n))
                        .collect(Collectors.toList()))
                .commentImages(comment.getCommentImages()
                        .stream()
                        .map(n->imageDtoConvertor.convertToImageDto(n))
                        .collect(Collectors.toList()))
                .user(userDtoConvertor.convertToUserDto(comment.getUser()))
                .build();
    }


}
