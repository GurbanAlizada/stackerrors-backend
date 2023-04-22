package com.stackerrors.service;


import com.stackerrors.adapters.inter.CloudServiceInter;
import com.stackerrors.dtos.request.AddCommentRequest;
import com.stackerrors.dtos.request.UpdateCommentRequest;
import com.stackerrors.dtos.request.UploadCommentImageRequest;
import com.stackerrors.dtos.response.CommentDto;
import com.stackerrors.exception.ErrorCode;
import com.stackerrors.exception.GenericException;
import com.stackerrors.mapper.CommentDtoConvertor;
import com.stackerrors.model.Comment;
import com.stackerrors.model.Image;
import com.stackerrors.model.Question;
import com.stackerrors.model.User;
import com.stackerrors.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final QuestionService questionService;
    private final CloudServiceInter cloudServiceInter;
    private final CommentDtoConvertor commentDtoConvertor;
    private final AuthService authService;
    private final ImageService imageService;

    public CommentService(CommentRepository commentRepository,
                          QuestionService questionService,
                          @Qualifier("cloudinaryServiceImpl") CloudServiceInter cloudServiceInter,
                          CommentDtoConvertor commentDtoConvertor,
                          AuthService authService, ImageService imageService) {
        this.commentRepository = commentRepository;
        this.questionService = questionService;
        this.cloudServiceInter = cloudServiceInter;
        this.commentDtoConvertor = commentDtoConvertor;
        this.authService = authService;
        this.imageService = imageService;
    }


    @Transactional
    public void addComment(AddCommentRequest request){

        Question question = questionService.findById(request.getQuestionId());
        User user = authService.getAuthenticatedUser();

        Comment comment = Comment.builder()
                .creationDate(new Date(System.currentTimeMillis()))
                .isVerified(false)
                .question(question)
                .text(request.getText())
                .user(user)
                .updateDate(null)
                .build();


        if(request.getFiles()!=null) {
            List<Image> images = commentsImages(request.getFiles() , comment);
            comment.setCommentImages(images);
        }

        commentRepository.save(comment);

    }




    @Transactional
    public void updateComment(UpdateCommentRequest request){

        User user = authService.getAuthenticatedUser();
        Comment comment = findById(request.getCommentId());

        if (user.getId() != comment.getUser().getId()){
            throw GenericException.builder()
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .errorMessage("Yalniz oz commentini deyise bilersiniz ")
                    .build();
        }

        comment.setText(request.getText());
        comment.setUpdateDate(new Date(System.currentTimeMillis()));

        commentRepository.save(comment);
    }




    @Transactional
    public void deleteComment(int id){

        Comment comment = findById(id);
        User user = authService.getAuthenticatedUser();

        if (user.getId() != comment.getUser().getId()){
            throw GenericException.builder()
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .errorMessage("Yalniz oz commentini sile bilersiniz ")
                    .build();
        }
        commentRepository.delete(comment);
    }



    public List<CommentDto> findAllByUser_Id(int pageNo , int size){

        User user = authService.getAuthenticatedUser();
        Sort sort = Sort.by(Sort.Direction.ASC , "creationDate");


        List<Comment> comments = commentRepository.findAllByUser_Id(user.getId() ,  PageRequest.of(pageNo - 1, size , sort));

        List<CommentDto> result = comments.stream()
                .map(n->commentDtoConvertor.convertToCommentDto(n))
                .collect(Collectors.toList());
        return result;

    }









    protected List<Image> commentsImages(List<MultipartFile> files , Comment comment){

        List<Image> images = new ArrayList<>();

        for (MultipartFile file : files) {
            final Map<String , String> cloudData = cloudServiceInter.uploadImage(file);
            final String imageUrl = cloudData.get("secure_url");
            final String publishId = cloudData.get("public_id");

            images.add(
                    Image.builder()
                            .imageUrl(imageUrl)
                            .publishId(publishId)
                            .comment(comment)
                            .error(null)
                            .user(null)
                            .question(null)
                            .build()
            );
        }
        return images;

    }



    @Transactional
    public void likeComment(int commentId){
        User user = authService.getAuthenticatedUser();
        Comment comment = findById(commentId);
        List<User> likedUsers = comment.getLikedCommentUsers();

        for (User element : likedUsers ){
            if (element.getId() == user.getId()){
                throw GenericException.builder()
                        .errorCode(ErrorCode.ALREADY_LIKED)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .errorMessage("Siz zaten like etmisiz")
                        .build();
            }
        }

        comment.getLikedCommentUsers().add(user);
        commentRepository.save(comment);
    }







    protected Comment findById(int id){
        return commentRepository.findById(id).orElseThrow(
                () ->
                        GenericException.builder()
                                .errorCode(ErrorCode.COMMENT_NOT_FOUND)
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .errorMessage("comment not found")
                                .build()

        );
    }


    public void verifyComment(int questionId , int commentId){
        User user = authService.getAuthenticatedUser();
        Comment comment = findById(commentId);
        Question question = questionService.findById(questionId);

        if (question.getUser().getId() != user.getId() ){
            throw GenericException.builder()
                    .errorCode(ErrorCode.ACCESS_DENIED)
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .errorMessage("Yalniz oz commentini deyise bilersiniz ")
                    .build();
        }
        comment.setVerified(true);
        commentRepository.save(comment);
    }





    @Transactional
    public void uploadCommentImage(UploadCommentImageRequest request) {
        Comment comment = findById(request.getCommentId());

        if(request.getFiles() != null) {
            List<Image> images = commentsImages(request.getFiles() , comment);
            comment.setCommentImages(images);
            System.out.println("Deyer  :  "+images.get(0).getImageUrl());
        }

        commentRepository.save(comment);
    }


    public void deleteCommentImage(int imageId) throws IOException {
        imageService.deleteImage(imageId);
    }





    public List<CommentDto> getAllCommentsByQuestionIsOrderByLikeCount(int questionId , int pageNo, int size) {



        List<Comment> comments = commentRepository.findAllByQuestion_Id(questionId, PageRequest.of(pageNo - 1, size));
        List<CommentDto> result = comments
                .stream()
                .map(n -> commentDtoConvertor.convertToCommentDto(n))
                .sorted( (o1,o2) -> (o2.getLikeCount()).compareTo(o1.getLikeCount() ))
                .collect(Collectors.toList());
        return result;
    }


    public List<CommentDto> getAllCommentsByQuestionIsOrderByDate(int questionId , int pageNo, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC , "creationDate");
        List<Comment> comments = commentRepository.findAllByQuestion_Id(questionId, PageRequest.of(pageNo - 1, size, sort));
        List<CommentDto> result = comments
                .stream()
                .map(n -> commentDtoConvertor.convertToCommentDto(n))
                .collect(Collectors.toList());
        return result;
    }

    public void undoLikeCommemt(int commentId) {
        Comment comment = findById(commentId);
        User user = authService.getAuthenticatedUser();
        comment.getLikedCommentUsers().remove(user);
        commentRepository.save(comment);
    }


}
