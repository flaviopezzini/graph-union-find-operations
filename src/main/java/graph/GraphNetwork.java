package graph;

import java.util.HashMap;
import java.util.Map;

/**
 * This implements a network/graph of nodes using a tree.
 */
public class GraphNetwork {

    private Map<Integer, Node> numberToNodeMap;
    private int size;

    public GraphNetwork(int size) {
        if (size < 0) {
            throw new InvalidSize("The size parameter must be greater or equal to zero");
        }
        this.size = size;
        numberToNodeMap = new HashMap<>();
    }

    public void union(int a, int b) {
        Node rootA = getOrAdd(a);
        Node rootB = getOrAdd(b);
        if (rootA.sizeOfTreeUnderThisNode >= rootB.sizeOfTreeUnderThisNode) {
            pointToBiggerTree(rootB, rootA);
        } else {
            pointToBiggerTree(rootA, rootB);
        }
    }

    public boolean areConnected(int a, int b) {
        Node nodeA = getNodeFromNumber(a);
        Node nodeB = getNodeFromNumber(b);
        if (nodeA == null || nodeB == null) {
            return false;
        }
        return root(nodeA).number == root(nodeB).number;
    }

    private void pointToBiggerTree(Node child, Node root) {
        child.rootOfTree = root;
        root.sizeOfTreeUnderThisNode += child.sizeOfTreeUnderThisNode;
    }

    private Node getOrAdd(int number) {
        Node node = getNodeFromNumber(number);
        if (node == null) {
            node = addNode(number);
            numberToNodeMap.put(number, node);
            return node;
        }
        Node rootNode = root(node);
        return rootNode;
    }

    private Node addNode(int number) {
        if (numberToNodeMap.size() == size) {
            throw new StackOverflow();
        }
        Node n = new Node();
        n.number = number;
        n.sizeOfTreeUnderThisNode = 1;
        n.rootOfTree = n;
        return n;
    }

    private Node root(Node n) {
        Node root = new Node(n); // separate object to prevent changing the parameter
        while (root.number != root.rootOfTree.number) {
            n.rootOfTree = n.rootOfTree.rootOfTree; // points to grandfather node for path compression
            root = root.rootOfTree;
        }
        return root;
    }

    private Node getNodeFromNumber(int number) {
        return numberToNodeMap.get(number);
    }

    class Node {
        int number;
        int sizeOfTreeUnderThisNode;
        Node rootOfTree;

        Node() {

        }

        Node(Node clone) {
            number = clone.number;
            sizeOfTreeUnderThisNode = clone.sizeOfTreeUnderThisNode;
            rootOfTree = clone.rootOfTree;
        }
    }

    class StackOverflow extends RuntimeException {

    }

    class InvalidSize extends RuntimeException {
        public InvalidSize(String message) {
            super(message);
        }
    }

}