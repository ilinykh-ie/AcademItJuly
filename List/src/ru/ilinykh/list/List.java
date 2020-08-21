package ru.ilinykh.list;

import java.util.NoSuchElementException;

public class List<T> {
    private ListElement<T> head;
    private int count;

    public List(T data) {
        head = new ListElement<>(data);
        count = 1;
    }

    public List() {
    }

    public T getFirstElementData() {
        if (count == 0) {
            throw new NoSuchElementException("Список пустой.");
        }

        return head.getData();
    }

    public int getCount() {
        return count;
    }

    public void insertElementToBeginning(T data) {
        head = new ListElement<>(data, head);

        count++;
    }

    private ListElement<T> getElementByIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона вставки (от 0 до " + (count - 1) + ").");
        }

        int i = 0;
        ListElement<T> temp = null;

        for (ListElement<T> e = head; e != null; e = e.getNext()) {
            if (i == index) {
                temp = e;
                break;
            }

            i++;
        }

        return temp;
    }

    public void insertElement(T data, int index) {
        if (index > count || index < 0) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диспазона вставки (от 0 до " + count + ").");
        }

        if (index == 0) {
            insertElementToBeginning(data);
        } else {
            ListElement<T> element = new ListElement<>(data);
            ListElement<T> previous = getElementByIndex(index - 1);

            element.setNext(previous.getNext());
            previous.setNext(element);

            count++;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");
        ListElement<T> e = head;

        for (int i = 0; e != null; i++) {
            stringBuilder.append(e.getData());

            if (i < count - 1) {
                stringBuilder.append(", ");
            }

            e = e.getNext();
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public T getData(int index) {
        return getElementByIndex(index).getData();
    }

    public T setData(T data, int index) {
        ListElement<T> element = getElementByIndex(index);
        T temp = element.getData();
        element.setData(data);

        return temp;
    }

    public T deleteElement(int index) {
        if (index >= count || index < 0) {
            throw new IndexOutOfBoundsException("Элемента с этим индексом " + index + " в списке нет.");
        }

        if (index == 0) {
            return deleteFirstElement();
        }

        ListElement<T> previous = getElementByIndex(index - 1);
        T temp = previous.getNext().getData();
        previous.setNext(previous.getNext().getNext());
        count--;

        return temp;
    }

    public boolean deleteElement(T data) {
        for (ListElement<T> e = head, prev = null; e != null; prev = e, e = e.getNext()) {
            if (e.getData() == data || (e.getData() != null && e.getData().equals(data))) {
                if (prev != null) {
                    prev.setNext(e.getNext());
                } else {
                    head = head.getNext();
                }

                count--;
                return true;
            }
        }

        return false;
    }

    public T deleteFirstElement() {
        if (head == null) {
            throw new NoSuchElementException("Список пустой");
        }

        T temp = head.getData();
        head = head.getNext();
        count--;

        return temp;
    }

    public void reverse() {
        if (head == null) {
            return;
        }

        for (ListElement<T> e = head, prev = null, next = e.getNext(); ; prev = e, e = next, next = next.getNext()) {
            e.setNext(prev);

            if (next == null) {
                head = e;
                break;
            }
        }
    }

    public List<T> copy() {
        if (head == null) {
            return new List<>();
        }

        List<T> result = new List<>(head.getData());
        result.count = count;

        for (ListElement<T> e = head.getNext(), resultElement, resultPreviousElement = result.head; e != null; e = e.getNext(), resultPreviousElement = resultElement) {
            resultElement = new ListElement<>(e.getData());
            resultPreviousElement.setNext(resultElement);
        }

        return result;
    }
}