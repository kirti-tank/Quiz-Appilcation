package com.example.Quiz.App.DTO;

import java.util.List;

public class SummaryDTO {
    private int totalAnswered;
    private int correctAnswer;
    private int incorrectAnswer;
    private List<SubmissionDTO> submissions;

    public SummaryDTO(int totalAnswered, int correctAnswer, int incorrectAnswer, List<SubmissionDTO> submissions) {
        this.totalAnswered = totalAnswered;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswer = incorrectAnswer;
        this.submissions = submissions;
    }

    public int getTotalAnswered() {
        return totalAnswered;
    }

    public void setTotalAnswered(int totalAnswered) {
        this.totalAnswered = totalAnswered;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getIncorrectAnswer() {
        return incorrectAnswer;
    }

    public void setIncorrectAnswer(int incorrectAnswer) {
        this.incorrectAnswer = incorrectAnswer;
    }

    public List<SubmissionDTO> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<SubmissionDTO> submissions) {
        this.submissions = submissions;
    }
}
