package com.stackerrors.mapper;

import com.stackerrors.dtos.response.ErrorDto;
import com.stackerrors.model.Error;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ErrorDtoConvertor {

    private final UserDtoConvertor userDtoConvertor;
    private final ImageDtoConvertor imageDtoConvertor;

    public ErrorDtoConvertor(UserDtoConvertor userDtoConvertor, ImageDtoConvertor imageDtoConvertor) {
        this.userDtoConvertor = userDtoConvertor;
        this.imageDtoConvertor = imageDtoConvertor;
    }


    public ErrorDto convertToErrorDto(Error error){
        return ErrorDto.builder()
                .creationDate(error.getCreationDate())
                .description(error.getDescription())
                .id(error.getId())
                .imageUrls(error.getErrorImages().stream()
                        .map(n->imageDtoConvertor.convertToImageDto(n))
                        .collect(Collectors.toList()))
                .solution(error.getSolution())
                .tags(error.getTags()
                        .stream()
                        .map(n->n.getTagName())
                        .collect(Collectors.toList()))
                .title(error.getTitle())
                .updatedDate(error.getUpdatedDate())
                .likedErrorUsers(error.getLikedUsers()
                        .stream()
                        .map(k->userDtoConvertor.convertToUserDto(k))
                        .collect(Collectors.toList()))
                .userDto(userDtoConvertor.convertToUserDto(error.getUser()))
                .build();
    }



}
