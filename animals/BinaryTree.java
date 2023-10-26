package animals;

public class BinaryTree {

    private Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    // Returns null if value is not in BinaryTree
    public Node findNode(String value) {
        return findNodeHelper(value, this.root);
    }

    private static Node findNodeHelper(String value, Node root) {
        if (root == null) {
            return null;
        } else if (root.getValue().equals(value)) {
            return root;
        } else {
            if (root.getLeft() != null) {
                Node possible = findNodeHelper(value, root.getLeft());
                if (possible != null) return possible;
            } else if (root.getRight() != null) {
                Node possible = findNodeHelper(value, root.getRight());
                if (possible != null) return possible;
            }
        }
        return null;
    }

    public void replaceAnimalWithQuestionNode(Node questionNode, Node animalNode) {
        Node node = findParentNode(animalNode);
        if (node.equals(new Node(""))) this.root = questionNode;
        else node.setLeft(questionNode);
    }

    // If the childNode is the root of the tree, then return arbitrary Node Node("")
    private Node findParentNode(Node childNode) {
        if (this.root.equals(childNode)) {
            return new Node("");
        }
        return findParentNodeHelper(childNode, this.root);
    }

    private static Node findParentNodeHelper(Node childNode, Node node) {
        if (node == null) {
            return null;
        } else if (node.getLeft().equals(childNode) || node.getRight().equals(childNode)) {
            return node;
        } else {
            if (node.getLeft() != null) {
                Node left = node.getLeft();
                Node possible = findParentNodeHelper(childNode, left);
                if (possible != null) return possible;
            } else if (node.getRight() != null) {
                Node right = node.getRight();
                Node possible = findParentNodeHelper(childNode, right);
                if (possible != null) return possible;
            }
        }
        return null;
    }
}
