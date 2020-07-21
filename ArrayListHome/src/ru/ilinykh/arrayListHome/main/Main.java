package ru.ilinykh.arrayListHome.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> listFromFile = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream("ArrayListHome\\input.txt"))) {

            while (scanner.hasNextInt()) {
                listFromFile.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }

        System.out.println("Список из файла: " + listFromFile);

        for (int i = 0; i < listFromFile.size(); i++) {
            if (listFromFile.get(i) % 2 == 0) {
                listFromFile.remove(i);
                i--;
            }
        }

        System.out.println("Список из файла без четных чисел: " + listFromFile);

        for (int i = 1; i < listFromFile.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (listFromFile.get(j).intValue() == listFromFile.get(i).intValue()) {
                    listFromFile.remove(i);
                    i--;
                    break;
                }
            }
        }

        System.out.println("Предыдущий список без повторяющихся чисел: " + listFromFile);
    }
}