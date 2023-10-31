package animals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable {
    private static final long serialVersionUID = 7L;
    private String value;
    private Node left;  // represents the result of answering "no"
    private Node right;  // represents the result of answering "yes"
    private Node parent;

    public Node(String value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public String getValue() {
        return this.value;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public void setLeft(Node leftNode) {
        this.left = leftNode;
    }

    public void setRight(Node rightNode) {
        this.right = rightNode;
    }

    public void setParent(Node parentNode) { this.parent = parentNode; }

    public Node getParent() { return this.parent; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        return this.getValue().equals(((Node) obj).getValue());
    }

    public static Node createQuestionNode(String yesAnimal, String noAnimal, String animalFact) {

        // Create the question Node
        Node questionNode = new Node(animalFact);

        questionNode.setLeft(new Node(noAnimal));
        questionNode.setRight(new Node(yesAnimal));

        questionNode.getLeft().setParent(questionNode);
        questionNode.getRight().setParent(questionNode);

        return questionNode;
    }

    public boolean isAnimalNode() {
        return this.left == null && this.right == null;
    }

    public List<String> getFacts() {
        List<String> parents = new ArrayList<>();
        Node parentNode = this.getParent();
        Node currentNode = this;

        while (parentNode != null) {
            String animalFact = parentNode.getValue();
            if (parentNode.getLeft().equals(currentNode)) animalFact = Main.getNegationFromFact(animalFact);
            if (!parents.isEmpty()) parents.add(0, animalFact);
            else parents.add(animalFact);

            currentNode = parentNode;
            parentNode = parentNode.getParent();
        }

        return parents;
    }

}
