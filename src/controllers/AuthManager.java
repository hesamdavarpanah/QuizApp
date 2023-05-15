package controllers;

import models.User;
import repository.UserRepo;

import java.util.Optional;
import java.util.Scanner;

public class AuthManager {
    private static final Scanner input = new Scanner(System.in);

    private static int loginCounter = 0;
    private User user;
    private UserRepo userRepo;

    public AuthManager() {
        this.user = new User();
        this.user.setAuthenticated(false);
        this.userRepo = new UserRepo();
    }

    public User getUser() {
        return user;
    }

    public void login() {
        if (loginCounter <= 3) {
            System.out.println("Login");

            System.out.print("username: ");
            String username = input.next();

            System.out.print("password: ");
            String password = input.next();

            Optional<User> user = userRepo.login(username, password);
            if (user.isPresent()) {
                this.user = user.get();
                this.user.setAuthenticated(true);
            } else {
                System.out.println("username or password is not correct!");
                loginCounter++;
            }
        } else {
            System.out.println("login limit exceeds, please try later");
        }
    }

    public void redirectToLogin() {
        System.out.print("Login menu:\n\t1- Login\n\t2- Signup\n\n");

        int answer;
        while (true) {
            System.out.print("Your choice: ");
            answer = input.nextInt();

            if (answer == 1 || answer == 2) {
                break;
            } else {
                System.out.println("Invalid input");
            }
        }

        if (answer == 1) {
            login();
        } else {
            signUp();
        }
    }

    public void signUp() {
        System.out.println("SignUp");

        System.out.print("First Name: ");
        String firstName = input.next();

        System.out.print("Last Name: ");
        String lastName = input.next();

        System.out.print("Email: ");
        String email = input.next();

        String password;
        while (true) {
            System.out.print("password: ");
            password = input.next();

            System.out.print("confirm password: ");
            String confirmPassword = input.next();

            if (password.equals(confirmPassword)) {
                break;
            } else {
                System.out.println("password and confirm password do not match!");
            }
        }

        User newUser = new User(firstName, lastName, email, password);
        int userId = userRepo.create(newUser);

        if (userId > 0) {
            System.out.println("registration completed successfully");
            login();
        }
    }

    public boolean isAuthenticated() {
        if (!getUser().getAuthenticated()) {
            redirectToLogin();
        }
        return getUser().getAuthenticated();
    }
}
