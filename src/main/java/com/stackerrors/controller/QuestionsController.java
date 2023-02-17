package com.stackerrors.controller;

import com.stackerrors.dtos.request.AskQuestionRequest;
import com.stackerrors.dtos.request.UpdateQuestionRequest;
import com.stackerrors.dtos.request.UploadImageRequest;
import com.stackerrors.dtos.response.QuestionDto;
import com.stackerrors.dtos.response.QuestionListItemDto;
import com.stackerrors.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/question")
@CrossOrigin(origins = "http://localhost:3000" , maxAge = 3600)
public class QuestionsController {


    private final QuestionService service;

    public QuestionsController(QuestionService service) {
        this.service = service;
    }


    @PostMapping(value = "/askQuestion" , consumes = {"multipart/form-data" },
            produces = "application/json" )
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public void askQuestion(@Valid @ModelAttribute AskQuestionRequest request) {
        service.askQuestion(request);
    }


    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public void updateQuestion ( @Valid @RequestBody UpdateQuestionRequest request){
        service.updateQuestion(request);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/uploadImage"  , consumes = {"multipart/form-data" },
            produces = "application/json")
    @PreAuthorize("@questionService.auth( authentication.name , #request.questionId) ")
    public void uploadImage(@Valid @ModelAttribute UploadImageRequest request){
        service.uploadImage(request);
    }


    @DeleteMapping("/deleteImage")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@questionService.auth( authentication.name , #questionId)")
    public void deleteImage(@RequestParam int imageId , @RequestParam int questionId) throws IOException {
        service.deleteImage(imageId, questionId);
    }



    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("isAuthenticated()")
    public void deleteQuestion(@PathVariable("id") int id){
        service.deleteQuestion(id);
    }


    @GetMapping("/questions")
    //@PreAuthorize("permitAll()")
    public ResponseEntity<List<QuestionListItemDto>> getAllQuestions(
            @RequestParam("pageNo") int pageNo ,
            @RequestParam("size") int size){
        return ResponseEntity.ok(service.getAllQuestions(pageNo, size));
    }

    @GetMapping("/question/{id}")
   // @PreAuthorize("permitAll()")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable("id") int id){
        return ResponseEntity.ok(service.getQuestionById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/setActive")
    //@PreAuthorize("isAuthenticated()")
    public void setActive(
            @RequestParam("questionId") int questionId ,
            @RequestParam("state") boolean state){
        service.setActive(questionId, state);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/setAnswered")
    //@PreAuthorize("isAuthenticated()")
    public void setAnswered(  @RequestParam("questionId") int questionId ,
                              @RequestParam("state") boolean state){
        service.setAnswered(questionId, state);
    }



    @GetMapping("/getQuestionsAnsweredTrue")
    //@PreAuthorize("permitAll()")
    public ResponseEntity<List<QuestionListItemDto>> getQuestionsAnsweredTrue(@RequestParam("pageNo") int pageNo ,
                                                                              @RequestParam("size") int size){

        return ResponseEntity.ok(service.getQuestionsAnsweredTrue(pageNo, size));
    }

    @GetMapping("/getQuestionsAnsweredFalse")
    //@PreAuthorize("permitAll()")
    public ResponseEntity<List<QuestionListItemDto>> getQuestionsAnsweredFalse(@RequestParam("pageNo") int pageNo ,
                                                                              @RequestParam("size") int size){

        return ResponseEntity.ok(service.getQuestionsAnsweredFalse(pageNo, size));
    }



    @GetMapping("/getQuestionsActiveTrue")
    //@PreAuthorize("permitAll()")
    public ResponseEntity<List<QuestionListItemDto>> getQuestionsActiveTrue(@RequestParam("pageNo") int pageNo ,
                                                                            @RequestParam("size") int size){
        return ResponseEntity.ok(service.getQuestionsActiveTrue(pageNo, size));

    }


    @GetMapping("/getQuestionsActiveFalse")
    // @PreAuthorize("permitAll()")
    public ResponseEntity<List<QuestionListItemDto>> getQuestionsActiveFalse(@RequestParam("pageNo") int pageNo ,
                                                                            @RequestParam("size") int size){
        return ResponseEntity.ok(service.getQuestionsActiveFalse(pageNo, size));

    }



    @GetMapping("/creationDateOrderByAsc")
    // @PreAuthorize("permitAll()")
    public ResponseEntity<List<QuestionListItemDto>> creationDateOrderByAsc(@RequestParam("pageNo") int pageNo ,
                                                                             @RequestParam("size") int size){
        return ResponseEntity.ok(service.creationDateOrderByAsc(pageNo, size));

    }



    @GetMapping("/creationDateOrderByDesc")
    //  @PreAuthorize("permitAll()")
    public ResponseEntity<List<QuestionListItemDto>> creationDateOrderByDesc(@RequestParam("pageNo") int pageNo ,
                                                                             @RequestParam("size") int size){
        return ResponseEntity.ok(service.creationDateOrderByDesc(pageNo, size));

    }


    @GetMapping("/getAllQuestionsByUserId")
    public ResponseEntity<List<QuestionListItemDto>> getAllQuestionsByUserId(int pageNo , int pageSize){
        return ResponseEntity.ok(service.getAllQuestionByUserId(pageNo, pageSize));
    }

    @GetMapping("/getAllMyQuestionFromDraft")
    public ResponseEntity<List<QuestionListItemDto>> getAllMyQuestionFromDraft(int pageNo , int size){
        return ResponseEntity.ok(service.getAllMyQuestionFromDraft(pageNo, size));
    }


    @PutMapping("/changeDraftFalse")
    public void changeDraftFalse(@RequestParam("questionId") int  questionId){
        service.changeDraftFalse(questionId);
    }



    @GetMapping("/findAllByTagName")
    //  @PreAuthorize("permitAll()")
    public List<QuestionListItemDto> findAllByTags_TagName(@RequestParam("name") String name){
        return service.findAllByTags_TagName(name);
    }


    @GetMapping("/search")
    //  @PreAuthorize("permitAll()")
    public ResponseEntity<List<QuestionListItemDto>> searchQuestion(@RequestParam("search") String search){
        return ResponseEntity.ok(service.searchQuestion(search));
    }


    @PutMapping("/increaseViews/{id}")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("permitAll()")
    public void increaseViews(@PathVariable("id") int questionId){
        service.increaseViews(questionId);
    }




    @GetMapping("/like")
    @ResponseStatus(HttpStatus.OK)
    public void likeQuestion( @RequestParam("questionId") int questionId){
        service.likeQuestion(questionId);
    }



    @GetMapping("/dissLike")
    @ResponseStatus(HttpStatus.OK)
    public void dissLikeQuestion(@RequestParam("questionId") int questionId) throws InterruptedException {
        service.dissLikeQuestion(questionId);
    }


    @GetMapping("/undoLike")
    @ResponseStatus(HttpStatus.OK)
    public void undoLike(@RequestParam("questionId") int questionId){
        service.undoLike(questionId);
    }


    @GetMapping("/undoDissLike")
    @ResponseStatus(HttpStatus.OK)
    public void undoDissLike(@RequestParam("questionId") int questionId){
        service.undoDissLike(questionId);
    }





}
