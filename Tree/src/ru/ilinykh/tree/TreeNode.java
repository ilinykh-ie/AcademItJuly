package ru.ilinykh.tree;

class TreeNode<T> {
    private TreeNode<T> left;
    private TreeNode<T> right;
    private final T value;

    public TreeNode(T value) {
        this.value = value;
    }

    public T getData() {
        return value;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }
}