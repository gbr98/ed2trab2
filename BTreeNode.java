package arvore;

import java.util.Vector;

class Node {

    private int n;
    private Vector<Integer> key;
    private Vector<Node> children;
    private boolean leaf;


    public Node(int n) {
        this.key = new Vector<Integer>(n - 1);
        for (int i = 0; i < n - 1; i++) {
            this.key.add(null);
        }
        this.children = new Vector<Node>(n);
        for (int i = 0; i < n; i++) {
            this.children.add(null);
        }
        this.leaf = true;
        this.n = 0;
    }

    public Vector<Integer> getKey() {
        return key;
    }

    public void setKey(Vector<Integer> key) {
        this.key = key;
    }

    public Vector<Node> getChildren() {
        return children;
    }

    public void setChildren(Vector<Node> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }




}
