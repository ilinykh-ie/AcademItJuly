package ru.ilinykh.tree_main;

import ru.ilinykh.tree.Tree;
import ru.ilinykh.tree.TreeNode;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        TreeNode<Integer> node = new TreeNode<>(8);
        Tree<Integer> tree = new Tree<>(node);
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
        System.out.print(" - обход в ширину \n");

        tree.depthFirst(print);
        System.out.print(" - обход в глубину \n");

        tree.depthFirstRecursion(print, tree.getRoot());
        System.out.print(" - обход в глубину рекурсией \n");

        Integer number = 8;
        System.out.println("Содержит ли дерево число " + number + ": " + tree.contains(number));

        Integer number2 = 10;
        boolean isDeleted = tree.remove(number2);
        tree.breadthFirst(print);
        System.out.print(" - дерево после удаления \n");
        System.out.println("Удален ли элемент " + number2 + ": " + isDeleted);
        System.out.println("Количество элесентов в дереве = " + tree.size());
    }
}
