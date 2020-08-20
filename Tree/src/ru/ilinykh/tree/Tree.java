package ru.ilinykh.tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

public class Tree<T extends Number & Comparable<T>> {
    private TreeNode<T> root;
    private int count;

    public Tree(TreeNode<T> root) {
        this.root = root;
        count = 1;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<T> node) {
        root = node;
    }

    public int size() {
        return count;
    }

    public void breadthFirst(Consumer<T> consumer) {
        if (root == null) {
            return;
        }

        Deque<TreeNode<T>> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> currentElement = queue.getFirst();

            consumer.accept(currentElement.getData());

            if (currentElement.getLeft() != null) {
                queue.addLast(currentElement.getLeft());
            }

            if (currentElement.getRight() != null) {
                queue.addLast(currentElement.getRight());
            }

            queue.removeFirst();
        }
    }

    public void depthFirst(Consumer<T> consumer) {
        if (count == 0 || root == null) {
            return;
        }

        Deque<TreeNode<T>> stack = new LinkedList<>();

        stack.add(root);

        while (!stack.isEmpty()) {
            TreeNode<T> currentElement = stack.getLast();

            consumer.accept(currentElement.getData());

            stack.removeLast();

            if (currentElement.getRight() != null) {
                stack.addLast(currentElement.getRight());
            }

            if (currentElement.getLeft() != null) {
                stack.addLast(currentElement.getLeft());
            }
        }
    }


    public void depthFirstRecursion(Consumer<T> consumer, TreeNode<T> node) {
        if (count == 0 || root == null) {
            return;
        }

        consumer.accept(node.getData());

        for (TreeNode<T> child : node.getChildren()) {
            depthFirstRecursion(consumer, child);
        }
    }

    public void add(T data) {
        TreeNode<T> newNode = new TreeNode<>(data);

        if (root == null) {
            root = newNode;

            return;
        }

        TreeNode<T> currentNode = root;

        for (; ; ) {
            if (data.compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                } else {
                    currentNode.setLeft(newNode);
                    count++;

                    break;
                }
            } else {
                if (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                } else {
                    currentNode.setRight(newNode);
                    count++;

                    break;
                }
            }
        }
    }

    public boolean contains(T data) {
        if (data == null || root == null) {
            return false;
        }

        TreeNode<T> currentNode = root;

        for (; ; ) {
            if (currentNode.getData().equals(data)) {
                return true;
            }

            if (data.compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                } else {
                    break;
                }
            } else {
                if (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                } else {
                    break;
                }
            }
        }

        return false;
    }

    public boolean remove(T data) {
        if (data == null || root == null) {
            return false;
        }

        TreeNode<T> currentNode = root;

        if (currentNode.getData().equals(data)) {
            if (currentNode.getRight() == null && currentNode.getLeft() == null) {
                root = null;
            } else if (currentNode.getRight() == null) {
                setRoot(currentNode.getLeft());
            } else if (currentNode.getLeft() == null) {
                setRoot(currentNode.getRight());
            } else {
                TreeNode<T> temp = currentNode.getRight();

                while (temp.getLeft() != null) {
                    temp = temp.getLeft();
                }

                temp.setLeft(currentNode.getLeft());
                setRoot(currentNode.getRight());
            }

            count--;
            return true;
        }

        TreeNode<T> previousNode;

        for (; ; ) {
            if (data.compareTo(currentNode.getData()) < 0) {
                if (currentNode.getLeft() != null) {
                    previousNode = currentNode;
                    currentNode = currentNode.getLeft();
                } else {
                    break;
                }
            } else {
                if (currentNode.getRight() != null) {
                    previousNode = currentNode;
                    currentNode = currentNode.getRight();
                } else {
                    break;
                }
            }

            if (currentNode.getData().equals(data)) {
                if (currentNode.getRight() == null && currentNode.getLeft() == null) {
                    if (previousNode.getLeft().equals(currentNode)) {
                        previousNode.setLeft(null);
                    } else {
                        previousNode.setRight(null);
                    }
                } else if (currentNode.getRight() == null) {
                    if (previousNode.getLeft().equals(currentNode)) {
                        previousNode.setLeft(currentNode.getLeft());
                    } else {
                        previousNode.setRight(currentNode.getLeft());
                    }
                } else if (currentNode.getLeft() == null) {
                    if (previousNode.getLeft().equals(currentNode)) {
                        previousNode.setLeft(currentNode.getRight());
                    } else {
                        previousNode.setRight(currentNode.getRight());
                    }
                } else {
                    TreeNode<T> min = currentNode.getRight();
                    TreeNode<T> previousMin = currentNode;

                    while (min.getLeft() != null) {
                        previousMin = min;
                        min = min.getLeft();
                    }

                    if (min.getRight() != null) {
                        previousMin.setLeft(min.getRight());
                    } else {
                        previousMin.setLeft(null);
                    }

                    if (previousNode.getLeft().equals(currentNode)) {
                        previousNode.setLeft(min);
                    } else {
                        previousNode.setRight(min);
                    }

                    min.setLeft(currentNode.getLeft());
                    min.setRight(currentNode.getRight());
                }

                count--;
                return true;
            }
        }

        return false;
    }
}