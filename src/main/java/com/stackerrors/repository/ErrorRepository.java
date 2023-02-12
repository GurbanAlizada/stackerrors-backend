package com.stackerrors.repository;

import com.stackerrors.model.Error;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ErrorRepository extends JpaRepository<Error , Integer> {

    @Query("SELECT e FROM Error e WHERE e.title LIKE %:search%")
    List<Error> getByTitleLike(@Param("search") String search );


    List<Error> getAllByUser_Id(int userId, Pageable pageable);
}
