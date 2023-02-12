package com.stackerrors.controller;


import com.stackerrors.dtos.request.AddErrorRequest;
import com.stackerrors.dtos.request.UpdateErrorRequest;
import com.stackerrors.dtos.response.ErrorDto;
import com.stackerrors.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/error")
public class ErrorsController {

    private final ErrorService errorService;

    @PostMapping(value = "/addError" , consumes = {"multipart/form-data" },
            produces = "application/json" )
    @ResponseStatus(HttpStatus.CREATED)
    // @PreAuthorize("isAuthenticated()")
    public void addError(@Valid @ModelAttribute AddErrorRequest request) {
        errorService.addError(request);
    }




    @PutMapping("/updateError")
    @ResponseStatus(HttpStatus.OK)
    public void updateError(@RequestBody @Valid UpdateErrorRequest request){
        errorService.updateError(request);
    }



    @DeleteMapping("/deleteError")
    @ResponseStatus(HttpStatus.OK)
    public void deleteError(@RequestParam("id") int id){
        errorService.deleteError(id);
    }



    @GetMapping("/getAllErrors")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ErrorDto>> getAllErrors(@RequestParam("pageNo") int pageNo , @RequestParam("size") int size){
        return ResponseEntity.ok(errorService.getAll(pageNo, size));
    }



    @GetMapping("/getErrorById")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ErrorDto> getErrorById(@RequestParam("id") int id){
        return ResponseEntity.ok(errorService.getById(id));
    }



    @GetMapping("/getAllOrderByDate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ErrorDto>> getAllOrderByDate(@RequestParam("pageNo") int pageNo ,@RequestParam("size") int size){
        return ResponseEntity.ok(errorService.getAllOrderByDate(pageNo,size));
    }


    @GetMapping("/search/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ErrorDto>> searchError(@RequestParam("search") String search){
        return ResponseEntity.ok(errorService.searchError(search));
    }



    @GetMapping("/likeError")
    @ResponseStatus(HttpStatus.OK)
    public void likeError(int errorId){
        errorService.likeError(errorId);
    }



    @GetMapping("/getAllErrorsByUserId")
    public ResponseEntity<List<ErrorDto>> getAllErrorsByUserId(@RequestParam("pageNo") int pageNo ,@RequestParam("size") int size){
        return  ResponseEntity.ok(errorService.getAllErrorsByUserId(pageNo , size));
    }


}
