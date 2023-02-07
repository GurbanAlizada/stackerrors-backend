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

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;


    private String solution;


    private Date creationDate;

    private Date updatedDate;


    @OneToMany(mappedBy = "error" , cascade = { CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REMOVE })
    @JsonIgnore
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




    @ManyToMany(mappedBy = "likedErrors" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> likedUsers;


    @Override
    public String toString() {
        return "Error{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", solution='" + solution + '\'' +
                ", creationDate=" + creationDate +
                ", updatedDate=" + updatedDate +
                ", errorImages=" + errorImages +
           //     ", user=" + user +
                ", tags=" + tags +
            //    ", likedUsers=" + likedUsers +
                '}';
    }
}
