package btree;

public class BTreeNode {
    int[] keys;
    int t;
    BTreeNode[] children;
    int n;
    boolean leaf;

    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.n = 0;
    }

    public int findKey(int k) {
        int idx = 0;
        while (idx < n && keys[idx] < k) {
            idx++;
        }
        return idx;
    }

    public void insertNonFull(int k) {
        int i = n - 1;
        if (leaf) {
            while (i >= 0 && keys[i] > k) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = k;
            n = n + 1;
        } else {
            while (i >= 0 && keys[i] > k) {
                i--;
            }
            if (children[i + 1].n == 2 * t - 1) {
                splitChild(i + 1, children[i + 1]);
                if (keys[i + 1] < k) {
                    i++;
                }
            }
            children[i + 1].insertNonFull(k);
        }
    }

    public void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1;

        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }

        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t];
            }
        }

        y.n = t - 1;

        for (int j = n; j >= i + 1; j--) {
            children[j + 1] = children[j];
        }

        children[i + 1] = z;

        for (int j = n - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }

        keys[i] = y.keys[t - 1];
        n = n + 1;
    }

    public void remove(int k) {
        int idx = findKey(k);

        if (idx < n && keys[idx] == k) {
            if (leaf) {
                removeFromLeaf(idx);
            } else {
                removeFromNonLeaf(idx);
            }
        } else {
            if (leaf) {
                return;
            }

            boolean flag = (idx == n);

            if (children[idx].n < t) {
                fill(idx);
            }

            if (flag && idx > n) {
                children[idx - 1].remove(k);
            } else {
                children[idx].remove(k);
            }
        }
    }

    public void removeFromLeaf(int idx) {
        for (int i = idx + 1; i < n; ++i) {
            keys[i - 1] = keys[i];
        }
        n--;
    }

    public void removeFromNonLeaf(int idx) {
        int k = keys[idx];

        if (children[idx].n >= t) {
            int pred = getPred(idx);
            keys[idx] = pred;
            children[idx].remove(pred);
        } else if (children[idx + 1].n >= t) {
            int succ = getSucc(idx);
            keys[idx] = succ;
            children[idx + 1].remove(succ);
        } else {
            merge(idx);
            children[idx].remove(k);
        }
    }

    public int getPred(int idx) {
        BTreeNode cur = children[idx];
        while (!cur.leaf) {
            cur = cur.children[cur.n];
        }
        return cur.keys[cur.n - 1];
    }

    public int getSucc(int idx) {
        BTreeNode cur = children[idx + 1];
        while (!cur.leaf) {
            cur = cur.children[0];
        }
        return cur.keys[0];
    }

    public void fill(int idx) {
        if (idx != 0 && children[idx - 1].n >= t) {
            borrowFromPrev(idx);
        } else if (idx != n && children[idx + 1].n >= t) {
            borrowFromNext(idx);
        } else {
            if (idx != n) {
                merge(idx);
            } else {
                merge(idx - 1);
            }
        }
    }

    public void borrowFromPrev(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx - 1];

        for (int i = child.n - 1; i >= 0; --i) {
            child.keys[i + 1] = child.keys[i];
        }

        if (!child.leaf) {
            for (int i = child.n; i >= 0; --i) {
                child.children[i + 1] = child.children[i];
            }
        }

        child.keys[0] = keys[idx - 1];

        if (!child.leaf) {
            child.children[0] = sibling.children[sibling.n];
        }

        keys[idx - 1] = sibling.keys[sibling.n - 1];

        child.n += 1;
        sibling.n -= 1;
    }

    public void borrowFromNext(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        child.keys[child.n] = keys[idx];

        if (!child.leaf) {
            child.children[child.n + 1] = sibling.children[0];
        }

        keys[idx] = sibling.keys[0];

        for (int i = 1; i < sibling.n; ++i) {
            sibling.keys[i - 1] = sibling.keys[i];
        }

        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; ++i) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        child.n += 1;
        sibling.n -= 1;
    }

    public void merge(int idx) {
        BTreeNode child = children[idx];
        BTreeNode sibling = children[idx + 1];

        child.keys[t - 1] = keys[idx];

        for (int i = 0; i < sibling.n; ++i) {
            child.keys[i + t] = sibling.keys[i];
        }

        if (!child.leaf) {
            for (int i = 0; i <= sibling.n; ++i) {
                child.children[i + t] = sibling.children[i];
            }
        }

        for (int i = idx + 1; i < n; ++i) {
            keys[i - 1] = keys[i];
        }

        for (int i = idx + 2; i <= n; ++i) {
            children[i - 1] = children[i];
        }

        child.n += sibling.n + 1;
        n--;
    }

    public BTreeNode search(int k) {
        int i = 0;
        while (i < n && k > keys[i]) {
            i++;
        }
        if (i < n && keys[i] == k) {
            return this;
        }
        if (leaf) {
            return null;
        }
        return children[i].search(k);
    }

    public int getT() {
        return t;
    }

    public int getN() {
        return n;
    }

    public int[] getKeys() {
        return keys;
    }

    public BTreeNode[] getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return leaf;
    }
}
