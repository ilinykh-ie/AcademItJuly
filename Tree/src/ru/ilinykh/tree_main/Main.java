package ru.ilinykh.tree_main;

import ru.ilinykh.tree.Tree;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>(8);
        tree.add(3);
        tree.add(1);
        tree.add(6);
        tree.add(10);
        tree.add(14);
        tree.add(13);
        tree.add(7);
        tree.add(4);
        tree.add(9);
        tree.add(6);
        tree.add(3);
        tree.add(5);
        tree.add(6);

        Consumer<Integer> print = a -> System.out.print(a + " ");

        tree.breadthFirst(print);
        System.out.println(" - обход в ширину");

        tree.depthFirst(print);
        System.out.println(" - обход в глубину");

        tree.depthFirstRecursion(print);
        System.out.println(" - обход в глубину рекурсией");

        Integer number = 8;
        System.out.println("Содержит ли дерево число " + number + ": " + tree.contains(number));

        Integer number2 = 10;
        boolean isDeleted = tree.remove(number2);
        tree.breadthFirst(print);
        System.out.println(" - дерево после удаления ");
        System.out.println("Удален ли элемент " + number2 + ": " + isDeleted);
        System.out.println("Количество элесентов в дереве = " + tree.size());

        Tree<String> tree1 = new Tree<>();
        tree1.add(null);
        tree1.add("element2");
        tree1.add(null);
        tree1.add("element1");
        tree1.add("element3");

        Consumer<String> print1 = a -> System.out.print(a + " ");
        tree1.breadthFirst(print1);
        System.out.println("список с null значениями");

        System.out.println("Содержтся ли null в списке: " + tree1.contains(null));

        tree1.remove(null);
        tree1.breadthFirst(print1);
        System.out.println("список после уданения 1го элемента с null значением");

        Comparator<Integer> comparator = (o1, o2) -> {
            if (Objects.equals(o1, o2)) {
                return 0;
            }

            if (o2 == null || o1 != null && o1 < o2) {
                return -1;
            }

            return 1;
        };

        Tree<Integer> tree2 = new Tree<>(comparator);
        tree2.add(5);
        tree2.add(10);
        tree2.add(2);
        tree2.add(null);

        tree2.breadthFirst(print);
        System.out.println();
    }
}