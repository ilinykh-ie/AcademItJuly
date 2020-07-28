package ru.ilinykh.matrix_main;

import ru.ilinykh.matrix.Matrix;
import ru.ilinykh.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Matrix matrix = new Matrix(4, 5);
        Vector vector = matrix.getRow(0);
        System.out.println("Вектор-строка матрицы: " + vector);

        double[] array1 = {1, 5, -3, 34};
        double[] array2 = {0, 8, 13};
        Vector vector1 = new Vector(array1);
        Vector vector2 = new Vector(array2);
        Vector[] vectors = {vector1, vector2};
        matrix = new Matrix(vectors);
        System.out.println("Матрица из массива векторов: " + matrix);

        matrix.setRow(1, vector1);
        System.out.println("Матрица с после вставки вектора: " + matrix);

        vector2 = matrix.getColumn(3);
        System.out.println("Вектор-столбец матрицы: " + vector2);

        double[][] array = {{5, 9, 13, 5}, {-10, 15, 5, -5}, {5, 11, 15, 8}, {5, -3, 10, 0}};
        Matrix matrix1 = new Matrix(array);
        double determinant = matrix1.getDeterminant();
        System.out.println("Определитель марицы равен: " + determinant);

        double[][] array3 = {{4, 5, 9, 10}, {2, -10, 15, 10}, {3, 5, 11, 10}, {6, -3, 12, 44}};
        Matrix matrix2 = new Matrix(array3);
        matrix2.multiplyByNumber(10);
        System.out.println("Результат умножения: " + matrix2);

        matrix2.transpose();
        System.out.println("Развернутая матрица: " + matrix2);

        Matrix matrix3 = new Matrix(matrix1);
        Vector vector4 = matrix3.multiplyByVector(vector1);
        System.out.println("Произведение матрицы на вектор: " + vector4);

        matrix1.sum(matrix2);
        System.out.println("Результат суммы матриц: " + matrix1);

        matrix1.subtract(matrix2);
        System.out.println("Результат разницы матриц: " + matrix1);

        Matrix matrix4 = Matrix.getSum(matrix1, matrix2);
        System.out.println("Результат суммы матриц: " + matrix4);

        Matrix matrix5 = Matrix.getDifference(matrix4, matrix2);
        System.out.println("Результат разницы матриц: " + matrix5);

        double[][] array4 = {{1, 0}, {0, 1}, {1, 1}};
        double[][] array5 = {{1, 2, 1}, {0, 1, 2}};
        Matrix matrix6 = new Matrix(array4);
        Matrix matrix7 = new Matrix(array5);
        Matrix matrix8 = Matrix.getMultiplication(matrix6, matrix7);
        System.out.println("Результат произведения матриц: " + matrix8);
    }
}
