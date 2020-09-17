package ru.ilinykh.graph;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class Graph {
    private final int[][] connections;

    public Graph(int[][] array) {
        if (array == null) {
            throw new NullPointerException("Массив не должен быть null");
        }

        if (array.length < 1) {
            throw new IllegalArgumentException("Массив должен быть не пустой");
        }

        for (int[] e : array) {
            if (e.length != array.length) {
                throw new IllegalArgumentException("Высота и ширина двумерного массива должны быть равны");
            }
        }

        connections = array.clone();
    }

    public void breadthFirst(Consumer<Integer> consumer) {
        int length = connections.length;
        boolean[] isVisited = new boolean[length];

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < length; i++) {
            if (!isVisited[i]) {
                queue.add(i);
                isVisited[i] = true;

                while (!queue.isEmpty()) {
                    int currentElement = queue.remove();

                    consumer.accept(currentElement);

                    for (int j = 0; j < length; j++) {
                        if (j != currentElement && connections[currentElement][j] == 1 && !isVisited[j]) {
                            queue.add(j);
                            isVisited[j] = true;
                        }
                    }
                }
            }
        }
    }

    public void depthFirst(Consumer<Integer> consumer) {
        int length = connections.length;
        boolean[] isVisited = new boolean[length];

        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < length; i++) {
            if (!isVisited[i]) {
                stack.addLast(i);
                isVisited[i] = true;

                while (!stack.isEmpty()) {
                    int currentElement = stack.removeLast();

                    consumer.accept(currentElement);

                    for (int j = length - 1; j >= 0; j--) {
                        if (j != currentElement && connections[currentElement][j] == 1 && !isVisited[j]) {
                            stack.addLast(j);
                            isVisited[j] = true;
                        }
                    }
                }
            }
        }
    }

    public void depthFirstRecursion(Consumer<Integer> consumer) {
        int length = connections.length;
        boolean[] isVisited = new boolean[length];

        for (int i = 0; i < length; i++) {
            if (!isVisited[i]) {
                depthFirstRecursionNumber(consumer, i, isVisited);
            }
        }
    }

    private void depthFirstRecursionNumber(Consumer<Integer> consumer, int number, boolean[] isVisited) {
        int length = connections.length;
        isVisited[number] = true;

        consumer.accept(number);

        for (int i = 0; i < length; i++) {
            if (i != number && connections[number][i] == 1 && !isVisited[i]) {
                depthFirstRecursionNumber(consumer, i, isVisited);
            }
        }
    }
}