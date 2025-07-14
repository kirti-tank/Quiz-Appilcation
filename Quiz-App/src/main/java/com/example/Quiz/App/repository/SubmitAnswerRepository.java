package com.example.Quiz.App.repository;

import com.example.Quiz.App.DTO.SubmissionDTO;
import com.example.Quiz.App.DTO.SummaryDTO;
import com.example.Quiz.App.model.SubmitAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SubmitAnswerRepository extends JpaRepository<SubmitAnswer, Integer> {

    @Query(value = "SELECT * FROM submit_answer s WHERE s.quiz_session_id = :userId", nativeQuery = true)
    List<SubmitAnswer> findUserById(@Param("userId") String userId);

}
