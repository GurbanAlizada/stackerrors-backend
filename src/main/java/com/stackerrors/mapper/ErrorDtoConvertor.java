package com.stackerrors.mapper;

import com.stackerrors.dtos.response.ErrorDto;
import com.stackerrors.model.Error;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ErrorDtoConvertor {

    private final UserDtoConvertor userDtoConvertor;

    public ErrorDtoConvertor(UserDtoConvertor userDtoConvertor) {
        this.userDtoConvertor = userDtoConvertor;
    }


    public ErrorDto convertToErrorDto(Error error){
        return ErrorDto.builder()
                .creationDate(error.getCreationDate())
                .description(error.getDescription())
                .id(error.getId())
                .imageUrls(error.getErrorImages().stream()
                        .map(n->n.getImageUrl())
                        .collect(Collectors.toList()))
                .solution(error.getSolution())
                .tags(error.getTags()
                        .stream()
                        .map(n->n.getTagName())
                        .collect(Collectors.toList()))
                .title(error.getTitle())
                .updatedDate(error.getUpdatedDate())
                .userDto(userDtoConvertor.convertToUserDto(error.getUser()))
                .build();
    }



}
