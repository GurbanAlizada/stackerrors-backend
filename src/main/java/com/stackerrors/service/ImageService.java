package com.stackerrors.service;


import com.stackerrors.adapters.inter.CloudServiceInter;
import com.stackerrors.dtos.response.ImageDto;
import com.stackerrors.exception.ErrorCode;
import com.stackerrors.exception.GenericException;
import com.stackerrors.model.Image;
import com.stackerrors.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final CloudServiceInter cloudServiceInter;


    @Transactional
    public void deleteImage( int imageId) throws IOException {
        final Image fromDb = findById(imageId);
        imageRepository.delete(fromDb);
        cloudServiceInter.deleteImage(fromDb.getPublishId());
    }


    public ImageDto getImage(int id){
        final Image fromDb = findById(id);
        final ImageDto dto = ImageDto.builder()
                .id(fromDb.getId())
                //.questionId(fromDb.getQuestion().getId())
                .url(fromDb.getImageUrl())
                .publishId(fromDb.getPublishId())
                .build();

        return dto;
    }







    protected Image findById(int id){
        final Image image = imageRepository.findById(id).orElseThrow(
                () ->
                        GenericException.builder()
                                .errorMessage("image not found")
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .errorCode(ErrorCode.IMAGE_NOT_FOUND)
                                .build()
        );
        return image;
    }




}
