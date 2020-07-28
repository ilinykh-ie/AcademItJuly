package ru.ilinykh.matrix;

import ru.ilinykh.vector.Vector;

public class Matrix {
    private Vector[] components;

    public Matrix(int rowsCount, int columnCount) {
        if (rowsCount <= 0 || columnCount <= 0) {
            throw new IllegalArgumentException("Количество строк и столбцов должно быть больше 0");
        }

        components = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            components[i] = new Vector(columnCount);
        }
    }

    public Matrix(Matrix matrix) {
        components = new Vector[matrix.getRowsCount()];

        for (int i = 0; i < matrix.getRowsCount(); i++) {
            components[i] = new Vector(matrix.getColumnsCount());

            for (int j = 0; j < matrix.getColumnsCount(); j++) {
                components[i].setComponent(j, matrix.components[i].getComponent(j));
            }
        }
    }

    public Matrix(double[][] array) {
        int maxLength = array[0].length;

        for (int i = 1; i < array.length; i++) {
            if (array[i].length > maxLength) {
                maxLength = array[i].length;
            }
        }

        components = new Vector[array.length];

        for (int i = 0; i < array.length; i++) {
            components[i] = new Vector(maxLength, array[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        int rowsCount = vectors.length;
        int columnsCount = vectors[0].getSize();

        for (int i = 1; i < vectors.length; i++) {
            if (vectors[i].getSize() > columnsCount) {
                columnsCount = vectors[i].getSize();
            }
        }

        components = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            components[i] = new Vector(columnsCount);
            components[i].sum(vectors[i]);
        }
    }

    public int getRowsCount() {
        return components.length;
    }

    public int getColumnsCount() {
        return components[0].getSize();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");

        for (int i = 0; i < getRowsCount(); i++) {
            stringBuilder.append(components[i]);

            if (i != getRowsCount() - 1) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public Vector getRow(int index) {
        if (index < 0 || index > getRowsCount()) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона матрицы");
        }

        return new Vector(components[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index > getRowsCount()) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона матрицы");
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Длины задаваемого и заменяемого векторов должны быть равны");
        }

        for (int i = 0; i < vector.getSize(); i++) {
            components[index].setComponent(i, vector.getComponent(i));
        }
    }

    public Vector getColumn(int index) {
        if (index < 0 || index > getColumnsCount()) {
            throw new IndexOutOfBoundsException("Индекс вне диапазона матрицы");
        }

        Vector result = new Vector(getRowsCount());

        for (int i = 0; i < getRowsCount(); i++) {
            result.setComponent(i, components[i].getComponent(index));
        }

        return result;
    }

    public void transpose() {
        Vector[] temp = new Vector[getColumnsCount()];

        for (int i = 0; i < getColumnsCount(); i++) {
            temp[i] = getColumn(i);
        }

        components = temp;
    }

    public void multiplyByNumber(double number) {
        for (int i = 0; i < getRowsCount(); i++) {
            components[i].multiply(number);
        }
    }

    public double getDeterminant() {
        if (getColumnsCount() != getRowsCount()) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }

        if (components.length == 1) {
            return components[0].getComponent(0);
        }

        if (getRowsCount() == 2) {
            return components[0].getComponent(0) * components[1].getComponent(1) -
                    components[1].getComponent(0) * components[0].getComponent(1);
        }

        double determinant = 0;
        Matrix subMatrix = new Matrix(getRowsCount() - 1, getColumnsCount() - 1);

        for (int i = 0; i < getColumnsCount(); i++) {
            for (int j = 0; j < subMatrix.getRowsCount(); j++) {
                for (int k = 0; k < subMatrix.getColumnsCount(); k++) {
                    if (k < i) {
                        subMatrix.components[j].setComponent(k, components[j + 1].getComponent(k));
                    } else {
                        subMatrix.components[j].setComponent(k, components[j + 1].getComponent(k + 1));
                    }
                }
            }

            if (i % 2 == 0) {
                determinant += components[0].getComponent(i) * subMatrix.getDeterminant();
            } else {
                determinant -= components[0].getComponent(i) * subMatrix.getDeterminant();
            }
        }

        return determinant;
    }

    public Vector multiplyByVector(Vector vector) {
        if (getColumnsCount() != vector.getSize()) {
            throw new IllegalArgumentException("Число столбцов в матрице должно совпадать длиной вектора");
        }

        Vector result = new Vector(vector.getSize());

        for (int i = 0; i < getRowsCount(); i++) {
            double tempNumber = 0;

            for (int j = 0; j < getColumnsCount(); j++) {
                tempNumber += components[i].getComponent(j) * vector.getComponent(j);
            }

            result.setComponent(i, tempNumber);
        }

        return result;
    }

    public void sum(Matrix matrix) {
        if (getColumnsCount() != matrix.getColumnsCount() || getRowsCount() != matrix.getRowsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера");
        }

        for (int i = 0; i < getRowsCount(); i++) {
            components[i].sum(matrix.getRow(i));
        }
    }

    public void subtract(Matrix matrix) {
        if (getColumnsCount() != matrix.getColumnsCount() || getRowsCount() != matrix.getRowsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера");
        }

        for (int i = 0; i < getRowsCount(); i++) {
            components[i].subtract(matrix.getRow(i));
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getColumnsCount() || matrix1.getRowsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера");
        }

        Matrix result = new Matrix(matrix1);
        result.sum(matrix2);

        return result;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getColumnsCount() || matrix1.getRowsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера");
        }

        Matrix result = new Matrix(matrix1);
        result.subtract(matrix2);

        return result;
    }

    public static Matrix getMultiplication(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Число столбцов 1й матрицы должно быть равно числу строк 2й матрицы");
        }

        int rowsCount = matrix1.getRowsCount();
        int columnsCount = matrix2.getColumnsCount();
        Matrix matrix = new Matrix(rowsCount, columnsCount);

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                double temp = 0;

                for (int k = 0; k < matrix1.getColumnsCount(); k++) {
                    temp += matrix1.components[i].getComponent(k) * matrix2.components[k].getComponent(j);
                }

                matrix.components[i].setComponent(j, temp);
            }
        }

        return matrix;
    }
}