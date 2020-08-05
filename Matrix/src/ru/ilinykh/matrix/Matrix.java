package ru.ilinykh.matrix;

import ru.ilinykh.vector.Vector;

public class Matrix {
    private Vector[] components;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0 || columnsCount <= 0) {
            throw new IllegalArgumentException("Количество строк (" + rowsCount + ") и столбцов(" + columnsCount + ") должно быть больше 0");
        }

        components = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            components[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        components = new Vector[matrix.getRowsCount()];

        for (int i = 0; i < matrix.getRowsCount(); i++) {
            components[i] = new Vector(matrix.components[i]);
        }
    }

    public Matrix(double[][] array) {
        if (array.length == 0 || array[0].length == 0) {
            throw new IllegalArgumentException("Количество строк (" + array.length + ") и столбцов (" + array[0].length + ") должно быть больше 0");
        }

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
        if (vectors.length == 0 || vectors[0].getSize() == 0) {
            throw new IllegalArgumentException("Количество строк (" + vectors.length + ") и столбцов (" + vectors[0].getSize() + ") должно быть больше 0");
        }

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
        if (index < 0 || index >= getRowsCount()) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона матрицы (" + getRowsCount() + ").");
        }

        return new Vector(components[index]);
    }

    public void setRow(int index, Vector vector) {
        if (index < 0 || index >= getRowsCount()) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона матрицы (" + getRowsCount() + ").");
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Длины задаваемого (" + vector.getSize() + ") и заменяемого (" + getColumnsCount() + ") векторов должны быть равны.");
        }

        components[index] = new Vector(vector);
    }

    public Vector getColumn(int index) {
        if (index < 0 || index >= getColumnsCount()) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона матрицы (" + getRowsCount() + ").");
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
        for (Vector e : components) {
            e.multiply(number);
        }
    }

    public double getDeterminant() {
        if (getColumnsCount() != getRowsCount()) {
            throw new IllegalArgumentException("Матрица должна быть квадратной (число строк (" + getRowsCount() + ") должно быть равно числу столбцов(" + getColumnsCount() + ")).");
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
            throw new IllegalArgumentException("Число столбцов в матрице (" + getColumnsCount() + ") должно совпадать с длиной вектора (" + vector.getSize() + ")");
        }

        Vector result = new Vector(vector.getSize());

        for (int i = 0; i < getRowsCount(); i++) {
            result.setComponent(i, Vector.getScalarProduct(components[i], vector));
        }

        return result;
    }

    public void sum(Matrix matrix) {
        if (getColumnsCount() != matrix.getColumnsCount() || getRowsCount() != matrix.getRowsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера");
        }

        for (int i = 0; i < getRowsCount(); i++) {
            components[i].sum(matrix.components[i]);
        }
    }

    public void subtract(Matrix matrix) {
        if (getColumnsCount() != matrix.getColumnsCount() || getRowsCount() != matrix.getRowsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера");
        }

        for (int i = 0; i < getRowsCount(); i++) {
            components[i].subtract(matrix.components[i]);
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
            throw new IllegalArgumentException("Число столбцов 1й матрицы (" + matrix1.getColumnsCount() + ") должно быть равно числу строк 2й матрицы (" + matrix2.getColumnsCount() + ")");
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