package ru.ilinykh.shapes_main;

import ru.ilinykh.shape.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {new Triangle(0, 0, 0, 3, 3, 0), new Square(34), new Rectangle(8, 4),
                new Circle(5), new Triangle(5, 10, 10, 71, 41, 20), new Square(8), new Rectangle(32, 1),
                new Circle(5), new Rectangle(15, 3), new Circle(15)};

        System.out.println(Arrays.toString(shapes));

        AreaComparator areaComparator = new AreaComparator();
        Arrays.sort(shapes, areaComparator);
        System.out.println("Максимальня площадь: " + shapes[0]);

        PerimeterComparator perimeterComparator = new PerimeterComparator();
        Arrays.sort(shapes, perimeterComparator);
        System.out.println("Второй по величиние периметр: " + shapes[1]);

        Circle circle1 = new Circle(50);
        Circle circle2 = new Circle(50);
        System.out.println(circle1.equals(circle2));
        System.out.println(circle1.hashCode());
        System.out.println(circle2.hashCode());
    }
}

