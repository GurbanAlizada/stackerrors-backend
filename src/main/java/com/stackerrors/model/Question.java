package com.stackerrors.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Entity
@Table(name = "questions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title" , length = 500)
    private String title;


    @Column(name = "detail" , length = 3000 , columnDefinition = "TEXT")
    private String description;

    @Column(name = "draft")
    private boolean draft;

    @Column(name = "views")
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


    @OneToMany(mappedBy = "question" , cascade = {CascadeType.PERSIST ,CascadeType.MERGE, CascadeType.REMOVE })
    private List<Image> questionImages;


    @OneToMany(mappedBy = "question" ,cascade = {CascadeType.PERSIST ,CascadeType.MERGE, CascadeType.REMOVE })
    private List<Comment> comments;



    // ManyToMany-de save edilecek is mapped by olan terefde yox jointable
    // iliskini elan etdiyimiz yerde aparilir
    // yeni burada questionRepo uzerinden taglari save etdik
    @ManyToMany
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "question_likes",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedUsers;


    @ManyToMany
    @JoinTable(
            name = "question_dissLikes",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> dissLikedUsers;


}
