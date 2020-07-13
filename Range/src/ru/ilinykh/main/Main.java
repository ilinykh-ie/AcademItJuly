package ru.ilinykh.main;

import ru.ilinykh.range.Range;

public class Main {
    public static void main(String[] args) {
        Range range = new Range(50.4, 99.2);

        range.setTo(100.3);
        range.setFrom(45.2);
        double number1 = 70.5;
        double number2 = 110.4;
        double from = range.getFrom();
        double to = range.getTo();
        boolean isInside1 = range.isInside(number1);
        boolean isInside2 = range.isInside(number2);
        double length = range.getLength();

        if (isInside1) {
            System.out.printf("Число %.2f входит в диапазон от %.2f до %.2f. Длина диапазона %.2f.%n", number1, from, to, length);
        }

        if (isInside2) {
            System.out.printf("Число %.2f входит в диапазон от %.2f до %.2f.Длина диапазона %.2f.%n", number2, from, to, length);
        }

        Range range1 = new Range(10.5, 30.6);
        Range range2 = new Range(15.40, 23.4);

        Range range3 = range1.getIntersection(range2);

        if (range3 == null) {
            System.out.println("Пересечений нет");
        } else {
            System.out.println("Пересечение: " + range3.toString());
        }

        Range[] union = range1.getUnion(range2);

        if (union.length > 1) {
            System.out.println("Объединение 1: " + union[0].toString() + ", 2: " + union[1].toString());
        } else {
            System.out.println("Объединение: " + union[0].toString());
        }

        Range[] difference = range1.getDifference(range2);

        if (difference.length == 0) {
            System.out.println("Результат разности интервалов равен 0.");
        } else if (difference.length > 1) {
            System.out.println("Разность интеравалов 1: " + difference[0].toString() + ", 2: " + difference[1].toString());
        } else {
            System.out.println("Разность интеравалов: " + difference[0].toString());
        }
    }
}