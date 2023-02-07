package com.stackerrors.mapper;


import com.stackerrors.dtos.response.TagDto;
import com.stackerrors.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagDtoConvertor {

    public TagDto convertToTagDto(Tag tag){
        return TagDto.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .about(tag.getAbout())
                .build();

    }


}
