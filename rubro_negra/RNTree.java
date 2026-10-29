package rubro_negra;

import bst.*;
import rubro_negra.RNNode.Color;

public class RNTree extends Bst {

    public RNTree() {
        super();
    }

    public boolean isRed(Node n) {
        if (n == null) return false;
        return ((RNNode) n).isRed();
    }

    public boolean isBlack(Node n) {
        if (n == null) return true;
        return ((RNNode) n).isBlack();
    }

    public boolean isRubro(Node n) {
        return isRed(n);
    }

    public boolean isNegro(Node n) {
        return isBlack(n);
    }

    private Color getColor(Node n) {
        if (n == null) return Color.NEGRO;
        return ((RNNode) n).getColor();
    }

    private void setColor(Node n, Color c) {
        if (n != null) {
            ((RNNode) n).setColor(c);
        }
    }

    private RNNode getParentNode(Node n) {
        return (n != null && n.getParent() != null) ? (RNNode) n.getParent() : null;
    }

    private RNNode getGrandparent(Node n) {
        RNNode pai = getParentNode(n);
        return pai != null ? getParentNode(pai) : null;
    }

    private void rotationLeft(Node node) {
        Node rightChild = node.getRightChild();
        if (rightChild == null) return;

        Node gcLeft = rightChild.getLeftChild();
        Node parent = node.getParent();

        if (node == root()) {
            setRoot(rightChild);
        } else if (isLeftChild(node)) {
            parent.setLeftChild(rightChild);
        } else {
            parent.setRightChild(rightChild);
        }
        rightChild.setParent(parent);

        node.setRightChild(gcLeft);
        if (gcLeft != null) {
            gcLeft.setParent(node);
        }

        rightChild.setLeftChild(node);
        node.setParent(rightChild);
    }

    private void rotationRight(Node node) {
        Node leftChild = node.getLeftChild();
        if (leftChild == null) return;

        Node gcRight = leftChild.getRightChild();
        Node parent = node.getParent();

        if (node == root()) {
            setRoot(leftChild);
        } else if (isLeftChild(node)) {
            parent.setLeftChild(leftChild);
        } else {
            parent.setRightChild(leftChild);
        }
        leftChild.setParent(parent);

        node.setLeftChild(gcRight);
        if (gcRight != null) {
            gcRight.setParent(node);
        }

        leftChild.setRightChild(node);
        node.setParent(leftChild);
    }

    @Override
    public void insert(int key, Object object) {
        RNNode novo = new RNNode(key, object);

        if (isEmpty()) {
            setRoot(novo);
            novo.setColor(Color.NEGRO);
            return;
        }

        Node current = root();
        while (true) {
            if (key < current.getKey()) {
                if (current.getLeftChild() == null) {
                    current.setLeftChild(novo);
                    novo.setParent(current);
                    break;
                }
                current = current.getLeftChild();
            } else {
                if (current.getRightChild() == null) {
                    current.setRightChild(novo);
                    novo.setParent(current);
                    break;
                }
                current = current.getRightChild();
            }
        }

        balanceInsert(novo);
    }

    private void balanceInsert(RNNode v) {
        while (v != root() && isRed(v.getParent())) {
            RNNode w = getParentNode(v);
            RNNode t = getGrandparent(v);

            if (w == t.getLeftChild()) {
                RNNode u = (RNNode) t.getRightChild();

                if (isRed(u)) {
                    w.setColor(Color.NEGRO);
                    u.setColor(Color.NEGRO);
                    t.setColor(Color.RUBRO);
                    v = t;
                } else {
                    if (v == w.getRightChild()) {
                        v = w;
                        rotationLeft(v);
                        w = getParentNode(v);
                        t = getGrandparent(v);
                    }
                    w.setColor(Color.NEGRO);
                    t.setColor(Color.RUBRO);
                    rotationRight(t);
                    break;
                }
            } else {
                RNNode u = (RNNode) t.getLeftChild();

                if (isRed(u)) {
                    w.setColor(Color.NEGRO);
                    u.setColor(Color.NEGRO);
                    t.setColor(Color.RUBRO);
                    v = t;
                } else {
                    if (v == w.getLeftChild()) {
                        v = w;
                        rotationRight(v);
                        w = getParentNode(v);
                        t = getGrandparent(v);
                    }
                    w.setColor(Color.NEGRO);
                    t.setColor(Color.RUBRO);
                    rotationLeft(t);
                    break;
                }
            }
        }

        if (root() != null) {
            ((RNNode) root()).setColor(Color.NEGRO);
        }
    }

    @Override
    public Object remove(Node n) {
        if (n == null || isEmpty()) return null;

        Object oldObject = n.getObject();

        Node v;
        if (n.getLeftChild() != null && n.getRightChild() != null) {
            v = minNode(n.getRightChild());
            n.setKey(v.getKey());
            n.setObject(v.getObject());
        } else {
            v = n;
        }

        Node x = (v.getLeftChild() != null) ? v.getLeftChild() : v.getRightChild();
        Node parentV = v.getParent();
        boolean wasLeft = (parentV != null && parentV.getLeftChild() == v);
        Color corOriginalV = getColor(v);

        if (x != null) {
            x.setParent(parentV);
        }

        if (v == root()) {
            setRoot(x);
        } else if (wasLeft) {
            parentV.setLeftChild(x);
        } else {
            parentV.setRightChild(x);
        }

        v.setParent(null);
        v.setLeftChild(null);
        v.setRightChild(null);

        if (corOriginalV == Color.NEGRO) {
            if (isRed(x)) {
                setColor(x, Color.NEGRO);
            } else {
                balanceRemove(x, parentV, wasLeft);
            }
        }

        return oldObject;
    }

    private void balanceRemove(Node x, Node parent, boolean wasLeft) {
        while (x != root() && isBlack(x) && parent != null) {
            if (wasLeft) {
                Node w = parent.getRightChild();

                if (isRed(w)) {
                    setColor(w, Color.NEGRO);
                    setColor(parent, Color.RUBRO);
                    rotationLeft(parent);
                    w = parent.getRightChild();
                }

                if (w == null || (isBlack(w.getLeftChild()) && isBlack(w.getRightChild()))) {
                    setColor(w, Color.RUBRO);

                    if (isRed(parent)) {
                        setColor(parent, Color.NEGRO);
                        break;
                    }

                    x = parent;
                    parent = x.getParent();
                    wasLeft = (parent != null && parent.getLeftChild() == x);
                } else {
                    if (isBlack(w.getRightChild())) {
                        setColor(w.getLeftChild(), Color.NEGRO);
                        setColor(w, Color.RUBRO);
                        rotationRight(w);
                        w = parent.getRightChild();
                    }

                    setColor(w, getColor(parent));
                    setColor(parent, Color.NEGRO);
                    setColor(w.getRightChild(), Color.NEGRO);
                    rotationLeft(parent);
                    x = root();
                    break;
                }
            } else {
                Node w = parent.getLeftChild();

                if (isRed(w)) {
                    setColor(w, Color.NEGRO);
                    setColor(parent, Color.RUBRO);
                    rotationRight(parent);
                    w = parent.getLeftChild();
                }

                if (w == null || (isBlack(w.getLeftChild()) && isBlack(w.getRightChild()))) {
                    setColor(w, Color.RUBRO);

                    if (isRed(parent)) {
                        setColor(parent, Color.NEGRO);
                        break;
                    }

                    x = parent;
                    parent = x.getParent();
                    wasLeft = (parent != null && parent.getLeftChild() == x);
                } else {
                    if (isBlack(w.getLeftChild())) {
                        setColor(w.getRightChild(), Color.NEGRO);
                        setColor(w, Color.RUBRO);
                        rotationLeft(w);
                        w = parent.getLeftChild();
                    }

                    setColor(w, getColor(parent));
                    setColor(parent, Color.NEGRO);
                    setColor(w.getLeftChild(), Color.NEGRO);
                    rotationRight(parent);
                    x = root();
                    break;
                }
            }
        }

        if (x != null) {
            setColor(x, Color.NEGRO);
        }
        if (root() != null) {
            setColor(root(), Color.NEGRO);
        }
    }

    private Node minNode(Node node) {
        Node current = node;
        while (current.getLeftChild() != null) {
            current = current.getLeftChild();
        }
        return current;
    }

    @Override
    public void showTree() {
        if (isEmpty()) {
            System.out.println("(Árvore vazia)");
            return;
        }

        System.out.println("\n--- ESTRUTURA PIRAMIDAL 2D (Chave[Cor]) ---");
        showGridTree();

        System.out.println("\n--- VISUALIZAÇÃO HIERÁRQUICA (RAMOS) ---");
        System.out.println("Legenda: [N] = NEGRO | [R] = RUBRO | (E) = Esquerdo | (D) = Direito");
        RNNode raiz = (RNNode) root();
        String tagRaiz = raiz.isRed() ? "[R]" : "[N]";
        System.out.println(raiz.getKey() + " " + tagRaiz + " (raiz)");
        printTreeRecursive(raiz.getLeftChild(), "", false, "(E)");
        printTreeRecursive(raiz.getRightChild(), "", true, "(D)");
        System.out.println("----------------------------------------");
    }

    private void printTreeRecursive(Node node, String prefix, boolean isTail, String side) {
        if (node == null) return;

        RNNode rnNode = (RNNode) node;
        String tag = rnNode.isRed() ? "[R]" : "[N]";
        String branch = isTail ? "\\-- " : "+-- ";

        System.out.println(prefix + branch + side + " " + rnNode.getKey() + " " + tag);

        boolean hasLeft = node.getLeftChild() != null;
        boolean hasRight = node.getRightChild() != null;

        if (hasLeft || hasRight) {
            String nextPrefix = prefix + (isTail ? "    " : "|   ");
            if (node.getLeftChild() != null) {
                printTreeRecursive(node.getLeftChild(), nextPrefix, !hasRight, "(E)");
            } else {
                System.out.println(nextPrefix + (hasRight ? "+-- (E) (null) [N]" : "\\-- (E) (null) [N]"));
            }

            if (node.getRightChild() != null) {
                printTreeRecursive(node.getRightChild(), nextPrefix, true, "(D)");
            } else {
                System.out.println(nextPrefix + "\\-- (D) (null) [N]");
            }
        }
    }

    public void showGridTree() {
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
        fillGridRN(res, root(), 0, 0, columns - 1);
        for (int i = 0; i <= h; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < columns; j++) {
                if (res[i][j].equals("")) {
                    sb.append("        ");
                } else {
                    sb.append(String.format("%8s", res[i][j]));
                }
            }
            System.out.println(sb.toString());
        }
    }

    private void fillGridRN(String[][] res, Node n, int row, int left, int right) {
        if (n == null) return;
        int mid = left + (right - left) / 2;
        RNNode rnn = (RNNode) n;
        res[row][mid] = n.getKey() + (rnn.isRed() ? "(R)" : "(N)");
        fillGridRN(res, n.getLeftChild(), row + 1, left, mid - 1);
        fillGridRN(res, n.getRightChild(), row + 1, mid + 1, right);
    }
}
