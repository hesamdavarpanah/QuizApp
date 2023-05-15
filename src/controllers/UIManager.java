package controllers;

import java.util.Scanner;

public class UIManager {
    private static final Scanner input = new Scanner(System.in);
    private AuthManager authManager;
    private ExamController examController;

    public UIManager(AuthManager authManager) {
        this.authManager = authManager;
        this.examController = new ExamController(authManager);
    }

    public void showMainMenu() {
        if (authManager.isAuthenticated()) {
            while (true) {
                System.out.println("Main Menu:\n\t1- Start Exam\n\t2- Show Exam History\n\t3- Exit\n");

                System.out.print("your option: ");
                int choice = input.nextInt();
                if (choice == 1) {
                    examController.startExam();
                } else if (choice == 2) {
                    examController.showUserExamHistory(authManager.getUser().getId());
                } else {
                    System.out.println("Thank you, good luck!");
                    break;
                }
            }
        }
    }
}
