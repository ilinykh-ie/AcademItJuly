package ru.ilinykh.shape;

import java.util.Comparator;

public interface Shape {
    double getWidth();

    double getHeight();

    double getArea();

    double getPerimeter();

    Comparator<Shape> perimeterComparator = (o1, o2) -> (int) (o2.getPerimeter() - o1.getPerimeter());

    Comparator<Shape> areaComparator = (o1, o2) -> (int) (o2.getArea() - o1.getArea());
}
