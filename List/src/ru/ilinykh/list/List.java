package ru.ilinykh.list;

public class List<T> {
    private ListElement<T> head;
    private int count;

    public List(T data) {
        head = new ListElement<>(data);
        count = 1;
    }

    public T getFirstElementData() {
        return head.getData();
    }

    public int getCount() {
        return count;
    }

    public void insertElementToBeginning(T data) {
        ListElement<T> element = new ListElement<>(data);

        if (head != null) {
            element.setNext(head);
        }
        head = element;
        count++;
    }

    public void insertElement(T data, int index) throws IllegalArgumentException {
        if (index > count || index < 0) {
            throw new IllegalArgumentException("Индекс вне диспазона вставки.");
        }

        if (index == 0) {
            insertElementToBeginning(data);
        } else {
            ListElement<T> element = new ListElement<>(data);
            int i = -1;

            for (ListElement<T> e = head, prev = null; ; prev = e, e = e.getNext()) {
                i++;

                if (i == index) {
                    if (e != null) {
                        element.setNext(e);
                    }

                    if (prev != null) {
                        prev.setNext(element);
                    }

                    break;
                }
            }

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
            throw new IllegalArgumentException("Элемента с этим индексом в списке нет.");
        }

        int i = -1;
        T temp = null;

        for (ListElement<T> e = head; e != null; e = e.getNext()) {
            i++;

            if (i == index) {
                temp = e.getData();
            }
        }

        return temp;
    }

    public T setData(T data, int index) throws IllegalArgumentException {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Элемента с этим индексом в списке нет.");
        }

        int i = -1;
        T temp = null;

        for (ListElement<T> e = head; e != null; e = e.getNext()) {
            i++;

            if (i == index) {
                temp = e.getData();
                e.setData(data);
            }
        }

        return temp;
    }

    public T deleteElement(int index) {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Элемента с этим индексом в списке нет.");
        }

        int i = -1;
        T temp = null;

        for (ListElement<T> e = head, prev = null; e != null; prev = e, e = e.getNext()) {
            i++;

            if (i == index) {
                if (prev != null) {
                    temp = e.getData();
                    prev.setNext(e.getNext());
                } else {
                    head = null;
                }

                count--;

                break;
            }
        }

        return temp;
    }

    public boolean deleteElement(T data) {
        boolean isDeleted = false;

        for (ListElement<T> e = head, prev = null; e != null; prev = e, e = e.getNext()) {
            if (e.getData() == data) {
                if (prev != null) {
                    prev.setNext(e.getNext());
                } else {
                    head = null;
                }

                isDeleted = true;
                count--;

                break;
            }
        }

        return isDeleted;
    }

    public T deleteFirstElement() throws IllegalArgumentException {
        if (head == null) {
            throw new IllegalArgumentException("Список пустой");
        }

        T temp = head.getData();
        head = head.getNext();
        count--;

        return temp;
    }

    public void expandList() {
        if (head == null) {
            throw new IllegalArgumentException("Список пустой");
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
            throw new IllegalArgumentException("Список пустой");
        }

        List<T> result = new List<>(head.getData());

        for (int i = 1; i < count; i++) {
            T temp = getData(i);
            result.insertElement(temp, i);
        }

        return result;
    }
}