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

        ArrayList<Integer> result = new ArrayList<>(list.size());

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

        ArrayList<Integer> integerList = new ArrayList<>(Arrays.asList(0, -5, 18, 12, 3, 1, -10, 0, 18, 20, -3));
        System.out.println("Список целых чисел: " + integerList);

        for (int i = 0; i < integerList.size(); i++) {
            if (integerList.get(i) % 2 == 0) {
                integerList.remove(i);
                i--;
            }
        }

        System.out.println("Список без четных чисел: " + integerList);

        ArrayList<Integer> randomList = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 5, 5, 8, 7, 7, 7, 1, 1, 3, 3));
        System.out.println("Произвольный список: " + randomList);

        ArrayList<Integer> listWithoutRepeats = getListWithoutRepeats(randomList);
        System.out.println("Список без повторяющихся элементов: " + listWithoutRepeats);
    }
}