package ru.ilinykh.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int length) throws IllegalArgumentException {
        if (length <= 0) {
            throw new IllegalArgumentException("Длина вектора должна быть больше 0");
        }

        components = new double[length];
    }

    public Vector(Vector vector) {
        components = Arrays.copyOf(vector.components, vector.getSize());
    }

    public Vector(double[] array) {
        if (array.length <= 0) {
            throw new IllegalArgumentException("Длина вектора должна быть больше 0");
        }
        components = Arrays.copyOf(array, array.length);
    }

    public Vector(int length, double[] array) throws IllegalArgumentException {
        if (length <= 0) {
            throw new IllegalArgumentException("Длина вектора должна быть больше 0");
        } else if (length < array.length) {
            throw new IllegalArgumentException("Длина вектора должна быть не меньше длины массива");
        }

        components = Arrays.copyOf(array, length);
    }

    public int getSize() {
        return components.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(components).replace("[", "{").replace("]", "}");
    }

    public void sum(Vector vector) {
        if (vector.getSize() > this.getSize()) {
            Vector vector1 = new Vector(Math.max(this.getSize(), vector.getSize()), components);

            for (int i = 0; i < vector.getSize(); i++) {
                vector1.components[i] += vector.components[i];
            }

            components = Arrays.copyOf(vector1.components, vector1.getSize());
        } else {
            for (int i = 0; i < vector.getSize(); i++) {
                components[i] += vector.components[i];
            }
        }
    }

    public void difference(Vector vector) {
        if (vector.getSize() > this.getSize()) {
            Vector vector1 = new Vector(Math.max(this.getSize(), vector.getSize()), components);

            for (int i = 0; i < vector.getSize(); i++) {
                vector1.components[i] -= vector.components[i];
            }

            components = Arrays.copyOf(vector1.components, vector1.getSize());
        } else {
            for (int i = 0; i < vector.getSize(); i++) {
                components[i] -= vector.components[i];
            }
        }
    }

    public void multiplication(double number) {
        for (int i = 0; i < this.getSize(); i++) {
            components[i] *= number;
        }
    }

    public void deployVector() {
        for (int i = 0; i < this.getSize(); i++) {
            components[i] *= -1;
        }
    }

    public double getLength() {
        double length = 0;

        for (double e : components) {
            length += Math.pow(e, 2);
        }

        return Math.sqrt(length);
    }

    public double getVectorComponent(int index) throws IllegalArgumentException {
        if (index < 0 || index >= this.getSize()) {
            throw new IllegalArgumentException("Индекс вне диапазона вектора");
        }

        return components[index];
    }

    public void setVectorComponent(int index, double number) throws IllegalArgumentException {
        if (index < 0 || index >= this.getSize()) {
            throw new IllegalArgumentException("Индекс вне диапазона вектора");
        }

        components[index] = number;
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
        return Arrays.equals(components, v.components);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + Arrays.hashCode(components);
        return hash;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector vector = new Vector(vector1);
        vector.sum(vector2);

        return vector;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector vector = new Vector(vector1);
        vector.difference(vector2);

        return vector;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0;
        int minSize = Math.min(vector1.getSize(), vector2.getSize());

        for (int i = 0; i < minSize; i++) {
            scalarProduct += vector1.components[i] * vector2.components[i];
        }

        return scalarProduct;
    }
}