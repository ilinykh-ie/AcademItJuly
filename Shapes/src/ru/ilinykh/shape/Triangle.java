package ru.ilinykh.shape;

public class Triangle implements Shape, Comparable<Shape> {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private final int x3;
    private final int y3;

    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public double getWidth() {
        return Math.max(x1, Math.max(x2, x3)) - Math.min(x1, Math.min(x2, x3));
    }

    public double getHeight() {
        return Math.max(y1, Math.max(y2, y3)) - Math.min(y1, Math.min(y2, y3));
    }

    public double getArea() {
        double sideA = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        double sideB = Math.sqrt(Math.pow((x1 - x3), 2) + Math.pow((y1 - y3), 2));
        double sideC = Math.sqrt(Math.pow((x2 - x3), 2) + Math.pow((y2 - y3), 2));

        double halfPerimeter = (sideA + sideB + sideC) / 2;

        return Math.sqrt(halfPerimeter * (halfPerimeter - sideA) * (halfPerimeter - sideB) * (halfPerimeter - sideC));
    }

    public double getPerimeter() {
        double sideA = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        double sideB = Math.sqrt(Math.pow((x1 - x3), 2) + Math.pow((y1 - y3), 2));
        double sideC = Math.sqrt(Math.pow((x2 - x3), 2) + Math.pow((y2 - y3), 2));

        return sideA + sideB + sideC;
    }

    @Override
    public String toString() {
        return String.format("Фигура - треугольник, площадь = %.2f, периметр равен = %.2f, высота = %.2f, ширина равна %.2f.",
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

        Triangle t = (Triangle) o;
        return x1 == t.x1 && x2 == t.x2 && x3 == t.x3 && y1 == t.y1 && y2 == t.y2 && y3 == t.y3;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = prime * hash + x1;
        hash = prime * hash + y1;
        hash = prime * hash + x2;
        hash = prime * hash + y2;
        hash = prime * hash + x3;
        hash = prime * hash + y3;
        return hash;
    }
}
