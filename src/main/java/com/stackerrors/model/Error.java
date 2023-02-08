package com.stackerrors.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "errors")
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
    //@JsonIgnore
    private List<Image> errorImages;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany(cascade =
            { CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REMOVE }
    )
    @JoinTable(
            name = "error_tag",
            joinColumns = @JoinColumn(name = "error_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();




    @ManyToMany(mappedBy = "likedErrors" , cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<User> likedUsers;


    // all and no args contructors
    public Error(int id, String title, String description, String solution,
                 Date creationDate, Date updatedDate, List<Image> errorImages,
                 User user, List<Tag> tags, List<User> likedUsers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.solution = solution;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.errorImages = errorImages;
        this.user = user;
        this.tags = tags;
        this.likedUsers = likedUsers;
    }

    public Error() {
    }


    // getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<Image> getErrorImages() {
        return errorImages;
    }

    public void setErrorImages(List<Image> errorImages) {
        this.errorImages = errorImages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

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
