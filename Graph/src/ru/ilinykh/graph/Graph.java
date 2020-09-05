package ru.ilinykh.graph;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class Graph {
    private final int[][] connections;
    private final boolean[] isVisited;

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

        isVisited = new boolean[connections.length];
    }

    public void breadthFirst(Consumer<Integer> consumer) {
        Arrays.fill(isVisited, false);

        int length = connections.length;

        Queue<Integer> queue = new LinkedList<>();

        queue.add(0);
        isVisited[0] = true;
        int searchStart = 1;

        while (!queue.isEmpty()) {
            int currentElement = queue.remove();

            consumer.accept(currentElement);

            for (int i = 0; i < length; i++) {
                if (i != currentElement && connections[currentElement][i] == 1 && !isVisited[i]) {
                    queue.add(i);
                    isVisited[i] = true;
                }
            }

            if (queue.isEmpty()) {
                for (int i = searchStart; i < length; i++) {
                    if (!isVisited[i]) {
                        queue.add(i);
                        isVisited[i] = true;
                        searchStart = i + 1;
                        break;
                    }
                }
            }
        }
    }

    public void depthFirst(Consumer<Integer> consumer) {
        Arrays.fill(isVisited, false);

        int length = connections.length;

        Deque<Integer> stack = new LinkedList<>();

        stack.addLast(0);
        isVisited[0] = true;
        int searchStart = 1;

        while (!stack.isEmpty()) {
            int currentElement = stack.removeLast();

            consumer.accept(currentElement);

            for (int i = length - 1; i >= 0; i--) {
                if (i != currentElement && connections[currentElement][i] == 1 && !isVisited[i]) {
                    stack.addLast(i);
                    isVisited[i] = true;
                }
            }

            if (stack.isEmpty()) {
                for (int i = searchStart; i < length; i++) {
                    if (!isVisited[i]) {
                        stack.addLast(i);
                        isVisited[i] = true;
                        searchStart = i + 1;

                        break;
                    }
                }
            }
        }
    }

    public void depthFirstRecursion(Consumer<Integer> consumer) {
        Arrays.fill(isVisited, false);
        depthFirstRecursionNumber(consumer, 0);

        int length = connections.length;

        for (int i = 1; i < length; i++) {
            if (!isVisited[i]) {
                depthFirstRecursionNumber(consumer, i);
            }
        }

    }

    private void depthFirstRecursionNumber(Consumer<Integer> consumer, int number) {
        int length = connections.length;
        isVisited[number] = true;

        consumer.accept(number);

        for (int i = 0; i < length; i++) {
            if (i != number && connections[number][i] == 1 && !isVisited[i]) {
                depthFirstRecursionNumber(consumer, i);
            }
        }
    }
}