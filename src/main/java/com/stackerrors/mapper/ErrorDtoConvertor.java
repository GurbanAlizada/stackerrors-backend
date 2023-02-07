package com.stackerrors.mapper;

import com.stackerrors.dtos.response.ErrorDto;
import com.stackerrors.model.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ErrorDtoConvertor {

    private final UserDtoConvertor userDtoConvertor;


    public ErrorDto convertToErrorDto(Error error){
        return ErrorDto.builder()
                .creationDate(error.getCreationDate())
                .description(error.getDescription())
                .id(error.getId())
                .imageUrls(error.getErrorImages().stream()
                        .map(n->n.getImageUrl())
                        .collect(Collectors.toList()))
                .solution(error.getSolution())
                .tags(error.getTags())
                .title(error.getTitle())
                .updatedDate(error.getUpdatedDate())
                .userDto(userDtoConvertor.convertToUserDto(error.getUser()))
                .build();
    }



}
