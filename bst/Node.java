public class Node {
    private int key;
    private Object object;
    private Node leftChild;
    private Node rightChild;
    private Node parent;

    public Node(int key, Object object) {
        this.key = key;
        this.object = object;
    }

    public int getKey() {
        return key;
    }

    public Object getObject() {
        return object;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public Node getParent() {
        return parent;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
