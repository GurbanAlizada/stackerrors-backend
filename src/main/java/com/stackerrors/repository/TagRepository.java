package com.stackerrors.repository;

import com.stackerrors.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TagRepository extends JpaRepository<Tag, Integer> {

}
