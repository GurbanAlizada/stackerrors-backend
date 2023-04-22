package com.stackerrors.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "errors")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;


    private String solution;

    private Date creationDate;

    private Date updatedDate;


    @OneToMany(mappedBy = "error" , cascade = { CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REMOVE }  )
    private List<Image> errorImages;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany
    @JoinTable(
            name = "error_tag",
            joinColumns = @JoinColumn(name = "error_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();



    @ManyToMany
    @JoinTable(
            name = "errors_likes",
            joinColumns = @JoinColumn(name = "error_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likedErrorUsers;


}
