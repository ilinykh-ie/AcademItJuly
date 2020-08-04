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
        count = 0;
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
        ListElement<T> element = new ListElement<>(data);
        element.setNext(head);
        head = element;

        count++;
    }

    private ListElement<T> getElementByIndex(int index) {
        if (index > count || index < 0) {
            throw new IndexOutOfBoundsException("Индекс вне диспазона вставки.");
        }

        int i = 0;
        ListElement<T> temp = new ListElement<>();

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
            throw new IndexOutOfBoundsException("Индекс вне диспазона вставки.");
        }

        if (index == 0) {
            insertElementToBeginning(data);
        } else {
            ListElement<T> element = new ListElement<>(data);
            ListElement<T> previous = getElementByIndex(index - 1);

            if (index < count) {
                element.setNext(previous.getNext());
            }

            previous.setNext(element);

            count++;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;

        stringBuilder.append("{");

        for (ListElement<T> e = head; e != null; e = e.getNext()) {
            stringBuilder.append(e.getData());
            i++;

            if (i != count) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public T getData(int index) {
        if (index >= count || index < 0) {
            throw new IndexOutOfBoundsException("Элемента с этим индексом в списке нет.");
        }

        return getElementByIndex(index).getData();
    }

    public T setData(T data, int index) {
        if (index >= count || index < 0) {
            throw new IndexOutOfBoundsException("Элемента с этим индексом в списке нет.");
        }

        ListElement<T> element = getElementByIndex(index);
        T temp = element.getData();
        element.setData(data);

        return temp;
    }

    public T deleteElement(int index) {
        if (index >= count || index < 0) {
            throw new IndexOutOfBoundsException("Элемента с этим индексом в списке нет.");
        }

        T temp;

        if (index >= 1) {
            ListElement<T> previous = getElementByIndex(index - 1);
            temp = previous.getNext().getData();
            previous.setNext(previous.getNext().getNext());
            count--;
        } else {
            temp = deleteFirstElement();
        }

        return temp;
    }

    public boolean deleteElement(T data) {
        boolean isDeleted = false;

        for (ListElement<T> e = head, prev = null; e != null; prev = e, e = e.getNext()) {
            if (e.getData().equals(data)) {
                if (prev != null) {
                    prev.setNext(e.getNext());
                } else {
                    head = head.getNext();
                }

                isDeleted = true;
                count--;

                break;
            }
        }

        return isDeleted;
    }

    public T deleteFirstElement() {
        if (head == null) {
            throw new IllegalArgumentException("Список пустой");
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

        List<T> result = new List<>();

        for (ListElement<T> e = head, resultElement, resultPreviousElement = null; e != null; e = e.getNext(), resultPreviousElement = resultElement) {
            resultElement = new ListElement<>(e.getData());

            if (resultPreviousElement != null)
                resultPreviousElement.setNext(resultElement);

            if (e == head) {
                result.head = resultElement;
            }

            result.count++;
        }

        return result;
    }
}