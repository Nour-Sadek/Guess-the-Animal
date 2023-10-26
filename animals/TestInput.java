package animals;

public class TestInput {

    protected static boolean getYesOrNo() {
        while (true) {
            String userInput = Main.scanner.nextLine().toLowerCase().strip();
            if (userInput.endsWith("!") || userInput.endsWith(".")) userInput = userInput.substring(0, userInput.length() - 1);
            if (Main.YES_ANSWERS.contains(userInput)) return true;
            else if (Main.NO_ANSWERS.contains(userInput)) return false;
            System.out.println("yes or no");
        }
    }

    protected static String getAnimalFact() {
        while (true) {
            String userInput = Main.scanner.nextLine().toLowerCase().strip();
            if (userInput.startsWith("it can ") || userInput.startsWith("it has ") || userInput.startsWith("it is ")) {
                return userInput;
            } else {
                System.out.println("The examples of a statement:");
                System.out.println(" - It can fly\n" + " - It has horn\n" + " - It is a mammal");
                System.out.println("Specify a fact that distinguishes a cat from a shark.");
                System.out.println("The sentence should be of the format: 'It can/has/is ...'.");
            }
        }

    }
}
