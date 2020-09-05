package ru.ilinykh.graph_main;

import ru.ilinykh.graph.Graph;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        int[][] array = {
                //0, 1, 2, 3, 4, 5, 6, 7
                {0, 0, 0, 0, 0, 0, 0, 0}, //0
                {0, 0, 0, 0, 0, 0, 1, 1}, //1
                {0, 0, 0, 0, 0, 0, 1, 0}, //2
                {0, 0, 0, 0, 0, 0, 1, 0}, //3
                {0, 0, 0, 0, 0, 1, 0, 0}, //4
                {0, 0, 0, 0, 1, 0, 0, 0}, //5
                {0, 0, 1, 1, 0, 0, 0, 0}, //6
                {0, 1, 0, 0, 0, 0, 0, 0}  //7
        };

        Graph graph = new Graph(array);

        Consumer<Integer> print = a -> System.out.print(a + " ");

        graph.breadthFirst(print);
        System.out.println(" обход в ширину.");

        graph.depthFirst(print);
        System.out.println(" обход в глубину.");

        graph.depthFirstRecursion(print);
        System.out.println(" обход в глубину с рекурсией.");
    }
}
