package ru.ilinykh.graph;

import java.util.Deque;
import java.util.LinkedList;
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
        boolean[] isVisited = new boolean[connections.length];

        int length = connections.length;

        Deque<Integer> queue = new LinkedList<>();

        queue.add(0);
        isVisited[0] = true;

        while (!queue.isEmpty()) {
            int currentElement = queue.getFirst();
            queue.removeFirst();

            consumer.accept(currentElement);

            for (int i = 0; i < length; i++) {
                if (i != currentElement && connections[currentElement][i] == 1 && !isVisited[i]) {
                    queue.addLast(i);
                    isVisited[i] = true;
                }
            }

            if (queue.isEmpty()) {
                for (int i = 0; i < length; i++) {
                    if (!isVisited[i]) {
                        queue.add(i);
                        isVisited[i] = true;

                        break;
                    }
                }
            }
        }
    }

    public void depthFirst(Consumer<Integer> consumer) {
        boolean[] isVisited = new boolean[connections.length];

        int length = connections.length;

        Deque<Integer> stack = new LinkedList<>();

        stack.add(0);
        isVisited[0] = true;

        while (!stack.isEmpty()) {
            int currentElement = stack.getLast();
            stack.removeLast();

            consumer.accept(currentElement);

            for (int i = length - 1; i > 0; i--) {
                if (i != currentElement && connections[currentElement][i] == 1 && !isVisited[i]) {
                    stack.addLast(i);
                    isVisited[i] = true;
                }
            }

            if (stack.isEmpty()) {
                for (int i = 0; i < length; i++) {
                    if (!isVisited[i]) {
                        stack.add(i);
                        isVisited[i] = true;

                        break;
                    }
                }
            }
        }
    }
}