package animals;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class BinaryTree {

    private Node root;
    private SortedSet<String> animals;
    private Set<String> facts;

    public BinaryTree() {
        this.animals = new TreeSet<String>();
        this.facts = new HashSet<String>();
    }

    public Node getRoot() {
        return this.root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public SortedSet<String> getAnimals() {
        return this.animals;
    }

    public void setAnimals(SortedSet<String> animals) {
        this.animals = animals;
    }

    public Set<String> getFacts() {
        return this.facts;
    }

    public void setFacts(Set<String> facts) {
        this.facts = facts;
    }

    // Returns null if value is not in BinaryTree
    public Node findNode(String value) {
        return findNodeHelper(value, this.root);
    }

    private static Node findNodeHelper(String value, Node root) {
        if (root == null) {
            return null;
        } else if (Main.stripArticle(root.getValue()).equals(value)) {
            return root;
        } else {
            if (root.getLeft() != null) {
                Node possible = findNodeHelper(value, root.getLeft());
                if (possible != null) return possible;
            }
            if (root.getRight() != null) {
                Node possible = findNodeHelper(value, root.getRight());
                if (possible != null) return possible;
            }
        }
        return null;
    }

}
