package ru.ilinykh.array_list_home.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static ArrayList<Integer> getListWithoutRepeats(ArrayList<Integer> list) {
        if (list == null) {
            return null;
        }

        ArrayList<Integer> result = new ArrayList<>();
        result.add(list.get(0));

        for (int i = 1; i < list.size(); i++) {
            int temp = list.get(i);

            if (!result.contains(temp)) {
                result.add(temp);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        ArrayList<String> listFromFile = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream("ArrayListHome\\input.txt"))) {
            while (scanner.hasNextLine()) {
                listFromFile.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

        System.out.println("Список из файла: " + listFromFile);

        ArrayList<Integer> integerList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            integerList.add(i);
        }

        System.out.println("Список целых чисел: " + integerList);

        for (int i = 0; i < integerList.size(); i++) {
            if (integerList.get(i) % 2 == 0) {
                integerList.remove(i);
                i--;
            }
        }

        integerList.add(5);

        System.out.println("Список без четных чисел: " + integerList);

        ArrayList<Integer> integerList2 = new ArrayList<>(Arrays.asList(1, 1, 2, 1, 1, 15, 2, 2, 2, 2, 3, 3, 3, 8, 3, 8));
        System.out.println("Произвольный список: " + integerList2);
        ArrayList<Integer> integerList3 = getListWithoutRepeats(integerList2);
        System.out.println("Список без повторяющихся элементов: " + integerList3);
    }
}