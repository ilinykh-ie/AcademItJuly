package ru.ilinykh.hash_table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class HashTable<T> implements Collection<T> {
    private final ArrayList<T>[] table;
    private int count;

    public HashTable(int length) {
        //noinspection unchecked
        table = (ArrayList<T>[]) new ArrayList[length];
    }

    public HashTable() {
        //noinspection unchecked
        table = (ArrayList<T>[]) new ArrayList[100];
    }

    private class HashTableIterator implements Iterator<T> {
        private int currentCount;
        private int hashCode;
        private int listIndex;

        public HashTableIterator() {
            hashCode = 0;
            listIndex = -1;
        }

        @Override
        public boolean hasNext() {
            return currentCount < count;
        }

        @Override
        public T next() {
            currentCount++;
            T temp = null;

            for (int i = hashCode; i < table.length; i++) {
                if (table[i] != null) {
                    if (listIndex < table[i].size() - 1) {
                        listIndex++;
                    } else {
                        hashCode++;
                        listIndex = -1;
                        continue;
                    }

                    temp = table[i].get(listIndex);
                    break;
                } else {
                    hashCode++;
                }
            }

            return temp;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{");

        int i = 0;

        for (ArrayList<T> arrayList : table) {
            if (arrayList != null) {
                for (T t : arrayList) {
                    stringBuilder.append(t);
                    i++;

                    if (i != count) {
                        stringBuilder.append(", ");
                    }
                }
            }
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException("Проверяемый элемент не может быть null.");
        }

        int index = Math.abs(o.hashCode() % table.length);

        if (table[index] == null) {
            return false;
        }

        return table[index].contains(o);
    }

    @Override
    public boolean add(T t) {
        if (t == null) {
            throw new NullPointerException("Элемент для вставки не может быть null.");
        }

        int index = Math.abs(t.hashCode() % table.length);

        if (table[index] == null) {
            table[index] = new ArrayList<>();
        }

        table[index].add(t);
        count++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException("Элемент для удаления не может быть null.");
        }

        int index = Math.abs(o.hashCode() % table.length);

        if (table[index] == null) {
            return false;
        }

        for (T t : table[index]) {
            if (t.equals(o)) {
                count--;
                break;
            }
        }

        return table[index].remove(o);
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
    public boolean addAll(Collection<? extends T> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        for (T t : collection) {
            add(t);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        int temp = count;

        for (ArrayList<T> arrayList : table) {
            if (arrayList != null) {
                for (int j = 0; j < arrayList.size(); j++) {
                    if (collection.contains(arrayList.get(j))) {
                        remove(arrayList.get(j));
                        j--;
                    }
                }
            }
        }

        return temp != count;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        int temp = count;

        for (ArrayList<T> arrayList : table) {
            if (arrayList != null) {
                for (int j = 0; j < arrayList.size(); j++) {
                    if (!collection.contains(arrayList.get(j))) {
                        remove(arrayList.get(j));
                        j--;
                    }
                }
            }
        }

        return temp != count;
    }

    @Override
    public Iterator<T> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[count];

        int i = 0;

        for (T t : this) {
            result[i] = t;
            i++;
        }

        return result;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        //noinspection unchecked
        T1[] temp = (T1[]) new Object[count];

        int i = 0;

        for (T t : this) {
            //noinspection unchecked
            temp[i] = (T1) t;
            i++;
        }

        if (count <= a.length) {
            System.arraycopy(temp, 0, a, 0, count);
        } else {
            //noinspection unchecked
            a = (T1[]) Arrays.copyOf(temp, count, a.getClass());
        }

        return a;
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        count = 0;
    }
}