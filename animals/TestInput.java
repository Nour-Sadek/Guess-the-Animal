package animals;

import java.text.MessageFormat;
import java.util.List;

public class TestInput {

    private static final List<String> NUMBER_OPTIONS = List.of(new String[]{"0", "1", "2", "3", "4", "5"});

    protected static boolean getYesOrNo() {
        while (true) {
            String userInput = Main.scanner.nextLine().toLowerCase().strip();
            if (userInput.matches(Main.patternsBundle.getString("positiveAnswer.isCorrect"))) return true;
            else if (userInput.matches(Main.patternsBundle.getString("negativeAnswer.isCorrect"))) return false;

            // Ask user to write either yes or no
            System.out.println(Main.getOneFromMany("ask.again"));
        }
    }

    protected static String getAnimalFact(String oldAnimal, String newAnimal) {
        while (true) {
            String userInput = Main.scanner.nextLine().toLowerCase().strip();
            if (userInput.matches(Main.patternsBundle.getString("statement.isCorrect"))) {
                return userInput;
            } else {
                System.out.println(Main.messageBundle.getString("statement.error"));
                System.out.println(MessageFormat.format(Main.messageBundle.getString("statement.prompt"),
                        oldAnimal, newAnimal));
            }
        }

    }

    protected static String getNumberOption() {
        while (true) {
            String userInput = Main.scanner.nextLine().strip();
            if (NUMBER_OPTIONS.contains(userInput)) return userInput;
            else {
                System.out.println(MessageFormat.format(Main.messageBundle.getString("menu.property.error"),
                        NUMBER_OPTIONS.size() - 1));
            }
        }
    }
}
