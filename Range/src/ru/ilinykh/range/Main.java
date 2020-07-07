package ru.ilinykh.range;

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

        Range interval1 = new Range(12.3, 50.6);
        Range interval2 = new Range(25.40, 39.2);


        Range interval3 = interval1.getIntersection(interval2);

        if (interval3 == null) {
            System.out.println("Пересечений нет");
        } else {
            System.out.printf("Начало пересечения %.2f, конец пересечения %.2f.%n", interval3.getFrom(), interval3.getTo());
        }

        Range[] union = interval1.getUnion(interval2);

        if (union.length > 1) {
            System.out.printf("Объединение от %.2f до %.2f и от %.2f до %.2f.%n", union[0].getFrom(), union[0].getTo(), union[1].getFrom(), union[1].getTo());
        } else {
            System.out.printf("Начало объединения %.2f, конец объединения %.2f.%n", union[0].getFrom(), union[0].getTo());
        }

        Range[] difference = interval1.getDifference(interval2);

        if (difference == null) {
            System.out.println("Результат разности интервалов равен 0.");
        } else if (difference.length > 1) {
            System.out.printf("Разность интеравалов от %.2f до %.2f и от %.2f до %.2f.%n",
                    difference[0].getFrom(), difference[0].getTo(), difference[1].getFrom(), difference[1].getTo());
        } else {
            System.out.printf("Начало разности интеравалов %.2f, конец %.2f.%n", difference[0].getFrom(), difference[0].getTo());
        }
    }
}