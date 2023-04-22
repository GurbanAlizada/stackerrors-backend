package com.stackerrors.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Builder
@Entity
@Table(name = "tags")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "about")
    private String about;


    @ManyToMany(mappedBy = "tags" )
    private List<Question> questions = new ArrayList<>();


    @ManyToMany(mappedBy = "tags" )
    private List<Error> errors = new ArrayList<>();

}
