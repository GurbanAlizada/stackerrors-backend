package com.stackerrors.service;


import com.stackerrors.adapters.inter.CloudServiceInter;
import com.stackerrors.dtos.request.AddCommentRequest;
import com.stackerrors.dtos.request.UpdateCommentRequest;
import com.stackerrors.dtos.response.CommentDto;
import com.stackerrors.exception.ErrorCode;
import com.stackerrors.exception.GenericException;
import com.stackerrors.mapper.CommentDtoConvertor;
import com.stackerrors.model.Comment;
import com.stackerrors.model.Image;
import com.stackerrors.model.Question;
import com.stackerrors.model.User;
import com.stackerrors.repository.CommentRepository;
import com.stackerrors.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final QuestionService questionService;
    private final CloudServiceInter cloudServiceInter;
    private final CommentDtoConvertor commentDtoConvertor;
    private final AuthService authService;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          QuestionService questionService,
                          UserService userService,
                   @Qualifier("cloudinaryServiceImpl") CloudServiceInter cloudServiceInter,
                          CommentDtoConvertor commentDtoConvertor,
                          AuthService authService, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.questionService = questionService;
        this.cloudServiceInter = cloudServiceInter;
        this.commentDtoConvertor = commentDtoConvertor;
        this.authService = authService;
        this.userRepository = userRepository;
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



    public List<CommentDto> findAllByUser_Id(){

        User user = authService.getAuthenticatedUser();

        List<Comment> comments = commentRepository.findAllByUser_Id(user.getId());

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
                            .build()
            );
        }
        return images;

    }



    @Transactional
    public void likeComment(int commentId){
        User user = authService.getAuthenticatedUser();
        Comment comment = findById(commentId);
        List<User> likedUsers = comment.getLikedUsers();

        for (User element : likedUsers ){
            if (element.getId() == user.getId()){
                throw GenericException.builder()
                        .errorCode(ErrorCode.ALREADY_LIKED)
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .errorMessage("Siz zaten like etmisiz")
                        .build();
            }
        }

        user.getLikes().add(comment);
        userRepository.save(user);
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





}
