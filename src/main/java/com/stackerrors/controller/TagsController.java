package com.stackerrors.controller;


import com.stackerrors.dtos.request.SaveTagRequest;
import com.stackerrors.dtos.request.UpdateTagRequest;
import com.stackerrors.dtos.response.TagDto;
import com.stackerrors.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tag")
@CrossOrigin
public class TagsController {

    private final TagService tagService;


    public TagsController(TagService tagService) {
        this.tagService = tagService;
    }


    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody SaveTagRequest request){
         tagService.save(request);
    }



    @GetMapping("/getById/{id}")
    //@PreAuthorize("permitAll()")
    public ResponseEntity<TagDto> getById(@PathVariable("id") int id){
        return ResponseEntity.ok(tagService.getById(id));
    }


    @GetMapping("/getAllTags")
    //@PreAuthorize("permitAll()")
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TagDto>> getAllTags(@RequestParam("pageNo") int pageNo ,
                                                   @RequestParam("size") int size){
        return ResponseEntity.ok(tagService.getAllTags(pageNo, size));
    }


    @GetMapping("/findByTagsOrderByName")
    //@PreAuthorize("permitAll()")
    public ResponseEntity<List<TagDto>> findByTagsOrderByName(@RequestParam("pageNo") int pageNo ,
                                                        @RequestParam("size") int size){
        return ResponseEntity.ok(tagService.findByTagsOrderByName(pageNo, size));
    }



    @DeleteMapping("/admin/delete/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable("id") int id){
       tagService.deleteTag(id);
    }


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/admin/updateTag")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public void updateTag(@RequestBody @Valid UpdateTagRequest request){
        tagService.updateTag(request);
    }




}
