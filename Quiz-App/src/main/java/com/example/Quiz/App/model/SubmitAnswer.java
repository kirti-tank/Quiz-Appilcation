package com.example.Quiz.App.model;

import jakarta.persistence.*;

@Entity
@Table(name = "submit_answer")
public class SubmitAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "quiz_session_id" , referencedColumnName = "userId", foreignKey = @ForeignKey(name = "fk_answer_submission_quiz_session"))
    private QuizSession quizSession;

    @ManyToOne
    @JoinColumn(name = "question_id" ,referencedColumnName = "id" , foreignKey = @ForeignKey(name = "fk_answer_submission_question"))
    private Questions questions;

    private String selectedOption;
    private String correctOption;
    private boolean isCorrect;

    public SubmitAnswer(){}
    public SubmitAnswer(QuizSession quizSession, Questions questions, String selectedOption,String correctOption, boolean isCorrect) {
        this.quizSession = quizSession;
        this.questions = questions;
        this.selectedOption = selectedOption;
        this.correctOption = correctOption;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    public Questions getQuestions() {
        return questions;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
