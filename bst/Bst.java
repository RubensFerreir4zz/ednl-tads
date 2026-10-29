package bst;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Bst implements BstInterface {
    private Node root;
    private int size;

    public Bst() {
        this.root = null;
        this.size = 0;
    }

    public int size() { return size; }

    public int height() { return height(root); }

    private int height(Node n) {
        if (n == null) { return -1; }
        return Math.max(height(n.getLeftChild()), height(n.getRightChild())) + 1;
    }

    public int depth(Node n) {
        if (n == null || n == root) { return 0; }
        return 1 + depth(n.getParent());
    }

    public boolean isEmpty() { return root == null; }

    public Iterator<Object> elements() {
        List<Object> list = new ArrayList<>();
        inOrderElements(root, list);
        return list.iterator();
    }

    private void inOrderElements(Node n, List<Object> list) {
        if (n != null) {
            inOrderElements(n.getLeftChild(), list);
            list.add(n.getObject());
            inOrderElements(n.getRightChild(), list);
        }
    }

    public Iterator<Node> nos() {
        List<Node> list = new ArrayList<>();
        inOrderNos(root, list);
        return list.iterator();
    }

    private void inOrderNos(Node n, List<Node> list) {
        if (n != null) {
            inOrderNos(n.getLeftChild(), list);
            list.add(n);
            inOrderNos(n.getRightChild(), list);
        }
    }

    public Node root() {
        if (isEmpty()) {
            throw new BstException("A árvore está vazia.");
        }
        return root;
    }

    public Node parent(Node n) {
        if (n == root) {
            throw new BstException("O nó raiz não possui pai.");
        }
        return n != null ? n.getParent() : null;
    }

    public Node leftChild(Node n) {
        return n != null ? n.getLeftChild() : null;
    }

    public Node rightChild(Node n) {
        return n != null ? n.getRightChild() : null;
    }

    public boolean hasLeft(Node n) {
        return n.getLeftChild() != null;
    }

    public boolean hasRight(Node n) {
        return n.getRightChild() != null;
    }

    public boolean isLeftChild(Node n) {
        if (n == null || n.getParent() == null) {
            return false;
        }
        return n.getParent().getLeftChild() == n;
    }

    public boolean isRightChild(Node n) {
        if (n == null || n.getParent() == null) {
            return false;
        }
        return n.getParent().getRightChild() == n;
    }

    public boolean isInternal(Node n) {
        if (n == null) {
            return false;
        }
        return n.getLeftChild() != null || n.getRightChild() != null;
    }

    public boolean isRoot(Node n) {
        return n != null && n == root;
    }
    public boolean isExternal(Node n) {
        if (n == null) {
            return false;
        }
        return n.getLeftChild() == null && n.getRightChild() == null;
    }

    public Object replace(Node n, Object e) {
        if (n == null) {
            return null;
        }
        Object old = n.getObject();
        n.setObject(e);
        return old;
    }

    protected Node createNode(int key, Object o) {
        return new Node(key, o);
    }

    public void insert(int key, Object o) {
        Node novo = createNode(key, o);
        if (root == null) {
            root = novo;
            size = 1;
            return;
        }
        Node current = root;
        while (true) {
            if (key < current.getKey()) {
                if (current.getLeftChild() == null) {
                    current.setLeftChild(novo);
                    novo.setParent(current);
                    size++;
                    break;
                }
                current = current.getLeftChild();
            } else {
                if (current.getRightChild() == null) {
                    current.setRightChild(novo);
                    novo.setParent(current);
                    size++;
                    break;
                }
                current = current.getRightChild();
            }
        }
    }

    public Object remove(Node n) {
        if (n == null) {
            return null;
        }
        Object oldObject = n.getObject();

        if (n.getLeftChild() != null && n.getRightChild() != null) {
            Node successor = minNode(n.getRightChild());
            int successorKey = successor.getKey();
            Object successorObj = successor.getObject();
            removeOneChild(successor);
            n.setKey(successorKey);
            n.setObject(successorObj);
        } else {
            removeOneChild(n);
        }

        size--;
        return oldObject;
    }

    private Node minNode(Node node) {
        Node current = node;
        while (current.getLeftChild() != null) {
            current = current.getLeftChild();
        }
        return current;
    }

    private void removeOneChild(Node node) {
        Node child = (node.getLeftChild() != null) ? node.getLeftChild() : node.getRightChild();

        if (child != null) {
            child.setParent(node.getParent());
        }

        if (node == root) {
            root = child;
        } else {
            Node parent = node.getParent();
            if (parent.getLeftChild() == node) {
                parent.setLeftChild(child);
            } else {
                parent.setRightChild(child);
            }
        }

        node.setParent(null);
        node.setLeftChild(null);
        node.setRightChild(null);
    }

    public void showTree() {
        int h = height();
        if (h < 0) {
            throw new BstException("Árvore vazia");
        }
        int columns = (int) Math.pow(2, h + 1) - 1;
        String[][] res = new String[h + 1][columns];
        for (int i = 0; i <= h; i++) {
            java.util.Arrays.fill(res[i], "");
        }
        fillGrid(res, root, 0, 0, columns - 1);
        for (int i = 0; i <= h; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < columns; j++) {
                if (res[i][j].equals("")) {
                    sb.append("   ");
                } else {
                    String keyStr = res[i][j];
                    if (keyStr.length() == 1) {
                        sb.append(" " + keyStr + " ");
                    } else if (keyStr.length() == 2) {
                        sb.append(" " + keyStr);
                    } else {
                        sb.append(keyStr);
                    }
                }
            }
            System.out.println(sb.toString());
        }
    }

    private void fillGrid(String[][] res, Node n, int row, int left, int right) {
        if (n == null) return;
        int mid = left + (right - left) / 2;
        res[row][mid] = String.valueOf(n.getKey());
        fillGrid(res, n.getLeftChild(), row + 1, left, mid - 1);
        fillGrid(res, n.getRightChild(), row + 1, mid + 1, right);
    }

    public Object find(int key) {
        Node current = root;
        while (current != null) {
            if (key == current.getKey()) {
                return current.getObject();
            } else if (key < current.getKey()) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        return null;
    }

    public Node findNode(int key) {
        Node current = root;
        while (current != null) {
            if (key == current.getKey()) {
                return current;
            } else if (key < current.getKey()) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }
        return null;
    }

    public void setRoot(Node node) {
        this.root = node;
    }
}