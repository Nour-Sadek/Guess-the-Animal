package animals;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Main {

    private final static String[] CLARIFICATION_STATEMENTS = new String[] {"I'm not sure I caught you: was it yes or no?",
            "Funny, I still don't understand, is it yes or no?", "Oh, it's too complicated for me: just tell me yes or no.",
            "Could you please simply say yes or no?", "Oh, no, don't try to confuse me: say yes or no.", "Come on, yes or no?"};

    private final static String[] GOODBYE_STATEMENTS = new String[] {"Bye!", "See you soon!", "Have a nice day!", "See you next time!", "See you later!", "See ya!",
            "Catch you later!", "Have a good day!", "Have a good one!", "Take care!", "Peace out!", "Peace!", "Adios!", "Au revoir!", "Farewell!", "Until next time..."};

    private final static List<String> YES_ANSWERS = Arrays.asList("y", "yes", "yeah", "yep", "sure", "right", "affirmative", "correct",
            "indeed", "you bet", "exactly", "you said it");

    private final static List<String> NO_ANSWERS = Arrays.asList("n", "no", "no way", "nah", "nope", "negative", "I don't think so",
            "yeah no");

    private final static List<Character> VOWELS = Arrays.asList('a', 'o', 'i', 'u', 'e');
    protected final static Scanner scanner = new Scanner(System.in);
    protected final static Random random = new Random();

    private static final String greeting;

    static {
        LocalTime now = LocalTime.now();
        LocalTime midnight = LocalTime.of(0, 0);
        LocalTime atFiveAM = LocalTime.of(5, 0);
        LocalTime atTwelvePM = LocalTime.of(12, 0);
        LocalTime atSixPM = LocalTime.of(18, 0);

        if ((now.isAfter(midnight) && now.isBefore(atFiveAM)) || now.equals(midnight) || now.equals(atFiveAM)) greeting = "Hi, Night Owl!";
        else if (now.isAfter(atFiveAM) && now.isBefore(atTwelvePM)) greeting = "Good morning!";
        else if ((now.isAfter(atTwelvePM) && now.isBefore(atSixPM)) || now.equals(atTwelvePM)) greeting = "Good afternoon!";
        else greeting = "Good evening!";
    }

    public static void main(String[] args) {
        System.out.println(greeting + "\n");

        System.out.println("Enter an animal:");
        String userInput = scanner.nextLine().toLowerCase().strip();
        System.out.println(formulateQuestion(userInput));

        String userAnswer = getUserAnswer();
        System.out.println("You answered: " + userAnswer + "\n");

        String goodbyeStatement = GOODBYE_STATEMENTS[random.nextInt(GOODBYE_STATEMENTS.length)];
        System.out.println(goodbyeStatement);
    }

    private static String formulateQuestion(String userInput) {
        String startingStatement = "Is it ";

        if (userInput.startsWith("a ") || userInput.startsWith("an ")) {
            startingStatement += userInput;
        } else {
            if (userInput.startsWith("the ")) userInput = userInput.substring(4);
            if (VOWELS.contains(userInput.charAt(0))) startingStatement += "an " + userInput;
            else startingStatement += "a " + userInput;
        }

        return startingStatement + "?";
    }

    private static String getUserAnswer() {
        while (true) {
            String userAnswer = scanner.nextLine().toLowerCase().strip();
            if (userAnswer.endsWith("!") || userAnswer.endsWith(".")) userAnswer = userAnswer.substring(0, userAnswer.length() - 1);

            if (YES_ANSWERS.contains(userAnswer)) {
                return "Yes";
            } else if (NO_ANSWERS.contains(userAnswer)) {
                return "No";
            } else {
                String clarificationStatement = CLARIFICATION_STATEMENTS[random.nextInt(CLARIFICATION_STATEMENTS.length)];
                System.out.println(clarificationStatement);
            }
        }
    }
}
