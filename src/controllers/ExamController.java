package controllers;

import models.*;
import repository.*;

import java.util.*;

public class ExamController {
    private static final Scanner scanner = new Scanner(System.in);

    private AuthManager authManager;
    private ExamRepo examRepo;
    private QuestionRepo questionRepo;
    private CategoryRepo categoryRepo;
    private AnswerRepo answerRepo;
    private UserExamRepo userExamRepo;

    public ExamController(AuthManager authManager) {
        this.authManager = authManager;
        this.questionRepo = new QuestionRepo();
        this.categoryRepo = new CategoryRepo();
        this.examRepo = new ExamRepo();
        this.answerRepo = new AnswerRepo();
        this.userExamRepo = new UserExamRepo();
    }

    private void displayCategory() {
        System.out.println("Exam category:");
        for (Category category : categoryRepo.findAll()) {
            System.out.printf("\t%d) %s\n", category.getId(), category.getName());
        }
    }

    private void displayExam(int categoryId) {
        System.out.println("Exams:");
        for (Exam exam : examRepo.findByCategoryId(categoryId)) {
            System.out.printf("\t%d) %s (difficulty level: %d)\n", exam.getId(), exam.getName(), exam.getDifficulty());
        }
    }

    private List<Answer> displayQuestion(int examId) {
        List<Answer> userAnswerList = new ArrayList<>();

        System.out.println("Questions:");
        for (Question question : questionRepo.findByExamId(examId)) {
            System.out.printf("%d) %s\n", question.getId(), question.getQuestionText());

            int i = 0;
//            this line should be improved
            List<Answer> answerList = answerRepo.findByQuestionId(question.getId());
            for (Answer answer : answerList) {
                System.out.printf("\t%d) %s\n", ++i, answer.getAnswerText());
            }

            System.out.print("Please choose the correct answer: ");
            int answerId = scanner.nextInt();
            userAnswerList.add(answerList.get(answerId - 1));

        }
        return userAnswerList;
    }

    private int evaluate(List<Answer> answerList) {
        int score = 0;

        for (Answer answer : answerList) {
            if (answer.isTrue()) {
                score += answer.getQuestion().getPoint();
            }
        }
        return score;
    }

    public void showUserExamHistory(int userId) {
        if (authManager.isAuthenticated()) {
            List<UserExam> userExamList = userExamRepo.findByUserId(userId);
            if (userExamList.size() > 0) {
                System.out.println("\nYour exam history:");

                User user = userExamList.get(0).getUser();
                System.out.printf("User Info:\n\tFull Name: %s %s\n\tEmail: %s\n\n",
                        user.getFirstName(), user.getLastName(), user.getEmail());

                System.out.println("Exam List:");
                for (UserExam userExam : userExamList) {
                    System.out.println(userExam);
                }
            }
            else{
                System.out.println("You have not participated in any exam!");
            }
        }
    }

    public void startExam() {
        if (authManager.isAuthenticated()) {
            displayCategory();

            System.out.print("Choose your exam category: ");
            int categoryId = scanner.nextInt();

            displayExam(categoryId);

            System.out.print("Choose exam: ");
            int examId = scanner.nextInt();

            List<Answer> userAnswerList = displayQuestion(examId);
            int score = evaluate(userAnswerList);

            System.out.println(score);

            User user = authManager.getUser();

            Exam exam = new Exam();
            exam.setId(examId);

            Calendar calendar = Calendar.getInstance();
            UserExam userExam = new UserExam(user, exam, calendar.getTime(),
                    score, "this is first exam");
            userExamRepo.create(userExam);
        }
    }
}
