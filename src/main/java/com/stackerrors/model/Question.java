package com.stackerrors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



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


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();


    @OneToMany(mappedBy = "question" , cascade = { CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REMOVE })
    //@JsonIgnore
    private List<Image> questionImages;



    @OneToMany(mappedBy = "question" , cascade = { CascadeType.PERSIST , CascadeType.MERGE , CascadeType.REMOVE } )
    // @JsonIgnore
    private List<Comment> comments;


    @ManyToMany(mappedBy = "likesQuestions" , cascade = CascadeType.ALL)
    private List<User> likedUsers;



    @ManyToMany(mappedBy = "dissLikedQuestions" , cascade = CascadeType.ALL)
    private List<User> dissLikedUsers;


    // all args constructor and no args contructor
    public Question(int id, String title, String description,
                    boolean draft, Integer views, Date creationDate,
                    Date updateDate, boolean isActive, boolean answered,
                    User user, List<Tag> tags, List<Image> questionImages,
                    List<Comment> comments, List<User> likedUsers,
                    List<User> dissLikedUsers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.draft = draft;
        this.views = views;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.isActive = isActive;
        this.answered = answered;
        this.user = user;
        this.tags = tags;
        this.questionImages = questionImages;
        this.comments = comments;
        this.likedUsers = likedUsers;
        this.dissLikedUsers = dissLikedUsers;
    }

    public Question() {
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

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
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

    public List<Image> getQuestionImages() {
        return questionImages;
    }

    public void setQuestionImages(List<Image> questionImages) {
        this.questionImages = questionImages;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<User> likedUsers) {
        this.likedUsers = likedUsers;
    }

    public List<User> getDissLikedUsers() {
        return dissLikedUsers;
    }

    public void setDissLikedUsers(List<User> dissLikedUsers) {
        this.dissLikedUsers = dissLikedUsers;
    }


}
