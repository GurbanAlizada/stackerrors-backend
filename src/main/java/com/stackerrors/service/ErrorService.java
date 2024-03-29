package com.stackerrors.service;


import com.stackerrors.adapters.inter.CloudServiceInter;
import com.stackerrors.dtos.request.UpdateErrorRequest;
import com.stackerrors.dtos.request.UploadErrorImageRequest;
import com.stackerrors.dtos.response.ErrorDto;
import com.stackerrors.exception.ErrorCode;
import com.stackerrors.exception.GenericException;
import com.stackerrors.mapper.ErrorDtoConvertor;
import com.stackerrors.model.Error;
import com.stackerrors.repository.ErrorRepository;
import com.stackerrors.dtos.request.AddErrorRequest;
import com.stackerrors.model.Image;
import com.stackerrors.model.Tag;
import com.stackerrors.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ErrorService {

    private final ErrorRepository errorRepository;
    private final TagService tagService;
    private final CloudServiceInter cloudServiceInter;
    private final AuthService authService;
    private final ErrorDtoConvertor errorDtoConvertor;
    private final ImageService imageService;

    public ErrorService(ErrorRepository errorRepository,
                        TagService tagService,
                        @Qualifier("cloudinaryServiceImpl") CloudServiceInter cloudServiceInter,
                        AuthService authService,
                        ErrorDtoConvertor errorDtoConvertor,
                        ImageService imageService) {
        this.errorRepository = errorRepository;
        this.tagService = tagService;
        this.cloudServiceInter = cloudServiceInter;
        this.authService = authService;
        this.errorDtoConvertor = errorDtoConvertor;
        this.imageService = imageService;
    }


    @Transactional
    public void addError(AddErrorRequest request){

        User user = authService.getAuthenticatedUser();
        List<Tag> tags = errorsTags(request.getTags());

        Error error = Error.builder()
                .description(request.getDescription())
                .title(request.getTitle())
                .solution(request.getSolution())
                .tags(tags)
                .updatedDate(null)
                .creationDate(new Date(System.currentTimeMillis()))
                .user(user)
                .build();

        if(request.getFiles()!=null) {
            List<Image> images = errorsImages(request.getFiles() , error);
            error.setErrorImages(images);
        }

        errorRepository.save(error);


    }



    @Transactional
    public void updateError(UpdateErrorRequest request){

        User user  = authService.getAuthenticatedUser();
        Error error = findById(request.getErrorId());


        if (user.getId() != error.getUser().getId()){
            throw GenericException.builder()
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage("Yalniz ozunun paylasdigin error uzerinde duzelis ede bilersiz")
                    .build();
        }

        error.setDescription(request.getDescription());
        error.setTitle(request.getTitle());
        error.setSolution(request.getSolution());
        error.setTags(errorsTags(request.getTags()));
        error.setUpdatedDate(new Date(System.currentTimeMillis()));
        errorRepository.save(error);
    }





    @Transactional
    public void deleteError(int errorId){
        User user = authService.getAuthenticatedUser();
        Error error = findById(errorId);

        if (user.getId() != error.getUser().getId()){
            throw GenericException.builder()
                    .errorMessage("Qardas diresme yalniz oz paylasdigin erroru sile bilersen")
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .build();
        }

        errorRepository.delete(error);
    }



    public ErrorDto getById(int id){
        Error error = findById(id);
        final var dto = errorDtoConvertor.convertToErrorDto(error);
        return dto;
    }


    public List<ErrorDto> getAll(int pageNo , int size){
        List<Error> errors = errorRepository.findAll(PageRequest.of(pageNo - 1, size)).getContent();
        List<ErrorDto> result = errors.stream().map(n -> errorDtoConvertor.convertToErrorDto(n)).collect(Collectors.toList());
        return result;
    }


    public List<ErrorDto> getAllOrderByDate(int pageNo , int size){
        Sort sort = Sort.by(Sort.Direction.DESC , "creationDate");
        List<Error> errors = errorRepository.findAll(PageRequest.of(pageNo - 1, size , sort)).getContent();
        List<ErrorDto> result = errors.stream().map(n -> errorDtoConvertor.convertToErrorDto(n)).collect(Collectors.toList());
        return result;
    }


    public List<ErrorDto> searchError(String search){
        List<Error> errors = errorRepository.getByTitleLike(search);
        List<ErrorDto> result = errors.stream().map(n -> errorDtoConvertor.convertToErrorDto(n)).collect(Collectors.toList());
        return result;
    }



    @Transactional
    public void likeError(int errorId){
        Error error = findById(errorId);
        User user  = authService.getAuthenticatedUser();
        List<User> likedUsers = error.getLikedErrorUsers();


        for (User element : likedUsers){
            if (element.getId() == user.getId() ){
                throw GenericException.builder()
                        .errorMessage("Error onsuzda like olunub")
                        .errorCode(ErrorCode.ALREADY_LIKED)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build();
            }
        }

        error.getLikedErrorUsers().add(user);
        errorRepository.save(error);

    }



    @Transactional
    public void undoLikeError(int errorId) {
        Error error = findById(errorId);
        User user  = authService.getAuthenticatedUser();
        error.getLikedErrorUsers().remove(user);
        errorRepository.save(error);
    }




    public List<ErrorDto> getAllErrorsByUserId(int pageNo , int size){
        User authenticatedUser = authService.getAuthenticatedUser();
        Sort sort = Sort.by(Sort.Direction.ASC , "creationDate");

        List<Error> errors = errorRepository.getAllByUser_Id(authenticatedUser.getId() ,  PageRequest.of(pageNo - 1, size , sort));
        List<ErrorDto> result = errors.stream().map(n -> errorDtoConvertor.convertToErrorDto(n)).collect(Collectors.toList());
        return result;
    }




    protected Error findById(int id){
        return errorRepository.findById(id).orElseThrow(()-> GenericException.builder()
                .errorMessage("Error Not Found")
                .httpStatus(HttpStatus.NOT_FOUND)
                .errorCode(ErrorCode.ERROR_NOT_FOUND)
                .build());

    }






    protected List<Image> errorsImages(List<MultipartFile> files , Error error){

        List<Image> images = new ArrayList<>();

        for (MultipartFile file : files) {
            final Map<String , String> cloudData = cloudServiceInter.uploadImage(file);
            final String imageUrl = cloudData.get("secure_url");
            final String publishId = cloudData.get("public_id");
            images.add(
                    Image.builder()
                            .imageUrl(imageUrl)
                            .publishId(publishId)
                            .error(error)
                            .build()
            );
        }
        return images;

    }





    private List<Tag> errorsTags(List<Integer> tagIDs){

        List<Tag> tags = new ArrayList<>();

        for(Integer tagId : tagIDs){
            final Tag tagFromDb = tagService.findById(tagId);
            tags.add(tagFromDb);
        }

        return tags;
    }



    @Transactional
    public void uploadErrorImage(UploadErrorImageRequest request) {
        Error error = findById(request.getErrorId());
        if(request.getFiles()!=null) {
            List<Image> images = errorsImages(request.getFiles() , error);
            error.setErrorImages(images);
        }

        errorRepository.save(error);
    }



    @Transactional
    public void deleteErrorImage(int imageId , int errorId) throws IOException {
        User user  = authService.getAuthenticatedUser();

        Error error = findById(errorId);

        if (user.getId() != error.getUser().getId()){
            throw GenericException.builder()
                    .errorMessage("Qardas diresme yalniz oz paylasdigin erroru sile bilersen")
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .build();
        }
        imageService.deleteImage(imageId);
    }



}
