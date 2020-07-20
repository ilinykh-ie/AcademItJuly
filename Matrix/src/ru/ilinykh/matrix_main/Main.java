package ru.ilinykh.matrix_main;

import ru.ilinykh.matrix.Matrix;
import ru.ilinykh.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(4, 5);
        Vector vector = matrix.getRowVector(0);
        System.out.println("Вектор-строка матрицы: " + vector);

        double[] array1 = {1, 5, -3, 34};
        double[] array2 = {0, 8, 13};
        Vector vector1 = new Vector(array1);
        Vector vector2 = new Vector(array2);
        Vector[] vectors = {vector1, vector2};
        matrix = new Matrix(vectors);
        System.out.println("Матрица из массива векторов: " + matrix);

        matrix.setRowVector(1, vector1);
        System.out.println("Матрица с после вставки вектора: " + matrix);

        vector2 = matrix.getColumnVector(3);
        System.out.println("Вектор-столбец матрицы: " + vector2);

        double[][] array = {{4, 5, 9, 13}, {2, -10, 15, 5}, {3, 5, 11, 15}, {1, 5, -3, 10}};
        Matrix matrix1 = new Matrix(array);
        double determinant = matrix1.getDeterminant();
        System.out.println("Определитель марицы равен: " + determinant);

        Matrix matrix2 = new Matrix(matrix1);
        matrix2.multiplicationByNumber(10);
        System.out.println("Результат умножения: " + matrix2);

        matrix2.transpose();
        System.out.println("Развернутая матрица: " + matrix2);

        Matrix matrix3 = new Matrix(matrix1);
        matrix3.multiplicationByVector(vector1);
        System.out.println("Произведение матрицы на вектор: " + matrix3);

        matrix1.sum(matrix2);
        System.out.println("Результат суммы матриц: " + matrix1);

        matrix1.difference(matrix2);
        System.out.println("Результат разницы матриц: " + matrix1);

        Matrix matrix4 = Matrix.getSum(matrix1, matrix2);
        System.out.println("Результат суммы матриц: " + matrix4);

        Matrix matrix5 = Matrix.getDifference(matrix4, matrix2);
        System.out.println("Результат разницы матриц: " + matrix5);

        Matrix matrix6 = Matrix.getMultiplication(matrix1, matrix2);
        System.out.println("Результат разницы матриц: " + matrix6);
    }
}
