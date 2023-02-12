package com.stackerrors.dtos.response;


import lombok.Builder;


@Builder
public class ImageDto {

    private int id;

    private String url;

    private String publishId;


    public ImageDto(int id, String url, String publishId) {
        this.id = id;
        this.url = url;
        this.publishId = publishId;
    }


    public ImageDto() {
    }


    // getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }


}
