package animals;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Main {

    // Used in Stage 1 of the project only
    private final static String[] CLARIFICATION_STATEMENTS = new String[] {"I'm not sure I caught you: was it yes or no?",
            "Funny, I still don't understand, is it yes or no?", "Oh, it's too complicated for me: just tell me yes or no.",
            "Could you please simply say yes or no?", "Oh, no, don't try to confuse me: say yes or no.", "Come on, yes or no?"};

    private final static String[] GOODBYE_STATEMENTS = new String[] {"Bye!", "See you soon!", "Have a nice day!", "See you next time!", "See you later!", "See ya!",
            "Catch you later!", "Have a good day!", "Have a good one!", "Take care!", "Peace out!", "Peace!", "Adios!", "Au revoir!", "Farewell!", "Until next time..."};

    // Used in Stage 1 of the project only
    protected final static List<String> YES_ANSWERS = Arrays.asList("y", "yes", "yeah", "yep", "sure", "right", "affirmative", "correct",
            "indeed", "you bet", "exactly", "you said it");

    // Used in Stage 1 of the project only
    protected final static List<String> NO_ANSWERS = Arrays.asList("n", "no", "no way", "nah", "nope", "negative", "i don't think so",
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

        System.out.println("I want to learn about animals.\n" +
                "Which animal do you like most?");
        String favAnimal = scanner.nextLine().toLowerCase().strip();
        String fullAnimalName = getArticle(favAnimal) + " " + stripArticle(favAnimal);
        BinaryTree animalTree = new BinaryTree(new Node(fullAnimalName));

        System.out.println("Wonderful! I've learned so much about animals!");
        printRules();
        scanner.nextLine();  // Reading the enter keystroke
        playGuessingGame(animalTree, favAnimal);

        // Say goodbye to user
        System.out.println();
        String goodbyeStatement = GOODBYE_STATEMENTS[random.nextInt(GOODBYE_STATEMENTS.length)];
        System.out.println(goodbyeStatement);

    }

    private static String stripArticle(String uneditedName) {
        if (uneditedName.startsWith("a ") || uneditedName.startsWith("an ") || uneditedName.startsWith("the ")) {
            int startingIndex = 0;
            for (; startingIndex < uneditedName.length(); startingIndex++) {
                if (uneditedName.charAt(startingIndex) == ' ') {
                    startingIndex++;
                    break;
                }
            }
            return uneditedName.substring(startingIndex);
        } else {
            return uneditedName;
        }
    }

    private static String getArticle(String uneditedAnimalName) {
        if (uneditedAnimalName.startsWith("a ")) return "a";
        else if (uneditedAnimalName.startsWith("an ")) return "an";
        else {
            if (uneditedAnimalName.startsWith("the ")) uneditedAnimalName = uneditedAnimalName.substring(4);
            if (VOWELS.contains(uneditedAnimalName.charAt(0))) return "an";
            else return "a";
        }
    }

    @Deprecated  // Used in Stage 1 of the project
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

    @Deprecated  // Used in Stage 1 of the project
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

    private static void summaryOfFactsLearned(String animalFact, String firstAnimal, String secondAnimal, boolean correctForSecond) {
        System.out.println("I learned the following facts about animals:");

        String negation;
        String affirmative;
        String fact;
        String beginningOfQuestion;
        if (animalFact.startsWith("it has ")) {
            negation = "doesn't have";
            affirmative = "has";
            fact = animalFact.replace("it has ", "").replace(".", "");
            beginningOfQuestion = "Does it have";
        } else if (animalFact.startsWith("it can ")) {
            negation = "can't";
            affirmative = "can";
            fact = animalFact.replace("it can ", "").replace(".", "");
            beginningOfQuestion = "Can it";
        } else {  // the statement starts with "it is"
            negation = "isn't";
            affirmative = "is";
            fact = animalFact.replace("it is ", "").replace(".", "");
            beginningOfQuestion = "Is it";
        }

        if (correctForSecond) {
            System.out.println(" - The " + firstAnimal + " " + negation + " " + fact + ".");
            System.out.println(" - The " + secondAnimal + " " + affirmative + " " + fact + ".");
        } else {
            System.out.println(" - The " + firstAnimal + " " + affirmative + " " + fact + ".");
            System.out.println(" - The " + secondAnimal + " " + negation + " " + fact + ".");
        }

        System.out.println("I can distinguish these animals by asking the question:");
        System.out.println(" - " + beginningOfQuestion + " " + fact + "?\n");
    }

    private static String getQuestionFromFact(String animalFact) {
        String negation;
        String affirmative;
        String fact;
        String beginningOfQuestion;
        if (animalFact.startsWith("it has ")) {
            negation = "doesn't have";
            affirmative = "has";
            fact = animalFact.replace("it has ", "").replace(".", "");
            beginningOfQuestion = "Does it have";
        } else if (animalFact.startsWith("it can ")) {
            negation = "can't";
            affirmative = "can";
            fact = animalFact.replace("it can ", "").replace(".", "");
            beginningOfQuestion = "Can it";
        } else {  // the statement starts with "it is"
            negation = "isn't";
            affirmative = "is";
            fact = animalFact.replace("it is ", "").replace(".", "");
            beginningOfQuestion = "Is it";
        }

        return beginningOfQuestion + " " + fact + "?";
    }

    private static void printRules() {
        System.out.println("""
                Let's play a game!
                You think of an animla, and I guess it.
                Press enter when you're ready.""");
    }

    private static void playGuessingGame(BinaryTree animalTree, String oldAnimal) {
        String fullOldAnimalName = getArticle(oldAnimal) + " " + stripArticle(oldAnimal);
        System.out.println("Is it " + fullOldAnimalName + "?");

        boolean correctForOldAnimal = TestInput.getYesOrNo();
        String newAnimal = null;

        if (correctForOldAnimal) System.out.println("Yay! I got it right!\n");
        else {
            System.out.println("I give up. What animal do you have in mind?");
            newAnimal = scanner.nextLine().toLowerCase().strip();
            String fullNewAnimalName = getArticle(newAnimal) + " " + stripArticle(newAnimal);

            printFactsInstructions(fullOldAnimalName, fullNewAnimalName);
            String animalFact = TestInput.getAnimalFact();
            System.out.println("Is the statement correct for " + fullNewAnimalName + "?");
            boolean correctForNewAnimal = TestInput.getYesOrNo();

            // Update BinaryTree to include the new question and animal fact
            String yesAnimal;
            String noAnimal;
            if (correctForNewAnimal) {
                yesAnimal = fullNewAnimalName;
                noAnimal = fullOldAnimalName;
            } else {
                yesAnimal = fullOldAnimalName;
                noAnimal = fullNewAnimalName;
            }
            String question = getQuestionFromFact(animalFact);

            Node questionNode = Node.createQuestionNode(yesAnimal, noAnimal, question);
            animalTree.replaceAnimalWithQuestionNode(questionNode, new Node(fullOldAnimalName));

            // Print the result to the console
            summaryOfFactsLearned(animalFact, stripArticle(oldAnimal), stripArticle(newAnimal), correctForNewAnimal);
            System.out.println("Nice! I've learned so much about animals!\n");
        }

        // Ask for another round
        System.out.println("Would you like to play again?");
        boolean goForAnotherRound = TestInput.getYesOrNo();

        if (goForAnotherRound) {
            printRules();
            scanner.nextLine();
            if (correctForOldAnimal) playGuessingGame(animalTree, oldAnimal);
            else playGuessingGame(animalTree, newAnimal);
        }
    }

    private static void printFactsInstructions(String oldAnimal, String newAnimal) {
        System.out.println("Specify a fact that distinguishes " + oldAnimal + " from " + newAnimal);
        System.out.println("""
                The sentence should satisfy one of the following templates:
                - It can ...
                - It has ...
                - It is a/an ...
                """);
    }

}
