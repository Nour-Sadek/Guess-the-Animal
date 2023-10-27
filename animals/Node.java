package animals;

public class Node {
    private String value;
    private Node left;  // represents the result of answering "no"
    private Node right;  // represents the result of answering "yes"

    public Node(String value) {
        this.value = value;
        this.left = null;
        this.right = null;
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

    public void setLeft(String value) {
        this.left = new Node(value);
    }

    public void setLeft(Node leftNode) {
        this.left = leftNode;
    }

    public void setRight(String value) {
        this.right = new Node(value);
    }

    public void setRight(Node rightNode) {
        this.right = rightNode;
    }

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

        return questionNode;
    }

    public boolean isAnimalNode() {
        return this.left == null && this.right == null;
    }

}
