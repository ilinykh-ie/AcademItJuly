package ru.ilinykh.shape;

public class Rectangle implements Shape, Comparable<Shape> {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return (width + height) * 2;
    }

    @Override
    public String toString() {
        return String.format("Фигура - прямоугольник, площадь = %.2f, периметр равен = %.2f, высота = %.2f, ширина равна %.2f.",
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

        Rectangle r = (Rectangle) o;
        return width == r.width && height == r.height;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + Double.hashCode(width);
        hash = prime * hash + Double.hashCode(height);
        return hash;
    }
}
