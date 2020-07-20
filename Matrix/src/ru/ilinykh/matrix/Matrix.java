package ru.ilinykh.matrix;

import ru.ilinykh.vector.Vector;

public class Matrix {
    Vector[] components;

    public Matrix(int rowsCount, int columnCount) {
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
                components[i].setVectorComponent(j, matrix.components[i].getVectorComponent(j));
            }
        }
    }

    public Matrix(double[][] array) {
        components = new Vector[array.length];

        for (int i = 0; i < array.length; i++) {
            components[i] = new Vector(array[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        int m = vectors.length;
        int n = vectors[0].getSize();

        for (int i = 1; i < vectors.length; i++) {
            if (vectors[i].getSize() > n) {
                n = vectors[i].getSize();
            }
        }

        components = new Vector[m];

        for (int i = 0; i < m; i++) {
            components[i] = new Vector(n);

            for (int j = 0; j < vectors[i].getSize(); j++) {
                components[i].setVectorComponent(j, vectors[i].getVectorComponent(j));
            }
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

        for (int i = 0; i < this.getRowsCount(); i++) {
            stringBuilder.append(components[i]);

            if (i != this.getRowsCount() - 1) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("}");

        return stringBuilder.toString().replace("[", "{").replace("]", "}");
    }

    public Vector getRowVector(int index) throws IllegalArgumentException {
        if (index < 0 || index > this.getRowsCount()) {
            throw new IllegalArgumentException("Индекс вне диапазона матрицы");
        }

        return new Vector(components[index]);
    }

    public void setRowVector(int index, Vector vector) throws IllegalArgumentException {
        if (index < 0 || index > this.getRowsCount()) {
            throw new IllegalArgumentException("Индекс вне диапазона матрицы");
        } else if (vector.getSize() > this.getColumnsCount()) {
            throw new IllegalArgumentException("Длина вектора больше длины матрицы");
        }

        for (int i = 0; i < vector.getSize(); i++) {
            double x = vector.getVectorComponent(i);
            components[index].setVectorComponent(i, x);
        }
    }

    public Vector getColumnVector(int index) throws IllegalArgumentException {
        if (index < 0 || index > this.getColumnsCount()) {
            throw new IllegalArgumentException("Индекс вне диапазона матрицы");
        }

        double[] array = new double[this.getRowsCount()];

        for (int i = 0; i < this.getRowsCount(); i++) {
            array[i] = components[i].getVectorComponent(index);
        }

        return new Vector(array);
    }

    public void transpose() {
        Matrix temp = new Matrix(this);
        components = new Vector[temp.getColumnsCount()];

        for (int i = 0; i < temp.getColumnsCount(); i++) {
            components[i] = new Vector(temp.getColumnVector(i));

        }
    }

    public void multiplicationByNumber(double number) {
        Matrix temp = new Matrix(this);

        for (int i = 0; i < this.getRowsCount(); i++) {
            for (int j = 0; j < this.getColumnsCount(); j++) {
                components[i].setVectorComponent(j, temp.components[i].getVectorComponent(j) * number);
            }
        }
    }

    public double getDeterminant() throws IllegalArgumentException {
        if (this.getColumnsCount() != this.getRowsCount()) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }

        if (this.getRowsCount() == 2) {
            return this.components[0].getVectorComponent(0) * this.components[1].getVectorComponent(1) -
                    this.components[1].getVectorComponent(0) * this.components[0].getVectorComponent(1);
        }

        double determinant = 0;
        Matrix subMatrix = new Matrix(this.getRowsCount() - 1, this.getColumnsCount() - 1);

        for (int i = 0; i < this.getColumnsCount(); i++) {
            for (int j = 0; j < subMatrix.getRowsCount(); j++) {
                for (int k = 0; k < subMatrix.getColumnsCount(); k++) {
                    if (k < i) {
                        subMatrix.components[j].setVectorComponent(k, this.components[j + 1].getVectorComponent(k));
                    } else {
                        subMatrix.components[j].setVectorComponent(k, this.components[j + 1].getVectorComponent(k + 1));
                    }
                }
            }

            if (i % 2 == 0) {
                determinant += this.components[0].getVectorComponent(i) * subMatrix.getDeterminant();
            } else {
                determinant -= this.components[0].getVectorComponent(i) * subMatrix.getDeterminant();
            }
        }

        return determinant;
    }

    public void multiplicationByVector(Vector vector) throws IllegalArgumentException {
        if (this.getColumnsCount() != vector.getSize()) {
            throw new IllegalArgumentException("Число столбцов в матрице должно совпадать длиной вектора");
        }

        Matrix temp = new Matrix(this);

        components = new Vector[temp.getRowsCount()];

        for (int i = 0; i < temp.getRowsCount(); i++) {
            components[i] = new Vector(1);

            double tempNumber = 0;

            for (int j = 0; j < vector.getSize(); j++) {
                tempNumber += temp.components[i].getVectorComponent(j) * vector.getVectorComponent(j);
            }

            components[i].setVectorComponent(0, tempNumber);
        }
    }

    public void sum(Matrix matrix) throws IllegalArgumentException {
        if (this.getColumnsCount() != matrix.getColumnsCount() || this.getRowsCount() != matrix.getRowsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера");
        }

        for (int i = 0; i < this.getRowsCount(); i++) {
            for (int j = 0; j < this.getColumnsCount(); j++) {
                components[i].setVectorComponent(j, components[i].getVectorComponent(j) + matrix.components[i].getVectorComponent(j));
            }
        }
    }

    public void difference(Matrix matrix) throws IllegalArgumentException {
        if (this.getColumnsCount() != matrix.getColumnsCount() || this.getRowsCount() != matrix.getRowsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера");
        }

        for (int i = 0; i < this.getRowsCount(); i++) {
            for (int j = 0; j < this.getColumnsCount(); j++) {
                components[i].setVectorComponent(j, components[i].getVectorComponent(j) - matrix.components[i].getVectorComponent(j));
            }
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) throws IllegalArgumentException {
        if (matrix1.getColumnsCount() != matrix2.getColumnsCount() || matrix1.getRowsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера");
        }

        Matrix result = new Matrix(matrix1);
        result.sum(matrix2);

        return result;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) throws IllegalArgumentException {
        if (matrix1.getColumnsCount() != matrix2.getColumnsCount() || matrix1.getRowsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одинакового размера");
        }

        Matrix result = new Matrix(matrix1);
        result.difference(matrix2);

        return result;
    }

    public static Matrix getMultiplication(Matrix matrix1, Matrix matrix2) throws IllegalArgumentException {
        if (matrix1.getColumnsCount() != matrix2.getRowsCount()) {
            throw new IllegalArgumentException("Число столбцов 1й матрицы должно быть равно числу строк 2й матрицы");
        }

        int rowsCount = matrix1.getRowsCount();
        int columnsCount = matrix2.getColumnsCount();
        Matrix matrix = new Matrix(rowsCount, columnsCount);

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                double temp = 0;

                for (int k = 0; k < columnsCount; k++) {
                    temp += matrix1.components[i].getVectorComponent(k) * matrix2.components[k].getVectorComponent(j);
                }

                matrix.components[i].setVectorComponent(j, temp);
            }
        }

        return matrix;
    }
}