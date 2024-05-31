import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private final String username;
    private String password;
    private String fullName;
    private String email;

    public User(String username, String password, String fullName, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

public class OnlineExamSystem {
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser;

    public static boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public static void createUser(String username, String password, String fullName, String email) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password, fullName, email));
            System.out.println("User created successfully!");
        } else {
            System.out.println("Username already exists. Try a different username.");
        }
    }

    public static void updateProfile() {
        if (currentUser != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter new full name: ");
            currentUser.setFullName(scanner.nextLine());
            System.out.print("Enter new email: ");
            currentUser.setEmail(scanner.nextLine());
            System.out.println("Profile updated successfully!");
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public static void updatePassword() {
        if (currentUser != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter new password: ");
            currentUser.setPassword(scanner.nextLine());
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public static void selectAnswers() {
        Scanner scanner = new Scanner(System.in);
        String[] questions = {
                "Q1: What is the capital of France?",
                "Q2: What is 2 + 2?"
        };
        String[][] options = {
                {"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"},
                {"1. 3", "2. 4", "3. 5", "4. 6"}
        };
        int[] correctAnswers = {3, 2};

        int[] userAnswers = new int[questions.length];

        for (int i = 0; i < questions.length; i++) {
            try {
                System.out.println(questions[i]);
                for (String option : options[i]) {
                    System.out.println(option);
                }
                System.out.print("Select your answer: ");
                userAnswers[i] = scanner.nextInt();

                if (userAnswers[i] < 1 || userAnswers[i] > options[i].length) {
                    System.out.println("Invalid option selected. Please try again.");
                    i--; // Repeat the question
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
                i--; // Repeat the question
            }
        }

        // Evaluation logic can be added here
    }

    public static void startExamWithTimer(int seconds) {
        Thread timerThread = new Thread(() -> {
            try {
                Thread.sleep(seconds * 1000);
                System.out.println("Time's up! Submitting your answers...");
                selectAnswers();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timerThread.start();
    }

    public static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully!");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Online Examination System");

        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("1. Login");
                System.out.println("2. Create User");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();

                        if (login(username, password)) {
                            System.out.println("Login successful!");

                            boolean userExit = false;
                            while (!userExit) {
                                System.out.println("1. Update Password");
                                System.out.println("2. Update Profile");
                                System.out.println("3. Start Exam");
                                System.out.println("4. Logout");
                                System.out.print("Choose an option: ");
                                int userChoice = scanner.nextInt();
                                scanner.nextLine(); // Clear the newline

                                switch (userChoice) {
                                    case 1:
                                        updatePassword();
                                        break;
                                    case 2:
                                        updateProfile();
                                        break;
                                    case 3:
                                        System.out.print("Enter exam duration in seconds: ");
                                        int duration = scanner.nextInt();
                                        startExamWithTimer(duration);
                                        break;
                                    case 4:
                                        logout();
                                        userExit = true;
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Try again.");
                                }
                            }
                        } else {
                            System.out.println("Invalid username or password");
                        }
                        break;
                    case 2:
                        System.out.print("Enter username: ");
                        String newUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String newPassword = scanner.nextLine();
                        System.out.print("Enter full name: ");
                        String fullName = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        createUser(newUsername, newPassword, fullName, email);
                        break;
                    case 3:
                        exit = true;
                        System.out.println("Exiting the system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.next(); // Clear the invalid input
            }
        }
    }
}
