package com.stackerrors.repository;

import com.stackerrors.model.Question;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question , Integer> {


    Optional<List<Question>> findByIsActiveTrueAndDraftFalse(Pageable pageable );

    Optional<List<Question>> findByIsActiveFalseAndDraftFalse( Pageable pageable);

    Optional<List<Question>> findByAnsweredTrueAndDraftFalse(Pageable pageable);

    List<Question> findAllByTags_TagNameAndDraftFalse(String name);

    List<Question> findAllByUser_IdAndDraftFalse(int userId , Pageable pageable);

    List<Question> findAllByUser_IdAndDraftTrue(int userId, Pageable pageable);

    List<Question> findAllByDraftFalse(Pageable pageable);

    @Query("SELECT q FROM Question q WHERE q.title LIKE %:search%")
    List<Question> getByTitleLike(@Param("search") String search );



}
