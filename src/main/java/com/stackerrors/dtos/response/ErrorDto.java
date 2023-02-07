package com.stackerrors.dtos.response;

import com.stackerrors.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {


    private int id;

    private String title;

    private String description;


    private String solution;


    private Date creationDate;

    private Date updatedDate;


    private List<String> imageUrls;


    private UserDto userDto;



    private List<Tag> tags ;





}
