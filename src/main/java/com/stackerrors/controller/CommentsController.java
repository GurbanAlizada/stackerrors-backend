package com.stackerrors.controller;


import com.stackerrors.dtos.request.AddCommentRequest;
import com.stackerrors.dtos.request.UpdateCommentRequest;
import com.stackerrors.dtos.response.CommentDto;
import com.stackerrors.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@CrossOrigin
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




    @GetMapping("/like")
    public void likeComment(@RequestParam("commentId") int commentId){
        commentService.likeComment(commentId);
    }



    @PostMapping("/verifyComment")
    public void verifyComment(int commentId){
        // Yalniz questinun yiyesi bunu verify ede biler

    }



}
