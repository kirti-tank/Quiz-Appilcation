package com.example.Quiz.App.repository;

import com.example.Quiz.App.model.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository <QuizSession , String>{

    QuizSession findByUsername(String username);
    boolean existsByUserId(String userId);

}
