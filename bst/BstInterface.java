package bst;
import java.util.Iterator;

public interface BstInterface {
    int size();

    int height();

    int depth(Node n);

    boolean isEmpty();

    Iterator<Object> elements();

    Iterator<Node> nos();

    Node root();

    Node parent(Node n);

    Node leftChild(Node n);

    Node rightChild(Node n);

    boolean hasLeft(Node n);

    boolean hasRight(Node n);

    boolean isLeftChild(Node n);

    boolean isRightChild(Node n);

    boolean isInternal(Node n);

    boolean isRoot(Node n);

    boolean isExternal(Node n);

    Object replace(Node n, Object e);

    void insert(int key, Object o);

    Object remove(Node n);

    Object find(int key);

    Node findNode(int key);
}
