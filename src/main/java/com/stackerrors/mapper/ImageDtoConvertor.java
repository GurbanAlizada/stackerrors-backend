package com.stackerrors.mapper;

import com.stackerrors.dtos.response.ImageDto;
import com.stackerrors.model.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageDtoConvertor {

    public ImageDto convertToImageDto(Image image){
        return ImageDto.builder()
                .url(image.getImageUrl())
                .id(image.getId())
                .publishId(image.getPublishId())
                .build();
    }


}
