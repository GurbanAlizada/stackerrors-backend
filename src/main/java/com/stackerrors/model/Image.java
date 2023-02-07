package com.stackerrors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Image implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "image_url")
    private String imageUrl;


    @Column(name = "publishId")
    private String publishId;

    @ManyToOne
    @JoinColumn(name = "question_id" )
    @JsonIgnore
    private Question question;


    @ManyToOne
    @JoinColumn(name = "comment_id")
    @JsonIgnore
    private Comment comment;


    @ManyToOne
    @JoinColumn(name = "error_id")
    private Error error;


    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user ;


    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishId='" + publishId + '\'' +
                ", question=" + question +
                ", comment=" + comment +
                ", error=" + error +
             //   ", user=" + user +
                '}';
    }
}
