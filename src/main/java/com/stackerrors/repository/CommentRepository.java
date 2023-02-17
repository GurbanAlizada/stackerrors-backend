package com.stackerrors.repository;

import com.stackerrors.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByUser_Id(int userId , Pageable pageable);

    List<Comment> findAllByQuestion_Id(int questionId , Pageable pageable);

}
