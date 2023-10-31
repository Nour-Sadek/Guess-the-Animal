package animals;

import java.time.LocalTime;
import java.util.*;
import java.io.*;

public class Main {

    private final static String[] GOODBYE_STATEMENTS = new String[] {"Bye!", "See you soon!", "Have a nice day!", "See you next time!", "See you later!", "See ya!",
            "Catch you later!", "Have a good day!", "Have a good one!", "Take care!", "Peace out!", "Peace!", "Adios!", "Au revoir!", "Farewell!", "Until next time..."};
    protected final static List<String> YES_ANSWERS = Arrays.asList("y", "yes", "yeah", "yep", "sure", "right", "affirmative", "correct",
            "indeed", "you bet", "exactly", "you said it");
    protected final static List<String> NO_ANSWERS = Arrays.asList("n", "no", "no way", "nah", "nope", "negative", "i don't think so",
            "yeah no");
    private final static List<Character> VOWELS = Arrays.asList('a', 'o', 'i', 'u', 'e');
    protected final static Scanner scanner = new Scanner(System.in);
    protected final static Random random = new Random();
    private static final String greeting;
    private static final BinaryTree animalTree = new BinaryTree();
    private static final String fileName = "animals.db";

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

        // deserialize root node from animals.db
        if (new File(fileName).isFile()) {  // Load root from previous run of the project
            try {
                List<Object> serializedObjects = (List<Object>) SerializationUtils.deserialize(fileName);
                animalTree.setRoot((Node) serializedObjects.get(0));
                animalTree.setAnimals((TreeSet<String>) serializedObjects.get(1));
                animalTree.setFacts((HashSet<String>) serializedObjects.get(2));
            } catch (IOException | ClassNotFoundException e) {
                String message = e.getMessage();
                System.out.println(message);
            }
        } else {
            System.out.println("I want to learn about animals.\n" +
                    "Which animal do you like most?");
            String favAnimal = scanner.nextLine().toLowerCase().strip();
            String fullAnimalName = getArticle(favAnimal) + " " + stripArticle(favAnimal);
            animalTree.setRoot(new Node(fullAnimalName));
            animalTree.getAnimals().add(stripArticle(favAnimal));
            System.out.println();
        }

        System.out.println("Welcome to the animal expert system!");

        boolean keepGoing = true;
        while (keepGoing) {
            printMenu();
            String userInput = TestInput.getNumberOption();
            switch (userInput) {
                case "1":
                    playGuessingGame();
                    break;
                case "2":
                    printListOfAnimals();
                    break;
                case "3":
                    System.out.println("Enter the animal:");
                    String animal = scanner.nextLine().toLowerCase().strip();
                    listAnimalFacts(stripArticle(animal));
                    break;
                case "4":
                    printAnimalTreeStatistics();
                    break;
                case "5":
                    BinaryTreePrinter.print(animalTree.getRoot());
                    break;
                case "0":
                    keepGoing = false;
                    break;
            }
        }

        // Serialize root node to animals.db
        try {
            List<Object> dataToSerialize = Arrays.asList(animalTree.getRoot(), animalTree.getAnimals(), animalTree.getFacts());
            SerializationUtils.serialize(dataToSerialize, fileName);
        } catch (IOException e) {
            String message = e.getMessage();
            System.out.println(message);
        }

        // Say goodbye to user
        System.out.println();
        String goodbyeStatement = GOODBYE_STATEMENTS[random.nextInt(GOODBYE_STATEMENTS.length)];
        System.out.println(goodbyeStatement);

    }

    protected static String stripArticle(String uneditedName) {
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

    protected static String getNegationFromFact(String animalFact) {
        String negation;
        String fact;
        if (animalFact.startsWith("it has ")) {
            negation = "doesn't have";
            fact = animalFact.replace("has", negation).replace(".", "");
        } else if (animalFact.startsWith("it can ")) {
            negation = "can't";
            fact = animalFact.replace("can", negation).replace(".", "");
        } else {  // the statement starts with "it is"
            negation = "isn't";
            fact = animalFact.replace("is", negation).replace(".", "");
        }

        return fact;
    }

    private static void printGuessingGameRules() {
        System.out.println("""
                You think of an animal, and I guess it.
                Press enter when you're ready.""");
    }

    private static void printMenu() {
        System.out.println("""
                                
                What do you want to do:
                                
                1. Play the guessing game
                2. List of all animals
                3. Search for an animal
                4. Calculate statistics
                5. Print the Knowledge Tree
                0. Exit""");
    }

    private static void playGuessingGame() {
        printGuessingGameRules();
        scanner.nextLine();  // Reading the enter keystroke
        while (true) {
            buildAnimalTree(animalTree.getRoot(), null);

            // Ask for another round
            System.out.println("Would you like to play again?");
            boolean goForAnotherRound = TestInput.getYesOrNo();

            if (goForAnotherRound) {
                printGuessingGameRules();
                scanner.nextLine();
            } else {
                break;
            }

        }
    }

    private static void printListOfAnimals() {
        System.out.println("Here are the animals I know:");
        for (String animal: animalTree.getAnimals()) {
            System.out.println(" - " + animal);
        }
    }

    private static void listAnimalFacts(String animal) {
        Node node = animalTree.findNode(animal);
        if (node == null) {
            System.out.println("No facts about the " + animal + ".");
        } else {
            List<String> facts = node.getFacts();
            System.out.println("Facts about the " + animal + ":");
            for (String fact: facts) {
                System.out.println(" - " + fact + ".");
            }
        }
    }

    private static void printAnimalTreeStatistics() {
        System.out.println("The Knowledge Tree stats\n");

        System.out.print("- root node                    ");
        System.out.println(animalTree.getRoot().getValue());

        System.out.print("- total number of nodes        ");
        System.out.println(animalTree.getAnimals().size() + animalTree.getFacts().size());

        System.out.print("- total number of animals      ");
        System.out.println(animalTree.getAnimals().size());

        System.out.print("- total number of statements   ");
        System.out.println(animalTree.getFacts().size());

        // Find values for last three questions
        int heightOfTree = 0;
        int minDepth = animalTree.findNode(animalTree.getAnimals().first()).getFacts().size();
        int sumOfAllDepths = 0;

        for (String animal: animalTree.getAnimals()) {
            Node animalNode = animalTree.findNode(animal);
            int animalNodeDepth = animalNode.getFacts().size();

            sumOfAllDepths = sumOfAllDepths + animalNodeDepth;

            if (minDepth > animalNodeDepth) {
                minDepth = animalNodeDepth;
            }
            if (heightOfTree < animalNodeDepth) {
                heightOfTree = animalNodeDepth;
            }
        }

        double avgDepth = (double) sumOfAllDepths / (double) animalTree.getAnimals().size();

        System.out.print("- height of the tree           ");
        System.out.println(heightOfTree);

        System.out.print("- minimum animal's depth       ");
        System.out.println(minDepth);

        System.out.print("- average animal's depth       ");
        System.out.printf("%.2f%n", avgDepth);

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

    private static void buildAnimalTree(Node node, Node parentNode) {
        if (node.isAnimalNode()) {
            addNewAnimalAndFact(node, parentNode);
        } else {
            String animalFact = node.getValue();
            System.out.println(getQuestionFromFact(animalFact));
            boolean answerIsYes = TestInput.getYesOrNo();

            if (answerIsYes) {
                buildAnimalTree(node.getRight(), node);
            } else {
                buildAnimalTree(node.getLeft(), node);
            }
        }
    }

    private static void addNewAnimalAndFact(Node node, Node parentNode) {
        String fullOldAnimalName = node.getValue();
        System.out.println("Is it " + fullOldAnimalName + "?");

        boolean correctForOldAnimal = TestInput.getYesOrNo();

        if (correctForOldAnimal) System.out.println("Yay! I got it right!\n");
        else {
            System.out.println("I give up. What animal do you have in mind?");
            String newAnimal = scanner.nextLine().toLowerCase().strip();
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

            Node questionNode = Node.createQuestionNode(yesAnimal, noAnimal, animalFact);
            if (parentNode == null) animalTree.setRoot(questionNode);
            else {
                if (parentNode.getLeft().equals(node)) {
                    parentNode.setLeft(questionNode);
                }
                else {
                    parentNode.setRight(questionNode);
                }
                questionNode.setParent(parentNode);
            }
            // Add new animal to animals set of animalTree BinaryTree
            animalTree.getAnimals().add(stripArticle(newAnimal));
            // Add new fact to facts set of animalTree BinaryTree
            animalTree.getFacts().add(animalFact);

            // Print the result to the console
            summaryOfFactsLearned(animalFact, stripArticle(fullOldAnimalName), stripArticle(newAnimal), correctForNewAnimal);
            System.out.println("Nice! I've learned so much about animals!\n");
        }
    }

}
