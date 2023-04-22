package com.stackerrors.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class ImageDto {

    private int id;

    private String url;

    private String publishId;


}
