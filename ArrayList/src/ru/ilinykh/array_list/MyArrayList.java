package ru.ilinykh.array_list;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private T[] array;
    private int length;
    private int modCount;

    public MyArrayList(T[] array) {
        this.array = array;
        length = array.length;
        modCount = 0;
    }

    public MyArrayList(int capacity) {
        //noinspection unchecked
        array = (T[]) new Object[capacity];
        length = 0;
        modCount = 0;
    }

    private class MyArrayListIterator implements Iterator<T> {
        private int currentIndex;
        private final int modCount;

        public MyArrayListIterator() {
            currentIndex = -1;
            modCount = MyArrayList.this.modCount;
        }

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Следующего элемента нет в списке.");
            }

            if (modCount != MyArrayList.this.modCount) {
                throw new ConcurrentModificationException("Список был изменен.");
            }

            currentIndex++;
            return array[currentIndex];
        }
    }

    private void increaseCapacity() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    public void ensureCapacity(int capacity) {
        if (array.length < capacity) {
            array = Arrays.copyOf(array, capacity);
        }
    }

    public void trimToSize() {
        array = Arrays.copyOf(array, length);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (int i = 0; i < length; i++) {
            stringBuilder.append(array[i]);

            if (i != length - 1) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException("Переданный объект не может быть null.");
        }

        for (T e : array) {
            if (o.equals(e)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        for (Object e : collection) {
            if (e != null && !contains(e)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, length);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        //noinspection unchecked
        a = (T1[]) toArray();

        return a;
    }

    @Override
    public boolean add(T object) {
        if (length >= array.length) {
            increaseCapacity();
        }

        array[length] = object;
        length++;
        modCount++;

        return true;
    }

    @Override
    public void add(int index, T object) {
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона списка (" + length + ").");
        }

        if (length >= array.length) {
            increaseCapacity();
        }

        if (index == length) {
            add(object);
        } else {
            System.arraycopy(array, index, array, index + 1, length - index);
            array[index] = object;
            length++;
            modCount++;
        }
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < length; i++) {
            if (array[i].equals(o)) {
                System.arraycopy(array, i + 1, array, i, length - 1 - i);
                length--;
                modCount++;
                array[length] = null;

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        int collectionSize = collection.size();

        if (length + collectionSize > array.length) {
            ensureCapacity(length + collectionSize);
        }

        for (T t : collection) {
            add(t);
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона списка (" + length + ").");
        }

        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        int collectionSize = collection.size();
        length += collectionSize;

        if (length > array.length) {
            ensureCapacity(length);
        }

        System.arraycopy(array, index, array, index + collectionSize, length - index - collectionSize);

        int temp = index;

        for (T t : collection) {
            set(temp, t);
            temp++;

            modCount++;
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        int temp = modCount;

        for (int i = 0; i < length; i++) {
            if (collection.contains(array[i])) {
                remove(i);
                i--;
            }
        }

        return temp != modCount;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        int temp = modCount;

        for (int i = 0; i < length; i++) {
            if (!collection.contains(array[i])) {
                remove(i);
                i--;
            }
        }

        return temp != modCount;
    }

    @Override
    public void clear() {
        for (int i = 0; i < length; i++) {
            array[i] = null;
        }

        length = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона списка (" + length + ").");
        }

        return array[index];
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона списка (" + length + ").");
        }

        T temp = array[index];
        array[index] = element;

        return temp;
    }


    @Override
    public T remove(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона списка (" + length + ").");
        }

        T temp = array[index];

        System.arraycopy(array, index + 1, array, index, length - 1 - index);
        length--;
        modCount++;

        return temp;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < length; i++) {
            if (array[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;

        for (int i = 0; i < length; i++) {
            if (array[i].equals(o)) {
                index = i;
            }
        }

        return index;
    }

    @Override
    public ListIterator<T> listIterator() {
        //noinspection ConstantConditions
        return null;
    }


    @Override
    public ListIterator<T> listIterator(int index) {
        //noinspection ConstantConditions
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        //noinspection ConstantConditions
        return null;
    }
}
