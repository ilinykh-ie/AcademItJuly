package ru.ilinykh.list_main;

import ru.ilinykh.list.List;
import ru.ilinykh.list.ListElement;

public class Main {
    public static void main(String[] args) {
        ListElement<Integer> element = new ListElement<>(10);
        List<Integer> integerList = new List<>(element.getData());
        integerList.insertElement(11,1);
        System.out.println(integerList);

        for (int i = 9; i >= 5; i--) {
            integerList.insertElementToBeginning(i);
        }

        System.out.println(integerList);
        System.out.println("Количество элекметов списка = " + integerList.getCount());
        System.out.println("Первый элемент = " + integerList.getFirstElementData());
        System.out.println("Элемент с индексом 3 = " + integerList.getData(3));

        int number = integerList.setData(0, 4);
        System.out.println(integerList + " у измененного элемента с индексм 4 было значение " + number);

        number = integerList.deleteElement(4);
        System.out.println(integerList + " у удаленного элемента с индексм 4 было значение " + number);

        Integer number2 = 13;
        boolean isDeleted = integerList.deleteElement(number2);
        System.out.println(integerList + " удален ли элемент со значением " + number2 + "? - " + isDeleted);

        number = integerList.deleteFirstElement();
        System.out.println(integerList + " у удаленного элемента с индексм 0 было значение " + number);

        integerList.reverse();
        System.out.println("Развернутый список" + integerList);

        List<Integer> list2 = integerList.copy();
        System.out.println("Скопированный список" + list2);
    }
}