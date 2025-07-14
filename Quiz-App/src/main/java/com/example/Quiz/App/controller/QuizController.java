package com.example.Quiz.App.controller;

import com.example.Quiz.App.DTO.QuestionDTO;
import com.example.Quiz.App.DTO.SummaryDTO;
import com.example.Quiz.App.service.QuizService;
import com.example.Quiz.App.DTO.UserSessionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("saveAll")
    public String saveQuestions(){
        return quizService.saveQues();
    }

    @PostMapping("start")
    public UserSessionResponse startSession(@RequestBody Map<String,Object> request){
        String username = request.get("username").toString();
        return quizService.start(username);
    }

    @GetMapping("getQuestion/{userId}")
    public ResponseEntity<?> getQuestions(@PathVariable String userId){
        //Because sometimes you're returning a QuestionDTO, and
        // sometimes a String message like "No more questions left" — using <?> handles both.
        QuestionDTO ques = quizService.getnextQuestion(userId);
        if (ques == null) {
            // Return a message instead of a null object
            return ResponseEntity.ok("No more questions left");
        }
        return ResponseEntity.ok(ques);
    }

    @PostMapping("submitAnswer/{userId}/{id}")
    public ResponseEntity<?> submitAns(@PathVariable String userId, @PathVariable int id,@RequestBody Map<String,Object> request){
        String selectedAns = request.get("selectedOption").toString();
        String result = quizService.submitAnswer(userId,id,selectedAns);

        if (result.contains("not found")){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("getSummary/{userId}")
    public ResponseEntity<?> getSummary(@PathVariable String userId){
        try {
            SummaryDTO res = quizService.getSummaryById(userId);
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
