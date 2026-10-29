package avl;

import bst.Node;

public class AvlNode extends Node {
    private int fb;

    public AvlNode(int key, Object object) {
        super(key, object);
        this.fb = 0;
    }

    public int getFb() {
        return fb;
    }

    public void setFb(int fb) {
        this.fb = fb;
    }

    @Override
    public AvlNode getLeftChild() {
        return (AvlNode) super.getLeftChild();
    }

    @Override
    public AvlNode getRightChild() {
        return (AvlNode) super.getRightChild();
    }

    @Override
    public AvlNode getParent() {
        return (AvlNode) super.getParent();
    }
}
