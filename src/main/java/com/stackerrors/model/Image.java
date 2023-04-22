package com.stackerrors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;



@Builder
@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    private Question question;


    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;


    @ManyToOne
    @JoinColumn(name = "error_id")
    private Error error;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user ;


}
