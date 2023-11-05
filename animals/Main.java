package animals;

import java.text.MessageFormat;
import java.time.LocalTime;
import java.util.*;
import java.io.*;

public class Main {
    protected final static Scanner scanner = new Scanner(System.in);
    protected final static Random random = new Random();
    private static final String greeting;
    private static final BinaryTree animalTree = new BinaryTree();
    private static final String fileName = "animals.db";
    protected static final ResourceBundle messageBundle;
    protected static final ResourceBundle patternsBundle;

    static {
        // Setting up the resource bundles
        String language = System.getProperty("user.language");
        if (language != null && language.equals("eo")) {
            messageBundle = ResourceBundle.getBundle("main.resources.messages_eo");
            patternsBundle = ResourceBundle.getBundle("main.resources.patterns_eo");
        } else {
            messageBundle = ResourceBundle.getBundle("main.resources.messages");
            patternsBundle = ResourceBundle.getBundle("main.resources.patterns");
        }
        // Setting up the greeting
        LocalTime now = LocalTime.now();
        LocalTime midnight = LocalTime.of(0, 0);
        LocalTime atFiveAM = LocalTime.of(5, 0);
        LocalTime atTwelvePM = LocalTime.of(12, 0);
        LocalTime atSixPM = LocalTime.of(18, 0);

        if ((now.isAfter(midnight) && now.isBefore(atFiveAM)) || now.equals(midnight) || now.equals(atFiveAM)) greeting = messageBundle.getString("greeting.night");
        else if (now.isAfter(atFiveAM) && now.isBefore(atTwelvePM)) greeting = messageBundle.getString("greeting.morning");
        else if ((now.isAfter(atTwelvePM) && now.isBefore(atSixPM)) || now.equals(atTwelvePM)) greeting = messageBundle.getString("greeting.afternoon");
        else greeting = messageBundle.getString("greeting.evening");
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
            System.out.println(messageBundle.getString("animal.wantLearn") + "\n" +
                    messageBundle.getString("animal.askFavorite"));
            String favAnimal = scanner.nextLine().toLowerCase().strip();
            favAnimal = getFullName(stripThe(favAnimal));
            animalTree.setRoot(new Node(favAnimal));
            animalTree.getAnimals().add(getNameFromFull(favAnimal));
            System.out.println();
        }

        System.out.println(messageBundle.getString("welcome"));

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
                    System.out.println(messageBundle.getString("animal.prompt"));
                    String animal = scanner.nextLine().toLowerCase().strip();
                    // Strip the article if it was given by the user
                    if (animal.matches(patternsBundle.getString("animal.1.pattern")))
                        animal = animal.replaceAll(patternsBundle.getString("animal.1.pattern"), patternsBundle.getString("animal.1.replace"));
                    listAnimalFacts(animal);
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
        System.out.println(getOneFromMany("farewell"));

    }

    private static void summaryOfFactsLearned(String animalFact, String firstAnimal, String secondAnimal, boolean correctForSecond) {
        System.out.println(messageBundle.getString("game.learned"));

        String negation = "";
        String fact = animalFact.replaceAll(patternsBundle.getString("animalFact.1.pattern"),
                patternsBundle.getString("animalFact.1.replace"));
        String question = "";
        if (animalFact.matches(patternsBundle.getString("question.1.pattern"))) {
            negation = animalFact.replaceAll(patternsBundle.getString("negative.2.pattern"),
                    patternsBundle.getString("negative.2.replace"));
            question = animalFact.replaceAll(patternsBundle.getString("question.1.pattern"),
                    patternsBundle.getString("question.1.replace"));
        } else if (animalFact.matches(patternsBundle.getString("question.2.pattern"))) {
            if (animalFact.matches(patternsBundle.getString("negative.1.pattern")))
                negation = animalFact.replaceAll(patternsBundle.getString("negative.1.pattern"),
                        patternsBundle.getString("negative.1.replace"));
            else negation = animalFact.replaceAll(patternsBundle.getString("negative.3.pattern"),
                    patternsBundle.getString("negative.3.replace"));
            question = animalFact.replaceAll(patternsBundle.getString("question.2.pattern"),
                    patternsBundle.getString("question.2.replace"));
        }

        String affirmativePattern = animalFact.replaceAll(patternsBundle.getString("animalFact.1.pattern"),
                patternsBundle.getString("animalFact.1.replace"));
        String negationPattern = negation.replaceAll(patternsBundle.getString("animalFact.1.pattern"),
                patternsBundle.getString("animalFact.1.replace"));
        if (correctForSecond) {
            System.out.println(String.format(negationPattern, messageBundle.getString("definitive") + " " + firstAnimal));
            System.out.println(String.format(affirmativePattern, messageBundle.getString("definitive") + " " + secondAnimal));
        } else {
            System.out.println(String.format(affirmativePattern, messageBundle.getString("definitive") + " " + firstAnimal));
            System.out.println(String.format(negationPattern, messageBundle.getString("definitive") + " " + secondAnimal));
        }

        System.out.println(messageBundle.getString("game.distinguish"));
        System.out.println(question + "\n");
    }

    private static String getQuestionFromFact(String animalFact) {
        String question = "";
        if (animalFact.matches(patternsBundle.getString("question.1.pattern"))) {
            question = animalFact.replaceAll(patternsBundle.getString("question.1.pattern"),
                    patternsBundle.getString("question.1.replace"));
        } else if (animalFact.matches(patternsBundle.getString("question.2.pattern"))) {
            question = animalFact.replaceAll(patternsBundle.getString("question.2.pattern"),
                    patternsBundle.getString("question.2.replace"));
        }

        return question;
    }

    protected static String getNegationFromFact(String animalFact) {
        String negation = "";
        if (animalFact.matches(patternsBundle.getString("question.1.pattern"))) {
            negation = animalFact.replaceAll(patternsBundle.getString("negative.2.pattern"),
                    patternsBundle.getString("negative.2.replace"));
        } else if (animalFact.matches(patternsBundle.getString("question.2.pattern"))) {
            if (animalFact.matches(patternsBundle.getString("negative.1.pattern")))
                negation = animalFact.replaceAll(patternsBundle.getString("negative.1.pattern"),
                        patternsBundle.getString("negative.1.replace"));
            else negation = animalFact.replaceAll(patternsBundle.getString("negative.3.pattern"),
                    patternsBundle.getString("negative.3.replace"));
        }

        return negation;
    }

    private static void printGuessingGameRules() {
        System.out.println(messageBundle.getString("game.think"));
        System.out.println(messageBundle.getString("game.enter"));
    }

    private static void printMenu() {
        System.out.println();
        System.out.println(messageBundle.getString("menu.property.title") + "\n");
        System.out.println("1. " + messageBundle.getString("menu.entry.play") + "\n" +
                "2. " + messageBundle.getString("menu.entry.list") + "\n" +
                "3. " + messageBundle.getString("menu.entry.search") + "\n" +
                "4. " + messageBundle.getString("menu.entry.statistics") + "\n" +
                "5. " + messageBundle.getString("menu.entry.print") + "\n" +
                "0. " + messageBundle.getString("menu.property.exit"));
    }

    private static void playGuessingGame() {
        printGuessingGameRules();
        scanner.nextLine();  // Reading the enter keystroke
        while (true) {
            buildAnimalTree(animalTree.getRoot(), null);

            // Ask for another round
            System.out.println(getOneFromMany("game.again"));
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
        System.out.println(messageBundle.getString("tree.list.animals"));
        for (String animal: animalTree.getAnimals()) {
            System.out.println(" - " + animal);
        }
    }

    private static void listAnimalFacts(String animal) {
        Node node = animalTree.findNode(animal);
        if (node == null) {
            System.out.println(MessageFormat.format(messageBundle.getString("tree.search.noFacts"), animal));
        } else {
            List<String> facts = node.getFacts();
            System.out.println(MessageFormat.format(messageBundle.getString("tree.search.facts"), animal));
            for (String fact: facts) {
                System.out.println(" - " + fact);
            }
        }
    }

    private static void printAnimalTreeStatistics() {
        System.out.println(messageBundle.getString("tree.stats.title") + "\n");

        System.out.println(MessageFormat.format(messageBundle.getString("tree.stats.root"),
                animalTree.getRoot().getValue()));

        System.out.println(MessageFormat.format(messageBundle.getString("tree.stats.nodes"),
                animalTree.getAnimals().size() + animalTree.getFacts().size()));

        System.out.println(MessageFormat.format(messageBundle.getString("tree.stats.animals"),
                animalTree.getAnimals().size()));

        System.out.println(MessageFormat.format(messageBundle.getString("tree.stats.statements"),
                animalTree.getFacts().size()));

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

        System.out.println(MessageFormat.format(messageBundle.getString("tree.stats.height"),
                heightOfTree));

        System.out.println(MessageFormat.format(messageBundle.getString("tree.stats.minimum"),
                minDepth));

        System.out.println(MessageFormat.format(messageBundle.getString("tree.stats.average"),
                avgDepth));

    }

    private static void printFactsInstructions(String oldAnimal, String newAnimal) {
        System.out.println(MessageFormat.format(messageBundle.getString("statement.prompt"),
                oldAnimal, newAnimal));
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
        System.out.println(fullOldAnimalName.replaceAll(patternsBundle.getString("guessAnimal.1.pattern"),
                patternsBundle.getString("guessAnimal.1.replace")));

        boolean correctForOldAnimal = TestInput.getYesOrNo();

        if (correctForOldAnimal) System.out.println(getOneFromMany("game.win") + "\n");
        else {
            System.out.println(messageBundle.getString("game.giveUp"));
            String newAnimal = scanner.nextLine().toLowerCase().strip();
            String fullNewAnimalName = getFullName(stripThe(newAnimal));

            // Check if provided animal name already exists in the animalTree
            if (animalTree.getAnimals().contains(getNameFromFull(fullNewAnimalName))) {
                System.out.println("It seems like this animal already exists in the knowledge tree.\n");
                return;
            }

            printFactsInstructions(fullOldAnimalName, fullNewAnimalName);
            String animalFact = TestInput.getAnimalFact(fullOldAnimalName, fullNewAnimalName);
            // Check of the provided statement already exists in the animalTree
            if (animalTree.getFacts().contains(animalFact)) {
                System.out.println("It seems like this animal fact already exists in the knowledge tree.\n");
                return;
            }
            System.out.println(MessageFormat.format(messageBundle.getString("game.isCorrect"), fullNewAnimalName));
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
            animalTree.getAnimals().add(getNameFromFull(fullNewAnimalName));
            // Add new fact to facts set of animalTree BinaryTree
            animalTree.getFacts().add(animalFact);

            // Print the result to the console
            summaryOfFactsLearned(animalFact, getNameFromFull(fullOldAnimalName), getNameFromFull(fullNewAnimalName),
                    correctForNewAnimal);
            System.out.println(getOneFromMany("animal.nice") + messageBundle.getString("animal.learnedMuch") + "\n");
        }
    }

    protected static String getOneFromMany(String message) {
        String allStatements = messageBundle.getString(message);
        String[] statements = allStatements.split("\\f");
        return statements[random.nextInt(statements.length)];
    }

    protected static String getFullName(String animal) {
        String fullName;

        if (animal.matches(patternsBundle.getString("animal.1.pattern"))) {
            fullName = animal.replaceAll(patternsBundle.getString("animal.1.pattern"), patternsBundle.getString("animal.1.replace"));
        } else if (animal.matches(patternsBundle.getString("animal.2.pattern"))) {
            fullName = animal.replaceAll(patternsBundle.getString("animal.2.pattern"), patternsBundle.getString("animal.2.replace"));
        } else {
            fullName = animal.replaceAll(patternsBundle.getString("animal.3.pattern"), patternsBundle.getString("animal.3.replace"));
        }

        return fullName;
    }

    protected static String getNameFromFull(String animal) {
        return animal.replaceAll(patternsBundle.getString("animalName.1.pattern"), patternsBundle.getString("animalName.1.replace"));
    }

    protected static String stripThe(String animal) {
        if (animal.startsWith(messageBundle.getString("definitive"))) {
            int startingIndex = 0;
            for (; startingIndex < animal.length(); startingIndex++) {
                if (animal.charAt(startingIndex) == ' ') {
                    startingIndex++;
                    break;
                }
            }
            return animal.substring(startingIndex);
        }
        else return animal;
    }

}
