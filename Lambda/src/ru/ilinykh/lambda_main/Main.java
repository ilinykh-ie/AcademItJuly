package ru.ilinykh.lambda_main;

import ru.ilinykh.person.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        ArrayList<Person> personsList = new ArrayList<>();

        personsList.add(new Person("Ivan", 30));
        personsList.add(new Person("Vladimir", 12));
        personsList.add(new Person("Tatiana", 17));
        personsList.add(new Person("Olga", 14));
        personsList.add(new Person("Evgeniy", 45));
        personsList.add(new Person("Ekaterina", 22));
        personsList.add(new Person("Petr", 13));
        personsList.add(new Person("Petr", 25));
        personsList.add(new Person("Nickolay", 8));

        String distinctNames = personsList.stream()
                .map(Person::getName)
                .distinct()
                .collect(Collectors.joining(", ", "Имена: ", "."));

        System.out.println(distinctNames);

        double averageAge = personsList.stream()
                .mapToInt(Person::getAge)
                .filter(x -> x < 18)
                .average()
                .orElse(-1);

        System.out.println("Средний возраст людей, младше 18 составляет: " + averageAge);

        Map<String, Double> averageAgeByName = personsList.stream()
                .collect(Collectors.groupingBy(Person::getName,
                        Collectors.averagingDouble(Person::getAge)));

        System.out.println(averageAgeByName);

        String personsFrom20to45 = personsList.stream()
                .filter(x -> x.getAge() >= 20 && x.getAge() <= 45)
                .sorted((o1, o2) -> Integer.compare(o2.getAge(), o1.getAge()))
                .map(Person::getName)
                .collect(Collectors.joining(", "));

        System.out.println(personsFrom20to45);

        DoubleStream sqrt = DoubleStream.iterate(0, x -> x + 1).map(Math::sqrt);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите количество элементов: ");
        int elementsCount = scanner.nextInt();

        sqrt.limit(elementsCount).forEach(System.out::println);

        Stream.iterate(new int[]{0, 1}, x -> new int[]{x[1], x[0] + x[1]})
                .map(t -> t[0])
                .limit(30)
                .forEach(e -> System.out.print(e + " "));
    }
}