package com.example.Quiz.App.service;

import com.example.Quiz.App.DTO.QuestionDTO;
import com.example.Quiz.App.DTO.SubmissionDTO;
import com.example.Quiz.App.DTO.SummaryDTO;
import com.example.Quiz.App.DTO.UserSessionResponse;
import com.example.Quiz.App.model.Questions;
import com.example.Quiz.App.model.QuizSession;
import com.example.Quiz.App.model.SubmitAnswer;
import com.example.Quiz.App.repository.QuestionRepository;
import com.example.Quiz.App.repository.QuizRepository;
import com.example.Quiz.App.repository.SubmitAnswerRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuizService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired  // need when you attach every file
    QuizRepository quizRepository ;

    @Autowired
    SubmitAnswerRepository submitAnswerRepository;




    public String saveQues() {
        //Gson
        try(InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/questions.json"))){
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Questions>>() {}.getType();
            List<Questions> questionsList = gson.fromJson(reader, listType);

            questionRepository.saveAll(questionsList);
            return "saved successfully";

        } catch (Exception e) {
            return "not saved" +e.getMessage();
        }

        //Jackson
//        try{
//            ObjectMapper mapper = new ObjectMapper();
//            InputStream inputStream = getClass().getResourceAsStream("/questions.json");
//            List<Questions> questionsList = mapper.readValue(inputStream, new TypeReference<>(){});
//            quizRepository.saveAll(questionsList);
//            return "Data loaded successfully";
//        }catch (Exception e){
//            return "Error" +e.getMessage();
//        }
    }

    public UserSessionResponse start(String username) {
        QuizSession quiz = quizRepository.findByUsername(username);

        if(quiz != null){
            return new UserSessionResponse(String.valueOf(quiz.getUserId()),"user already exist");
        }

            String userId = generateUserId();
            QuizSession newuser = new QuizSession(userId,username);
            List<Questions> randomQuestions = questionRepository.findFiveRandomQuestions();
            newuser.setQuestions(randomQuestions);
            quizRepository.save(newuser);
            return new UserSessionResponse(String.valueOf(userId),"user Created");

    }
    private String generateUserId() {
        String userId;
        do {
            userId = String.valueOf(new Random().nextInt(9000) + 1000); // Generates 1000–9999
        } while (quizRepository.existsByUserId(userId));
        return userId;
    }


    public QuestionDTO getnextQuestion(String userId) {
        QuizSession quizSession = quizRepository.findById(userId).orElse(null);
        if(quizSession == null){
            throw new RuntimeException("session not found with this user id");
        }
        int index = quizSession.getCurrentIndex();
//        System.out.println(index);
        List<Questions> questionsList = quizSession.getQuestions();
        if (index >= questionsList.size()){
            System.out.println("No more question left for this user" + userId);
            return null;
        }

        Questions currQuestion = questionsList.get(index);
        quizSession.setCurrentIndex(index + 1);
        quizRepository.save(quizSession);
        return new QuestionDTO
                (currQuestion.getId(),currQuestion.getQuestionText(),currQuestion.getOptionA(),currQuestion.getOptionB(),currQuestion.getOptionC(),currQuestion.getOptionD());
    }


    public String submitAnswer(String userId, int id, String selectedAns) {

        // this is way of avoid null pointer exception in such cases .. Optional<T>
//        Optional<QuizSession> session = quizRepository.findById(userId);
//        Optional<Questions> questions = questionRepository.findById(id);
//        if(session.isEmpty() || questions.isEmpty()){
//            return "User not found";
//        }
//        QuizSession quizSession = session.get();
//        Questions questions1 = questions.get();

        QuizSession session = quizRepository.findById(userId).orElse(null);
        if (session == null){
            throw new RuntimeException("session not found with this userId" + userId);
        }

        Questions question = questionRepository.findById(id).orElse(null);
        if(question == null){
            throw new RuntimeException("Question not found with ID: " + id);
        }

        boolean isCorrect = selectedAns.equalsIgnoreCase(question.getCorrectAns());
        SubmitAnswer submitAnswer = new SubmitAnswer(session,question,selectedAns, question.getCorrectAns(), isCorrect);
        submitAnswerRepository.save(submitAnswer);
        return "Answer submitted successfully";
    }

    public SummaryDTO getSummaryById(String userId) {
        List<SubmitAnswer> submission = submitAnswerRepository.findUserById(userId);

        if (submission == null || submission.isEmpty()) {
            throw new RuntimeException("No submissions found for userId: " + userId);
        }
        if (submission.size() < 5) {
            throw new RuntimeException("You must complete all 5 questions before viewing the summary.");
        }

        List<SubmissionDTO> submissionList = new ArrayList<>();

        int correct  = 0;
        for (SubmitAnswer submit : submission){
            if (submit.isCorrect()){
                correct++;
            }
            SubmissionDTO submissionDTO = new SubmissionDTO(submit.getQuestions().getId(),submit.getQuestions().getQuestionText(),submit.getSelectedOption(),submit.getCorrectOption(),submit.isCorrect());
            submissionList.add(submissionDTO);
        }

        int total = submission.size();
        int incorrect = total - correct;

        return new SummaryDTO(total, correct, incorrect, submissionList);

    }
}
