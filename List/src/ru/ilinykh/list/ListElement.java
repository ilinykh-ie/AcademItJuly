package ru.ilinykh.list;

public class ListElement<T> {
    private T data;
    private ListElement<T> next;

    public ListElement(T data) {
        this.data = data;
    }

    public ListElement(T data, ListElement<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ListElement<T> getNext() {
        return next;
    }

    public void setNext(ListElement<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return (data == null) ? "null" : data.toString();
    }
}