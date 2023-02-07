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
@Table(name = "comments")
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
    @JsonIgnore
    private Question question;


    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "creation_date")
    private Date creationDate;

    private Date updateDate;

    @OneToMany(mappedBy = "comment" , cascade = {CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REMOVE})
    //@JsonIgnore
    private List<Image> commentImages;



    @ManyToMany(mappedBy = "likes" , cascade = CascadeType.ALL)
    private List<User> likedUsers = new ArrayList<>();


}
