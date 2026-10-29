package rubro_negra;

import bst.Node;

public class RNNode extends Node {
    public enum Color { RUBRO, NEGRO }
    private Color color;

    public RNNode(int key, Object object) {
        super(key, object);
        this.color = Color.RUBRO;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isRed() {
        return this.color == Color.RUBRO;
    }

    public boolean isBlack() {
        return this.color == Color.NEGRO;
    }

    public boolean isRubro() {
        return isRed();
    }

    public boolean isNegro() {
        return isBlack();
    }
}
