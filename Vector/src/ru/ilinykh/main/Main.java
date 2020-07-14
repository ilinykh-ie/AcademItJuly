package ru.ilinykh.main;

import ru.ilinykh.vector.Vector;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        double[] array1 = {1, 2, 3, 4, 6, 10, 15};
        double[] array2 = {1, 2, 3, 4, 6, 10, 0};
        Vector vector1 = new Vector(array1.length, array1);
        Vector vector2 = new Vector(array2);

        vector1.getSum(vector2);
        System.out.println("Сумма векторов 1 и 2 равна " + vector1.toString());

        vector1.getDiff(vector2);
        System.out.println("Разность векторов 1 и 2 равна " + vector1.toString());

        Vector vector3 = new Vector(10);
        System.out.println("Размерность вектора 3 равна " + vector3.getSize());

        vector1.getMultiplication(1.8);
        System.out.println("Результат умножения вектора 1 на 1,8 равен " + vector1.toString());

        double vector1Length = vector1.getLength();
        System.out.println("Длина вектора 1 равна " + vector1Length);

        vector2.setVectorComponent(7, 15);
        System.out.println(vector2.getVectorComponent(7));

        if (vector1.equals(vector2)) {
            System.out.println("Векторы 1 и 2 равны");
        } else {
            System.out.println("Векторы 1 и 2 не равны");
        }

        vector1.expandVector();
        System.out.println("Развернутый вектор 1 равен " + vector1.toString());

        Vector vector4 = Vector.sum(vector1, vector2);
        System.out.println("Сумма векторов 1 и 2 равна " + vector4.toString());

        Vector vector5 = Vector.diff(vector1, vector2);
        System.out.println("Разность векторов 1 и 2 равна " + vector5.toString());

        double scalar = Vector.scalarProduct(vector1, vector2);
        System.out.println("Скалярное произведение векторов 1 и 2 равна " + scalar);
    }
}