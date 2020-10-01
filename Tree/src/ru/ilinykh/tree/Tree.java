package ru.ilinykh.tree;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class Tree<T> {
    private TreeNode<T> root;
    private int count;
    private Comparator<T> comparator;

    public Tree() {
    }

    public Tree(T data) {
        root = new TreeNode<>(data);
        count = 1;
    }

    public Tree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private int compare(T data1, T data2) {
        if (comparator != null) {
            return comparator.compare(data1, data2);
        }

        if (data2 == data1) {
            return 0;
        }

        if (data2 == null) {
            return -1;
        }

        if (data1 == null) {
            return 1;
        }

        //noinspection unchecked
        return ((Comparable<T>) data2).compareTo(data1);
    }

    public int size() {
        return count;
    }

    public void breadthFirst(Consumer<T> consumer) {
        if (root == null) {
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> currentElement = queue.remove();

            consumer.accept(currentElement.getValue());

            if (currentElement.getLeft() != null) {
                queue.add(currentElement.getLeft());
            }

            if (currentElement.getRight() != null) {
                queue.add(currentElement.getRight());
            }
        }
    }

    public void depthFirst(Consumer<T> consumer) {
        if (root == null) {
            return;
        }

        Deque<TreeNode<T>> stack = new LinkedList<>();

        stack.addLast(root);

        while (!stack.isEmpty()) {
            TreeNode<T> currentElement = stack.removeLast();

            consumer.accept(currentElement.getValue());

            if (currentElement.getRight() != null) {
                stack.addLast(currentElement.getRight());
            }

            if (currentElement.getLeft() != null) {
                stack.addLast(currentElement.getLeft());
            }
        }
    }

    public void depthFirstRecursion(Consumer<T> consumer) {
        if (root == null) {
            return;
        }

        depthFirstRecursionNode(consumer, root);
    }

    private void depthFirstRecursionNode(Consumer<T> consumer, TreeNode<T> node) {
        consumer.accept(node.getValue());

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
            count = 1;
            return;
        }

        TreeNode<T> currentNode = root;

        for (; ; ) {
            if (compare(currentNode.getValue(), data) < 0) {
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
            int comparisonResult = compare(currentNode.getValue(), data);

            if (comparisonResult == 0) {
                return true;
            }

            if (comparisonResult < 0) {
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

        if (compare(currentNode.getValue(), data) == 0) {
            if (currentNode.getRight() == null && currentNode.getLeft() == null) {
                root = null;
            } else if (currentNode.getRight() == null) {
                root = currentNode.getLeft();
            } else if (currentNode.getLeft() == null) {
                root = currentNode.getRight();
            } else {
                TreeNode<T> min = currentNode.getRight();
                TreeNode<T> previousMin = currentNode;

                while (min.getLeft() != null) {
                    previousMin = min;
                    min = min.getLeft();
                }

                if (previousMin != root) {
                    previousMin.setLeft(min.getRight());
                    min.setRight(currentNode.getRight());
                }

                min.setLeft(currentNode.getLeft());
                root = min;
            }

            count--;
            return true;
        }

        TreeNode<T> previousNode = currentNode;

        for (; ; ) {
            int comparisonResult = compare(currentNode.getValue(), data);

            if (comparisonResult < 0) {
                if (currentNode.getLeft() != null) {
                    previousNode = currentNode;
                    currentNode = currentNode.getLeft();
                } else {
                    break;
                }
            } else if (comparisonResult > 0) {
                if (currentNode.getRight() != null) {
                    previousNode = currentNode;
                    currentNode = currentNode.getRight();
                } else {
                    break;
                }
            } else {
                if (currentNode.getRight() == null && currentNode.getLeft() == null) {
                    if (previousNode.getLeft() == currentNode) {
                        previousNode.setLeft(null);
                    } else {
                        previousNode.setRight(null);
                    }
                } else if (currentNode.getRight() == null) {
                    if (previousNode.getLeft() == currentNode) {
                        previousNode.setLeft(currentNode.getLeft());
                    } else {
                        previousNode.setRight(currentNode.getLeft());
                    }
                } else if (currentNode.getLeft() == null) {
                    if (previousNode.getLeft() == currentNode) {
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

                    if (previousNode.getLeft() == currentNode) {
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