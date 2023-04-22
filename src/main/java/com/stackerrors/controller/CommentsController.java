package com.stackerrors.controller;


import com.stackerrors.dtos.request.AddCommentRequest;
import com.stackerrors.dtos.request.UpdateCommentRequest;
import com.stackerrors.dtos.request.UploadCommentImageRequest;
import com.stackerrors.dtos.response.CommentDto;
import com.stackerrors.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000" , maxAge = 3600)
public class CommentsController {

    private final CommentService commentService;


    @PostMapping(value = "/addComment" ,
            consumes = {"multipart/form-data" },
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(@Valid @ModelAttribute AddCommentRequest request){
        commentService.addComment(request);
    }



    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/updateComment")
    public void updateComment(@Valid @RequestBody UpdateCommentRequest request){
        commentService.updateComment(request);
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/deleteComment/{id}")
    public void deleteComment(@PathVariable("id") int id){
        commentService.deleteComment(id);
    }


    @GetMapping("/getAllByUserId")
    public ResponseEntity<List<CommentDto>> getAllByUserId(@RequestParam int pageNo ,@RequestParam int size){
        return ResponseEntity.ok(commentService.findAllByUser_Id(pageNo, size));
    }




    @PostMapping("/like")
    public void likeComment(@RequestParam("commentId") int commentId){
        commentService.likeComment(commentId);
    }


    @PostMapping("/undoLike")
    public void undoLikeComment(@RequestParam int commentId){
        commentService.undoLikeCommemt(commentId);
    }


    @PostMapping("/verifyComment")
    @ResponseStatus(HttpStatus.OK)
    public void verifyComment(int questionId , int commentId){
        commentService.verifyComment(questionId, commentId);
    }


    @DeleteMapping("/deleteCommentImage")
    public void deleteCommentImage(int imageId) throws IOException {
        commentService.deleteCommentImage(imageId);
    }


    @PostMapping(value = "/uploadCommentImage" ,
            consumes = {"multipart/form-data" },
            produces = "application/json")
    public void uploadCommentImage(@ModelAttribute @Valid UploadCommentImageRequest request){
        commentService.uploadCommentImage(request);
    }



    @GetMapping("/getAllOrderByDate")
    public ResponseEntity<List<CommentDto>> getAllCommentsByQuestionIsOrderByDate(@RequestParam int questionId,@RequestParam int pageNo ,@RequestParam int size){
        return ResponseEntity.ok(commentService.getAllCommentsByQuestionIsOrderByDate(questionId ,pageNo , size));
    }



    @GetMapping("/getAllOrderByLikeCount")
    public ResponseEntity<List<CommentDto>> getAllCommentsByQuestionIsOrderByLikeCount( @RequestParam int questionId, @RequestParam int pageNo ,@RequestParam int size){
        return ResponseEntity.ok(commentService.getAllCommentsByQuestionIsOrderByLikeCount(questionId , pageNo , size));
    }

}
