package ru.ilinykh.shape;

public class Square implements Shape, Comparable<Shape> {
    private final double sideLength;

    public Square(double sideLength) {
        this.sideLength = sideLength;
    }

    public double getWidth() {
        return sideLength;
    }

    public double getHeight() {
        return sideLength;
    }

    public double getArea() {
        return sideLength * sideLength;
    }

    public double getPerimeter() {
        return sideLength * 4;
    }

    @Override
    public String toString() {
        return String.format("Фигура - квадрат, площадь = %.2f, периметр равен = %.2f, высота = %.2f, ширина равна %.2f.",
                this.getArea(), this.getPerimeter(), this.getHeight(), this.getWidth());
    }

    @Override
    public int compareTo(Shape o) {
        return (int) (this.getArea() - o.getArea());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Square s = (Square) o;
        return sideLength == s.sideLength;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + Double.hashCode(sideLength);
        return hash;
    }
}
