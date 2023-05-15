# Java Quiz Program with Authentication
This is a Java quiz program that allows users to take an exam in four programming languages: Java, Python, C#, and JavaScript. To access the quiz, users must authenticate themselves using their username and password.

# Installation
To use this program, you need to have the following software installed on your system:

- Java Development Kit (JDK) 8 or later
- Apache Maven
- PostgreSQL
To install Apache Maven, follow the instructions provided on the official Maven website.

To install MySQL or MariaDB, follow the instructions provided on the appropriate website for your operating system.

# Usage
To run the quiz program, navigate to the project directory in the terminal and run the following command:

```shell
mvn clean package spring-boot:run
```
This will start the Spring Boot web server. You can then access the quiz program by opening a web browser and navigating to http://localhost:8080.

# Authentication
To access the quiz, users must first authenticate themselves by creating an account or logging in with an existing account. The authentication system uses a MySQL or MariaDB database to store user data, including usernames and hashed passwords.

# Exam
Once a user is authenticated, they can take an exam in any of the four programming languages offered: Java, Python, C#, and JavaScript. The exam consists of multiple-choice questions and is timed. Users must complete the exam within the allotted time, and their score will be displayed at the end.

# Conclusion
This Java quiz program provides a fun and interactive way for users to test their knowledge of programming languages. With its authentication system and multiple language options, it is a versatile tool for both educators and learners.