package ru.ilinykh.tree;

import java.util.ArrayList;

public class TreeNode<T extends Number & Comparable<T>> {
    private TreeNode<T> left;
    private TreeNode<T> right;
    private final T value;

    public TreeNode(T value) {
        if (value == null) {
            throw new NullPointerException("Данные элемента бинарного дерева не должны быть null");
        }

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

    public ArrayList<TreeNode<T>> getChildren() {
        ArrayList<TreeNode<T>> children = new ArrayList<>();

        if (left != null) {
            children.add(left);
        }

        if (right != null) {
            children.add(right);
        }

        return children;
    }
}