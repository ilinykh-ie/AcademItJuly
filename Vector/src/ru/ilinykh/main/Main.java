package ru.ilinykh.main;

import ru.ilinykh.vector.Vector;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        double[] array1 = {1, 2, 3, 4, 6, 10, 15};
        double[] array2 = {1, 2, 3, 4, 6, 10, 0};
        Vector vector1 = new Vector(array1.length, array1);
        Vector vector2 = new Vector(array2);

        Vector vector3 = vector1.getDiff(vector2);
        System.out.println("Разность векторов 1 и 2 равна " + vector3.toString());

        Vector vector4 = new Vector(10);
        System.out.println("Размерность вектора 4 равна " + vector4.getSize());

        Vector vector5 = vector1.getSum(vector2);
        System.out.println("Сумма векторов 1 и 2 равна " + vector5.toString());

        Vector vector6 = vector1.getMultiplication(1.8);
        System.out.println("Результат умножения вектора 1 на 1,8 равен " + vector6.toString());

        double vector1Length = vector1.getLength();
        System.out.println("Длина вектора 1 равна " + vector1Length);

        vector2.setVectorComponent(7, 15);
        System.out.println(vector2.getVectorComponent(7));

        if (vector1.equals(vector2)) {
            System.out.println("Векторы 1 и 2 равны");
        } else {
            System.out.println("Векторы 1 и 2 не равны");
        }

        Vector vector7 = vector1.expandVector();
        System.out.println("Развернутый вектор 1 равен " + vector7.toString());

        Vector vector8 = Vector.sum(vector1, vector2);
        System.out.println("Сумма векторов 1 и 2 равна " + vector8.toString());

        Vector vector9 = Vector.diff(vector1, vector2);
        System.out.println("Разность векторов 1 и 2 равна " + vector9.toString());

        double scalar = Vector.scalarProduct(vector1, vector2);
        System.out.println("Скалярное произведение векторов 1 и 2 равна " + scalar);
    }
}