package animals;

public class Node {
    private String value;
    private Node left;
    private Node right;

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

    public static Node createQuestionNode(String yesAnimal, String noAnimal, String question) {

        // Create the question Node
        Node questionNode = new Node(question);
        questionNode.setLeft("No");
        questionNode.setRight("Yes");

        // Assign the animals to the left nodes of the correct "Yes" or "No" nodes (right nodes are null)
        Node noNode = questionNode.getLeft();
        Node yesNode = questionNode.getRight();
        noNode.setLeft(noAnimal);
        yesNode.setLeft(yesAnimal);

        return questionNode;
    }

}
