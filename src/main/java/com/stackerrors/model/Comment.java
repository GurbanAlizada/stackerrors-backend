package com.stackerrors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Builder
@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;


    @Column(name = "is_verified")
    private boolean isVerified;


    @Column(name = "creation_date")
    private Date creationDate;

    private Date updateDate;


    @OneToMany(mappedBy = "comment" , cascade = {CascadeType.PERSIST , CascadeType.MERGE,  CascadeType.REMOVE})
    private List<Image> commentImages;



    @ManyToMany
    @JoinTable(
            name = "comment_likes",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedCommentUsers;



}
