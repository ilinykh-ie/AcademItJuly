package ru.ilinykh.vector_main;

import ru.ilinykh.vector.Vector;

public class Main {
    public static void main(String[] args) {
        double[] array1 = {1, 2, 3, 4, 5, 15};
        double[] array2 = {1, 2, 3, 4, 6, 10, 0};
        Vector vector1 = new Vector(array1.length, array1);
        Vector vector2 = new Vector(array2);

        vector1.sum(vector2);
        System.out.println("Сумма векторов 1 и 2 равна " + vector1);

        vector1.subtract(vector2);
        System.out.println("Разность векторов 1 и 2 равна " + vector1);

        Vector vector3 = new Vector(10);
        System.out.println("Размерность вектора 3 равна " + vector3.getSize());

        vector1.multiply(1.8);
        System.out.println("Результат умножения вектора 1 на 1,8 равен " + vector1);

        double vector1Length = vector1.getLength();
        System.out.println("Длина вектора 1 равна " + vector1Length);

        vector2.setComponent(6, 15);
        System.out.println("Результат получения компонента вектора" + vector2.getComponent(6));

        if (vector1.equals(vector2)) {
            System.out.println("Векторы 1 и 2 равны");
        } else {
            System.out.println("Векторы 1 и 2 не равны");
        }

        vector1.reverse();
        System.out.println("Развернутый вектор 1 равен " + vector1);

        Vector vector4 = Vector.getSum(vector1, vector2);
        System.out.println("Сумма векторов 1 и 2 равна " + vector4);

        Vector vector5 = Vector.getDifference(vector1, vector2);
        System.out.println("Разность векторов 1 и 2 равна " + vector5);

        double scalar = Vector.getScalarProduct(vector1, vector2);
        System.out.println("Скалярное произведение векторов 1 и 2 равна " + scalar);

        Vector vector6 = new Vector(vector5);
        System.out.println(vector6);
    }
}