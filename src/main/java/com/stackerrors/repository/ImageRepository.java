package com.stackerrors.repository;

import com.stackerrors.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Integer> {


}
