package ru.ilinykh.array_list;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private T[] items;
    private int length;
    private int modCount;

    public MyArrayList(T[] array) {
        if (array == null) {
            throw new NullPointerException("Переданный массив не должен быть null.");
        }

        items = array.clone();
        length = array.length;
    }

    public MyArrayList(int capacity) {
        if (capacity < 0) {
            throw new NegativeArraySizeException("Вместимость должна быть не меньше 0.");
        }

        //noinspection unchecked
        items = (T[]) new Object[capacity];
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
            return items[currentIndex];
        }
    }

    private void increaseCapacity() {
        if (items.length == 0) {
            items = Arrays.copyOf(items, 10);
        } else {
            items = Arrays.copyOf(items, items.length * 2);
        }
    }

    public void ensureCapacity(int capacity) {
        if (items.length < capacity) {
            items = Arrays.copyOf(items, capacity);
        }
    }

    public void trimToSize() {
        if (length < items.length) {
            items = Arrays.copyOf(items, length);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (int i = 0; i < length; i++) {
            stringBuilder.append(items[i]);

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
        return indexOf(o) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        for (Object e : collection) {
            if (!contains(e)) {
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
        return Arrays.copyOf(items, length);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (length > a.length) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(items, length, a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(items, 0, a, 0, length);

        if (length < a.length) {
            a[length] = null;
        }

        return a;
    }

    @Override
    public boolean add(T object) {
        if (length >= items.length) {
            increaseCapacity();
        }

        items[length] = object;
        length++;
        modCount++;

        return true;
    }

    @Override
    public void add(int index, T object) {
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона списка (" + length + ").");
        }

        if (length >= items.length) {
            increaseCapacity();
        }

        if (index == length) {
            add(object);
        } else {
            System.arraycopy(items, index, items, index + 1, length - index);
            items[index] = object;
            length++;
            modCount++;
        }
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index != -1) {
            remove(index);
            return true;
        }

        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return addAll(0, collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        if (index > length || index < 0) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона списка (" + length + ").");
        }

        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        if (collection.size() == 0) {
            return false;
        }

        ensureCapacity(length + collection.size());

        System.arraycopy(items, index, items, index + collection.size(), length - index);

        int i = index;

        for (T t : collection) {
            items[i] = t;
            i++;
        }

        length += collection.size();
        modCount++;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        int temp = modCount;

        for (int i = 0; i < length; i++) {
            if (collection.contains(items[i])) {
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
            if (!collection.contains(items[i])) {
                remove(i);
                i--;
            }
        }

        return temp != modCount;
    }

    @Override
    public void clear() {
        Arrays.fill(items, null);

        modCount++;
        length = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона списка (" + (length - 1) + ").");
        }

        return items[index];
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона списка (" + (length - 1) + ").");
        }

        T temp = items[index];
        items[index] = element;

        return temp;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Индекс " + index + " вне диапазона списка (" + (length - 1) + ").");
        }

        T temp = items[index];

        System.arraycopy(items, index + 1, items, index, length - 1 - index);
        length--;
        modCount++;

        return temp;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < length; i++) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = length - 1; i >= 0; i--) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
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