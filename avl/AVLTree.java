package avl;

import bst.*;

public class AVLTree extends Bst {
    public AVLTree() {
        super();
    }

    @Override
    protected Node createNode(int key, Object o) {
        return new AvlNode(key, o);
    }

    @Override
    public AvlNode root() {
        if (isEmpty()) {
            throw new BstException("A árvore está vazia.");
        }
        return (AvlNode) super.root();
    }

    @Override
    public AvlNode findNode(int key) {
        return (AvlNode) super.findNode(key);
    }

    @Override
    public void insert(int key, Object o) {
        super.insert(key, o);
        balance(key);
    }

    @Override
    public Object remove(Node node) {
        if (node == null) {
            return null;
        }
        return remove((AvlNode) node);
    }

    public Object remove(AvlNode node) {
        if (node == null) {
            return null;
        }

        AvlNode startParent;
        boolean wasLeft;

        // Identifica quem será fisicamente desconectado da árvore
        if (node.getLeftChild() != null && node.getRightChild() != null) {
            // Caso com 2 filhos: quem sai fisicamente é o sucessor
            AvlNode successor = node.getRightChild();
            while (successor.getLeftChild() != null) {
                successor = successor.getLeftChild();
            }
            startParent = successor.getParent();
            wasLeft = (startParent.getLeftChild() == successor);
        } else {
            // Caso com 0 ou 1 filho: o próprio 'node' sai fisicamente
            startParent = node.getParent();
            wasLeft = (startParent != null && startParent.getLeftChild() == node);
        }

        // Executa a remoção estrutural na BST
        Object removedObject = super.remove(node);

        // Se o nó removido possuía pai, propaga o balanceamento subindo a árvore
        if (startParent != null) {
            balanceRemove(startParent, wasLeft);
        }

        return removedObject;
    }

    private void balance(int key) {
        AvlNode node = findNode(key);
        AvlNode atual = node;

        while (atual != null && atual.getParent() != null)  {
            if (isRightChild(atual)){
                atual.getParent().setFb(atual.getParent().getFb() - 1);
            } else if (isLeftChild(atual)){
                atual.getParent().setFb(atual.getParent().getFb() + 1);
            }

            if (atual.getParent().getFb() == 0){
                break;
            }

            if (atual.getParent().getFb() < -1 || atual.getParent().getFb() > 1){
                rebalance(atual.getParent());
                break;
            }
            atual = atual.getParent();
        }
    }

    private void balanceRemove(AvlNode parent, boolean wasLeft) {
        AvlNode atual = parent;

        while (atual != null) {
            AvlNode nextParent = atual.getParent();
            boolean isCurrentLeft = (nextParent != null && nextParent.getLeftChild() == atual);

            if (wasLeft) {
                atual.setFb(atual.getFb() - 1);
            } else {
                atual.setFb(atual.getFb() + 1);
            }

            if (atual.getFb() == 1 || atual.getFb() == -1) {
                break;
            }

            if (atual.getFb() < -1 || atual.getFb() > 1) {
                AvlNode newRoot = rebalance(atual);
                if (newRoot != null && newRoot.getFb() != 0) {
                    break;
                }
                atual = nextParent;
            } else {
                atual = nextParent;
            }

            wasLeft = isCurrentLeft;
        }
    }

    private void rotationLeft(AvlNode node){
        AvlNode rightChild = node.getRightChild();
        AvlNode gcLeft = rightChild.getLeftChild();

        if (node == root()){
            setRoot(rightChild);
        } else if (isLeftChild(node)){
            ((AvlNode) node.getParent()).setLeftChild(rightChild);
        } else{
            ((AvlNode) node.getParent()).setRightChild(rightChild);
        }
        rightChild.setParent(node.getParent());
        node.setParent(rightChild);
        
        if (gcLeft != null){
            node.setRightChild(gcLeft);
            gcLeft.setParent(node);
        } else {
            node.setRightChild(null);
        }
        rightChild.setLeftChild(node);

        node.setFb(0);
        rightChild.setFb(0);
    }

    private void rotationRight(AvlNode node){
        AvlNode leftChild = node.getLeftChild();
        AvlNode gcRight = leftChild.getRightChild();

        if (node == root()){
            setRoot(leftChild);
        } else if (isLeftChild(node)){
            ((AvlNode) node.getParent()).setLeftChild(leftChild);
        } else{
            ((AvlNode) node.getParent()).setRightChild(leftChild);
        }

        leftChild.setParent(node.getParent());
        node.setParent(leftChild);
        
        if (gcRight != null){
            node.setLeftChild(gcRight);
            gcRight.setParent(node);
        } else {
            node.setLeftChild(null);
        }
        leftChild.setRightChild(node);

        node.setFb(0);
        leftChild.setFb(0);
    }

    private AvlNode rebalance(AvlNode node) {
        if (node.getFb() < -1) {
            AvlNode rightChild = node.getRightChild();
            int rightFb = rightChild != null ? rightChild.getFb() : 0;

            if (rightFb <= 0) {
                rotationLeft(node);
                if (rightFb == 0) {
                    node.setFb(-1);
                    rightChild.setFb(1);
                } else {
                    node.setFb(0);
                    rightChild.setFb(0);
                }
                return rightChild;
            } else {
                AvlNode grandchild = rightChild.getLeftChild();
                int gcOriginalFb = grandchild != null ? grandchild.getFb() : 0;

                rotationRight(rightChild);
                rotationLeft(node);

                if (gcOriginalFb == 1) {
                    node.setFb(0);
                    rightChild.setFb(-1);
                } else if (gcOriginalFb == -1) {
                    node.setFb(1);
                    rightChild.setFb(0);
                } else {
                    node.setFb(0);
                    rightChild.setFb(0);
                }
                if (grandchild != null) {
                    grandchild.setFb(0);
                }
                return grandchild;
            }
        } else if (node.getFb() > 1) {
            AvlNode leftChild = node.getLeftChild();
            int leftFb = leftChild != null ? leftChild.getFb() : 0;

            if (leftFb >= 0) {
                rotationRight(node);
                if (leftFb == 0) {
                    node.setFb(1);
                    leftChild.setFb(-1);
                } else {
                    node.setFb(0);
                    leftChild.setFb(0);
                }
                return leftChild;
            } else {
                AvlNode grandchild = leftChild.getRightChild();
                int gcOriginalFb = grandchild != null ? grandchild.getFb() : 0;

                rotationLeft(leftChild);
                rotationRight(node);

                if (gcOriginalFb == 1) {
                    node.setFb(-1);
                    leftChild.setFb(0);
                } else if (gcOriginalFb == -1) {
                    node.setFb(0);
                    leftChild.setFb(1);
                } else {
                    node.setFb(0);
                    leftChild.setFb(0);
                }
                if (grandchild != null) {
                    grandchild.setFb(0);
                }
                return grandchild;
            }
        }
        return node;
    }

    @Override
    public void showTree() {
        if (isEmpty()) {
            System.out.println("(Árvore vazia)");
            return;
        }
        int h = height();
        int columns = (int) Math.pow(2, h + 1) - 1;
        String[][] res = new String[h + 1][columns];
        for (int i = 0; i <= h; i++) {
            java.util.Arrays.fill(res[i], "");
        }
        fillGridAVL(res, root(), 0, 0, columns - 1);
        for (int i = 0; i <= h; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < columns; j++) {
                if (res[i][j].equals("")) {
                    sb.append("       ");
                } else {
                    String keyStr = res[i][j];
                    sb.append(String.format("%7s", keyStr));
                }
            }
            System.out.println(sb.toString());
        }
    }

    private void fillGridAVL(String[][] res, AvlNode n, int row, int left, int right) {
        if (n == null) return;
        int mid = left + (right - left) / 2;
        res[row][mid] = n.getKey() + "[" + n.getFb() + "]";
        fillGridAVL(res, n.getLeftChild(), row + 1, left, mid - 1);
        fillGridAVL(res, n.getRightChild(), row + 1, mid + 1, right);
    }
}