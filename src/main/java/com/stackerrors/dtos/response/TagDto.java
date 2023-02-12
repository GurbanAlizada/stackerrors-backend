package com.stackerrors.dtos.response;


import lombok.Builder;


@Builder
public class TagDto {

    private int id;

    private String about;

    private String tagName;

    public TagDto(int id, String about, String tagName) {
        this.id = id;
        this.about = about;
        this.tagName = tagName;
    }

    public TagDto() {
    }


    // getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
