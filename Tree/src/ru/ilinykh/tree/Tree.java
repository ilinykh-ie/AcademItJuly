package ru.ilinykh.tree;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

public class Tree<T> {
    private TreeNode<T> root;
    private int count;
    Comparator<T> comparator;

    public Tree() {
    }

    public Tree(T data) {
        root = new TreeNode<>(data);
        count = 1;
    }

    public Tree(Comparator<T> comparator) {
        this.comparator = comparator;
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
            TreeNode<T> currentElement = queue.removeFirst();

            consumer.accept(currentElement.getData());

            if (currentElement.getLeft() != null) {
                queue.addLast(currentElement.getLeft());
            }

            if (currentElement.getRight() != null) {
                queue.addLast(currentElement.getRight());
            }
        }
    }

    public void depthFirst(Consumer<T> consumer) {
        if (root == null) {
            return;
        }

        Deque<TreeNode<T>> stack = new LinkedList<>();

        stack.add(root);

        while (!stack.isEmpty()) {
            TreeNode<T> currentElement = stack.removeLast();

            consumer.accept(currentElement.getData());

            if (currentElement.getRight() != null) {
                stack.addLast(currentElement.getRight());
            }

            if (currentElement.getLeft() != null) {
                stack.addLast(currentElement.getLeft());
            }
        }
    }

    public void depthFirstRecursion(Consumer<T> consumer) {
        depthFirstRecursionNode(consumer, root);
    }

    private void depthFirstRecursionNode(Consumer<T> consumer, TreeNode<T> node) {
        if (root == null) {
            return;
        }

        consumer.accept(node.getData());

        if (node.getLeft() != null) {
            depthFirstRecursionNode(consumer, node.getLeft());
        }

        if (node.getRight() != null) {
            depthFirstRecursionNode(consumer, node.getRight());
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
            //noinspection unchecked
            if ((data == null & currentNode.getData() != null) || (data != null && currentNode.getData() != null &&
                    (comparator != null && comparator.compare(data, currentNode.getData()) < 0 ||
                            ((Comparable<T>) data).compareTo(currentNode.getData()) < 0))) {
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
        if (root == null) {
            return false;
        }

        TreeNode<T> currentNode = root;

        for (; ; ) {
            if (data == currentNode.getData() || currentNode.getData() != null && currentNode.getData().equals(data)) {
                return true;
            }

            //noinspection unchecked
            if ((data == null & currentNode.getData() != null) || data != null && currentNode.getData() != null &&
                    (comparator != null && comparator.compare(data, currentNode.getData()) < 0 ||
                            ((Comparable<T>) data).compareTo(currentNode.getData()) < 0)) {
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
        if (root == null) {
            return false;
        }

        TreeNode<T> currentNode = root;

        if (data == currentNode.getData() || currentNode.getData() != null && currentNode.getData().equals(data)) {
            if (currentNode.getRight() == null && currentNode.getLeft() == null) {
                root = null;
            } else if (currentNode.getRight() == null) {
                setRoot(currentNode.getLeft());
            } else if (currentNode.getLeft() == null) {
                setRoot(currentNode.getRight());
            } else {
                TreeNode<T> min = currentNode.getRight();
                TreeNode<T> previousMin = currentNode;

                while (min.getLeft() != null) {
                    previousMin = min;
                    min = min.getLeft();
                }

                if (!previousMin.equals(root)) {
                    if (min.getRight() != null) {
                        previousMin.setLeft(min.getRight());
                    } else {
                        previousMin.setLeft(null);
                    }

                    min.setRight(currentNode.getRight());
                }

                min.setLeft(currentNode.getLeft());
                root = min;
            }

            count--;
            return true;
        }

        TreeNode<T> previousNode;

        for (; ; ) {
            //noinspection unchecked
            if ((data == null & currentNode.getData() != null) || data != null && currentNode.getData() != null &&
                    (comparator != null && comparator.compare(data, currentNode.getData()) < 0 ||
                            ((Comparable<T>) data).compareTo(currentNode.getData()) < 0)) {
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

            if (data == currentNode.getData() || currentNode.getData() != null && currentNode.getData().equals(data)) {
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