package com.stackerrors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "questions")
public class Question implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title" , length = 500)
    private String title;

    @Column(name = "detail" , length = 3000 , columnDefinition = "TEXT")
    private String description;

    /*
    Sual ver hissesinde eger istifadeci indi ver buttonunu klikleyerse post sorgusunda draft=false
    yox eger sonra ver buttonunu klikleyerse draft = true olur
     */
    @Column(name = "draft")
    private boolean draft;


    @Column(name = "view")
    private Integer views;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_answer")
    private boolean answered;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();


    @OneToMany(mappedBy = "question" , cascade = { CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REMOVE })
    @JsonIgnore
    private List<Image> questionImages;



    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Comment> comments;


    @ManyToMany(mappedBy = "likesQuestions" , cascade = CascadeType.ALL)
    private List<User> likedUsers;



    @ManyToMany(mappedBy = "dissLikedQuestions" , cascade = CascadeType.ALL)
    private List<User> dissLikedUsers;


}
