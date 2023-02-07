package com.stackerrors.service;

import com.stackerrors.dtos.request.SaveTagRequest;
import com.stackerrors.dtos.request.UpdateTagRequest;
import com.stackerrors.dtos.response.TagDto;
import com.stackerrors.exception.ErrorCode;
import com.stackerrors.exception.GenericException;
import com.stackerrors.mapper.TagDtoConvertor;
import com.stackerrors.model.Tag;
import com.stackerrors.repository.TagRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagDtoConvertor tagDtoConvertor;

    public TagService(TagRepository tagRepository, TagDtoConvertor tagDtoConvertor) {
        this.tagRepository = tagRepository;
        this.tagDtoConvertor = tagDtoConvertor;
    }


    @Transactional
    public void save(SaveTagRequest request) {
        Tag tag = Tag.builder()
                .about(request.getAbout())
                .tagName(request.getTagName())
                .build();
        tagRepository.save(tag);

    }




    public List<TagDto> getAllTags(int pageNo , int size){
        List<Tag> tags = tagRepository.findAll(PageRequest.of(pageNo - 1, size)).getContent();
        List<TagDto> result = tags.stream()
                .map(n->tagDtoConvertor.convertToTagDto(n))
                .collect(Collectors.toList());
        return result;
    }


    public TagDto getById(int id){
        final Tag tag = findById(id);
        TagDto result = tagDtoConvertor.convertToTagDto(tag);
        return result;
    }


    public void deleteTag(int id){
        Tag tag = findById(id);
        tagRepository.delete(tag);
    }


    public void updateTag(UpdateTagRequest request){
        Tag tag = findById(request.getId());

        tag.setTagName(request.getTagName());
        tag.setAbout(request.getAbout());

        tagRepository.save(tag);

    }


    public List<TagDto> findByTagsOrderByName(int pageNo , int size){
        Sort sort = Sort.by(Sort.Direction.ASC , "tagName");

        List<Tag> tags = tagRepository.findAll(PageRequest.of(pageNo-1, size , sort)).getContent();

        List<TagDto> result = tags.stream()
                .map(n->tagDtoConvertor.convertToTagDto(n))
                .collect(Collectors.toList());

        return result;
    }





    protected Tag findById(int id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(
                        () ->
                GenericException.builder()
                        .errorMessage("tag not found")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorCode(ErrorCode.TAG_NOT_FOUND)
                        .build());
        return tag;
    }




}
