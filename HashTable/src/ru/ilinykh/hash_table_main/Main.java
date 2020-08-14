package ru.ilinykh.hash_table_main;

import ru.ilinykh.hash_table.HashTable;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        HashTable<Integer> numbers1 = new HashTable<>(50);
        numbers1.add(500);
        numbers1.add(100);
        numbers1.add(1);
        numbers1.remove(500);

        System.out.println("Длина списка " + numbers1 + " равна " + numbers1.size());

        int x = 0;

        for (Integer i : numbers1) {
            x += i;
        }

        System.out.println("Сумма элементов списка равна " + x);

        HashTable<Integer> numbers2 = new HashTable<>();
        numbers2.add(33);
        numbers2.add(22);
        numbers2.add(100);
        System.out.println("Список №2: " + numbers2);

        numbers1.addAll(numbers2);
        System.out.println("Список №1 с элементами 2 списка: " + numbers1);
        System.out.println("Содержатся ли элементы 2го списка в 1м списке: " + numbers1.containsAll(numbers2));

        numbers1.retainAll(numbers2);

        Object[] array = numbers1.toArray();
        System.out.println("Массив объектов, полученный из списка " + Arrays.toString(array));

        Number[] numbers = {1, 3, 4, 5, 6, 7};
        numbers = numbers1.toArray(numbers);
        System.out.println("Массив чисел, полученный из списка " + Arrays.toString(numbers));
    }
}
