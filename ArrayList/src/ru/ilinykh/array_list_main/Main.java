package ru.ilinykh.array_list_main;

import ru.ilinykh.array_list.MyArrayList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 4, 5};
        MyArrayList<Integer> arrayList = new MyArrayList<>(array);
        arrayList.add(6);
        arrayList.add(0, 0);
        arrayList.remove((Integer) 4);
        System.out.println("Полученный список " + arrayList);
        int deleted = arrayList.remove(1);
        System.out.println(arrayList + " удаленный элемент = " + deleted);

        arrayList.ensureCapacity(15);
        arrayList.trimToSize();

        MyArrayList<Integer> arrayList1 = new MyArrayList<>(6);
        arrayList1.addAll(arrayList);
        arrayList1.add(3);
        arrayList1.addAll(2, arrayList);

        System.out.println("Полученный список после изменений " + arrayList1);
        System.out.println("Первый индекс числа 3 в списке: " + arrayList1.indexOf(3));
        System.out.println("Последний индекс числа 3 в списке: " + arrayList1.lastIndexOf(3));

        Integer[] array2 = (Integer[]) arrayList.toArray();
        System.out.println("Массив, полученный из списка: " + Arrays.toString(array2));

        String[] array1 = {"один", "два", "два", "три", "три", null};
        MyArrayList<String> arrayList2 = new MyArrayList<>(array1);
        System.out.println("Список строк: " + arrayList2);

        String[] array3 = {"два", "три", null, null};
        MyArrayList<String> arrayList3 = new MyArrayList<>(array3);
        arrayList2.retainAll(arrayList3);

        System.out.println("Список 2: " + arrayList2);
        System.out.println("Список 3: " + arrayList3);
        System.out.println("Содержатся ли все элементы списка 3 в списке 2: " + arrayList2.containsAll(arrayList3));

        arrayList2.add(1, null);
        arrayList2.add(3, null);
        System.out.println("Список 2 после добавления null значений: " + arrayList2);

        arrayList2.remove(null);
        arrayList2.remove(null);
        System.out.println("Список 2 после удаления null значеня: " + arrayList2);

        System.out.println("Содержит ли список 2 null: " + arrayList2.contains(null));

        Number[] numbers = {1, 3, 4, 5, 6, 65, 98, 35.63};
        numbers = arrayList1.toArray(numbers);
        System.out.println("Массив чисел, полученный списка " + Arrays.toString(numbers));
    }
}