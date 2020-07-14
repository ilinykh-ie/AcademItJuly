package ru.ilinykh.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;
    private int n;

    public Vector(int n) throws IllegalAccessException {
        if (n <= 0) {
            throw new IllegalAccessException("Размерность вектора n должна быть больше 0");
        }

        this.components = new double[n];
        this.n = n;

        for (int i = 0; i < n; i++) {
            components[i] = 0;
        }
    }

    public Vector(Vector vector) {
        this.components = new double[vector.n];
        this.n = vector.n;

        System.arraycopy(vector.components, 0, this.components, 0, vector.n);
    }

    public Vector(double[] array) {
        this.components = new double[array.length];
        this.n = array.length;

        System.arraycopy(array, 0, this.components, 0, n);
    }

    public Vector(int n, double[] array) throws IllegalAccessException {
        if (n <= 0) {
            throw new IllegalAccessException("Размерность вектора n должна быть больше 0");
        }

        this.components = new double[n];
        this.n = n;

        System.arraycopy(array, 0, this.components, 0, array.length);

        for (int i = array.length; i < n; i++) {
            components[i] = 0;
        }
    }

    public int getSize() {
        return n;
    }

    @Override
    public String toString() {
        return Arrays.toString(components);
    }

    public void getSum(Vector vector) throws IllegalAccessException {
        Vector vector1 = new Vector(Math.max(this.n, vector.n), this.components);

        for (int i = 0; i < vector.n; i++) {
            vector1.components[i] += vector.components[i];
        }

        this.n = vector1.n;
        System.arraycopy(vector1.components, 0, this.components, 0, vector1.n);
    }

    public void getDiff(Vector vector) throws IllegalAccessException {
        Vector vector1 = new Vector(Math.max(this.n, vector.n), this.components);

        for (int i = 0; i < vector.n; i++) {
            vector1.components[i] -= vector.components[i];
        }

        this.n = vector1.n;
        System.arraycopy(vector1.components, 0, this.components, 0, vector1.n);
    }

    public void getMultiplication(double number) {
        for (int i = 0; i < n; i++) {
            components[i] *= number;
        }
    }

    public void expandVector() {
        for (int i = 0; i < n; i++) {
            components[i] *= -1;
        }
    }

    public double getLength() {
        double length = 0;

        for (int i = 0; i < this.n; i++) {
            length += Math.pow(this.components[i], 2);
        }

        return Math.sqrt(length);
    }

    public double getVectorComponent(int index) throws IllegalAccessException {
        if (index <= 0 || index > n) {
            throw new IllegalAccessException("Индекс вне диапазона вектора");
        }

        return this.components[index - 1];
    }

    public void setVectorComponent(int index, double number) throws IllegalAccessException {
        if (index <= 0 || index > n) {
            throw new IllegalAccessException("Индекс вне диапазона вектора");
        }

        this.components[index - 1] = number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Vector v = (Vector) o;
        return n == v.n && Arrays.equals(components, v.components);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + Arrays.hashCode(components);
        hash = prime * hash + n;
        return hash;
    }

    public static Vector sum(Vector vector1, Vector vector2) throws IllegalAccessException {
        Vector vector = new Vector(Math.max(vector1.n, vector2.n), vector1.components);

        for (int i = 0; i < vector2.n; i++) {
            vector.components[i] += vector2.components[i];
        }

        return vector;
    }

    public static Vector diff(Vector vector1, Vector vector2) throws IllegalAccessException {
        Vector vector = new Vector(Math.max(vector1.n, vector2.n), vector1.components);

        for (int i = 0; i < vector2.n; i++) {
            vector.components[i] -= vector2.components[i];
        }

        return vector;
    }

    public static double scalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0;

        for (int i = 0; i < (Math.min(vector1.n, vector2.n)); i++) {
            scalarProduct += vector1.components[i] * vector2.components[i];
        }

        return scalarProduct;
    }
}