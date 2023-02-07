package com.stackerrors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tags")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "about")
    private String about;


    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();


    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Error> errors = new ArrayList<>();



    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
