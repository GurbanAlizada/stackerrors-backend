package com.stackerrors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Builder
@Entity
@Table(name = "tags")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "about")
    private String about;


    @ManyToMany(mappedBy = "tags" )
    private List<Question> questions = new ArrayList<>();



    @ManyToMany(mappedBy = "tags" )
    private List<Error> errors = new ArrayList<>();



    // all args and no args constructors
    public Tag(int id, String tagName, String about, List<Question> questions, List<Error> errors) {
        this.id = id;
        this.tagName = tagName;
        this.about = about;
        this.questions = questions;
        this.errors = errors;
    }


    public Tag() {
    }


    // getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                '}';
    }


}
