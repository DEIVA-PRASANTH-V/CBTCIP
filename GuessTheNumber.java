import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int totalScore = 0;
        char playAgain;

        do {
            System.out.println("Welcome to 'Guess the Number' game!");

            System.out.print("Enter the minimum number of the range: ");
            int minRange = scanner.nextInt();

            System.out.print("Enter the maximum number of the range: ");
            int maxRange = scanner.nextInt();

            System.out.print("Enter the number of attempts allowed: ");
            int maxAttempts = scanner.nextInt();

            int numberToGuess = random.nextInt(maxRange - minRange + 1) + minRange;
            int attempts = 0;
            boolean hasGuessedCorrectly = false;

            System.out.println("I have selected a number between " + minRange + " and " + maxRange + ".");
            System.out.println("Try to guess it within " + maxAttempts + " attempts.");

            while (attempts < maxAttempts && !hasGuessedCorrectly) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess > numberToGuess) {
                    System.out.println("Too high!");
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low!");
                } else {
                    System.out.println("Congratulations! You've guessed the number.");
                    hasGuessedCorrectly = true;
                    totalScore += (maxAttempts - attempts + 1); // Score based on remaining attempts
                }
            }

            if (!hasGuessedCorrectly) {
                System.out.println("Sorry, you've used all your attempts. The number was: " + numberToGuess);
            }

            System.out.println("Your score for this round: " + (hasGuessedCorrectly ? (maxAttempts - attempts + 1) : 0));
            System.out.println("Total score: " + totalScore);
            System.out.print("Do you want to play another round? (y/n): ");
            playAgain = scanner.next().charAt(0);
        } while (playAgain == 'y' || playAgain == 'Y');

        System.out.println("Thanks for playing! Your final score: " + totalScore);
        scanner.close();
    }
}