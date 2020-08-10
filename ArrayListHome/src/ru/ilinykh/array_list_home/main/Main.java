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

        if (list.size() == 0) {
            return new ArrayList<>();
        }

        ArrayList<Integer> result = new ArrayList<>();
        result.add(list.get(0));

        for (Integer e : list) {
            if (!result.contains(e)) {
                result.add(e);
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

        System.out.println("Список без четных чисел: " + integerList);

        ArrayList<Integer> integerList2 = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 5, 5, 8, 7, 7, 7, 1, 1, 3, 3));
        System.out.println("Произвольный список: " + integerList2);
        ArrayList<Integer> integerList3 = getListWithoutRepeats(integerList2);
        System.out.println("Список без повторяющихся элементов: " + integerList3);
    }
}