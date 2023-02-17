package com.stackerrors.service;


import com.stackerrors.adapters.inter.CloudServiceInter;
import com.stackerrors.dtos.response.ImageDto;
import com.stackerrors.exception.ErrorCode;
import com.stackerrors.exception.GenericException;
import com.stackerrors.model.Image;
import com.stackerrors.model.User;
import com.stackerrors.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.Optional;


@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final CloudServiceInter cloudServiceInter;

    public ImageService(ImageRepository imageRepository,
                        @Qualifier("cloudinaryServiceImpl") CloudServiceInter cloudServiceInter) {
        this.imageRepository = imageRepository;
        this.cloudServiceInter = cloudServiceInter;
    }



    public void deleteImage(int id) throws IOException {
        Image image = getByImageId(id);
        String publishId = image.getPublishId();
        cloudServiceInter.deleteImage(publishId);
        imageRepository.delete(image);
    }




    public ImageDto getImage(int id){
        final Image fromDb = getByImageId(id);
        final ImageDto dto = ImageDto.builder()
                .id(fromDb.getId())
                //.questionId(fromDb.getQuestion().getId())
                .url(fromDb.getImageUrl())
                .publishId(fromDb.getPublishId())
                .build();

        return dto;
    }







    protected Image getByImageId(int id){
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
