package btree;

public class BTree {
    private BTreeNode root;
    private final int t;

    public BTree(int t) {
        if (t < 2) {
            throw new IllegalArgumentException("O grau mínimo t deve ser pelo menos 2.");
        }
        this.t = t;
        this.root = null;
    }

    public BTreeNode getRoot() {
        return root;
    }

    public int getT() {
        return t;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public BTreeNode search(int k) {
        return (root == null) ? null : root.search(k);
    }

    public boolean contains(int k) {
        return search(k) != null;
    }

    public void insert(int k) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = k;
            root.n = 1;
            return;
        }

        if (root.n == 2 * t - 1) {
            BTreeNode s = new BTreeNode(t, false);
            s.children[0] = root;
            s.splitChild(0, root);

            int i = 0;
            if (s.keys[0] < k) {
                i++;
            }
            s.children[i].insertNonFull(k);

            root = s;
        } else {
            root.insertNonFull(k);
        }
    }

    public void remove(int k) {
        if (root == null) {
            return;
        }

        root.remove(k);

        if (root.n == 0) {
            if (root.leaf) {
                root = null;
            } else {
                root = root.children[0];
            }
        }
    }

    public void printTree() {
        if (root == null) {
            System.out.println("Árvore vazia.");
            return;
        }
        printSubtree(root, "", true);
    }

    private String formatKey(int k) {
        if (k >= 65 && k <= 90) {
            return "'" + (char) k + "'";
        }
        return String.valueOf(k);
    }

    private void printSubtree(BTreeNode node, String indent, boolean last) {
        if (node == null) {
            return;
        }

        System.out.print(indent);
        if (last) {
            System.out.print("\\-- ");
            indent += "    ";
        } else {
            System.out.print("|-- ");
            indent += "|   ";
        }

        System.out.print("[");
        for (int i = 0; i < node.n; i++) {
            System.out.print(formatKey(node.keys[i]));
            if (i < node.n - 1) {
                System.out.print(" | ");
            }
        }
        System.out.println("]" + (node.leaf ? " (folha)" : ""));

        if (!node.leaf) {
            for (int i = 0; i <= node.n; i++) {
                printSubtree(node.children[i], indent, i == node.n);
            }
        }
    }

    public void printInOrder() {
        if (root == null) {
            System.out.println("Árvore vazia.");
            return;
        }
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(BTreeNode node) {
        if (node == null) {
            return;
        }

        int i;
        for (i = 0; i < node.n; i++) {
            if (!node.leaf) {
                printInOrder(node.children[i]);
            }
            System.out.print(formatKey(node.keys[i]) + " ");
        }

        if (!node.leaf) {
            printInOrder(node.children[i]);
        }
    }
}
