package com.breynli.service;

import com.stackerrors.dtos.request.SaveTagRequest;
import com.stackerrors.dtos.response.TagDto;
import com.stackerrors.exception.GenericException;
import com.stackerrors.mapper.TagDtoConvertor;
import com.stackerrors.model.Tag;
import com.stackerrors.repository.TagRepository;
import com.stackerrors.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class TagServiceTest {

    private TagRepository tagRepository;
    private TagDtoConvertor tagDtoConvertor;
    private TagService tagService;

    @BeforeEach
    public void setUp(){
        tagDtoConvertor = Mockito.mock(TagDtoConvertor.class);
        tagRepository = Mockito.mock(TagRepository.class);
        tagService = new TagService(tagRepository , tagDtoConvertor);
    }


    @Test
    public void testSaveTag_whenValidSaveTagRequest(){
        SaveTagRequest saveTagRequest = SaveTagRequest.builder()
                .about("about")
                .tagName("tagname")
                .build();

        Tag tag = Tag.builder()
                .about(saveTagRequest.getAbout())
                .tagName(saveTagRequest.getTagName())
                .build();

        when(tagRepository.save(tag)).thenReturn(tag);


        tagService.save(saveTagRequest);

        verify(tagRepository , times(1));

    }



    @Test
    public void testGetBtId_whenTagExists_itShouldReturnTagDto(){

        Tag tag = Tag.builder()
                .tagName("tagName")
                .about("about")
                .build();

        TagDto expected = TagDto.builder()
                .tagName(tag.getTagName())
                .about(tag.getAbout())
                .build();

        when(tagRepository.findById(1)).thenReturn(Optional.of(tag));
        when(tagDtoConvertor.convertToTagDto(tag)).thenReturn(expected);

        TagDto actual = tagService.getById(1);

        assertEquals(expected.getAbout() , actual.getAbout());
        verify(tagRepository , times(1)).findById(1);
        verify(tagDtoConvertor , times(1)).convertToTagDto(tag);
    }




    @Test
    public void testGetById_whenTagDoesNotExists_itShouldThrowsGenericException(){
        when(tagRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        assertThrows(GenericException.class , ()-> tagService.getById(Mockito.anyInt()));

    }









}