package ru.ilinykh.shape;

import java.util.Comparator;

public class PerimeterComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape o1, Shape o2) {
        return Double.compare(o2.getPerimeter(), o1.getPerimeter());
    }
}