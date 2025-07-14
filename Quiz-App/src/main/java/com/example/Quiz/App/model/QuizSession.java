package com.example.Quiz.App.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "quizSession")
public class QuizSession {
    @Id
    @Column(unique = true)
    String userId;
    String username;
    private int currentIndex;

    @ManyToMany
    private List<Questions> questions;


    public QuizSession() {}
    public QuizSession(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.currentIndex = 0;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }
}
