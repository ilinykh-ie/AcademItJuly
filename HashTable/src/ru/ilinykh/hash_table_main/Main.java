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

        System.out.println("Длина хэш-таблицы " + numbers1 + " равна " + numbers1.size());

        int x = 0;

        for (Integer i : numbers1) {
            x += i;
        }

        System.out.println("Сумма элементов хэш-таблицы равна " + x);

        HashTable<Integer> numbers2 = new HashTable<>();
        numbers2.add(33);
        numbers2.add(22);
        numbers2.add(100);
        System.out.println("Хэш-таблица №2: " + numbers2);

        numbers1.addAll(numbers2);

        System.out.println("Хэш-таблица №1 с элементами 2 хэш-таблицы: " + numbers1);
        System.out.println("Содержатся ли элементы 2й хэш-таблицы в 1й хэш-таблице: " + numbers1.containsAll(numbers2));

        numbers1.retainAll(numbers2);

        Object[] array = numbers1.toArray();
        System.out.println("Массив объектов, полученный из хэш-таблицы " + Arrays.toString(array));

        Number[] numbers = {1, 3, 4, 5, 6, 7, 8};
        numbers = numbers1.toArray(numbers);
        System.out.println("Массив чисел, полученный из хэш-таблицы " + Arrays.toString(numbers));

        numbers1.add(null);
        numbers1.add(null);
        System.out.println("Хэш-таблица после добавления null значений " + numbers1);

        System.out.println("Содержится ли null в хэш-таблице: " + numbers1.contains(null));

        numbers1.remove(null);
        System.out.println("Хэш-таблица после удаления null значения " + numbers1);
    }
}
