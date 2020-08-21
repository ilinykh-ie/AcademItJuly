package ru.ilinykh.hash_table;

import java.util.*;

public class HashTable<T> implements Collection<T> {
    private final ArrayList<T>[] table;
    private int count;
    private int modCount;

    public HashTable(int length) {
        if (length < 1) {
            throw new ArrayIndexOutOfBoundsException("Длина должна быть больше 0.");
        }

        //noinspection unchecked
        table = (ArrayList<T>[]) new ArrayList[length];
    }

    public HashTable() {
        //noinspection unchecked
        table = (ArrayList<T>[]) new ArrayList[100];
    }

    private int getIndex(Object o) {
        return (o == null) ? 0 : Math.abs(o.hashCode() % table.length);
    }

    private class HashTableIterator implements Iterator<T> {
        private int currentCount;
        private int tableIndex;
        private int listIndex;
        private final int modCount;

        public HashTableIterator() {
            listIndex = -1;
            modCount = HashTable.this.modCount;
        }

        @Override
        public boolean hasNext() {
            return currentCount < count;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Следующего элемента нет в таблице.");
            }

            if (modCount != HashTable.this.modCount) {
                throw new ConcurrentModificationException("Таблица была изменена.");
            }

            currentCount++;
            T temp = null;

            for (int i = tableIndex; i < table.length; i++) {
                if (table[i] == null) {
                    tableIndex++;
                } else {
                    if (listIndex < table[i].size() - 1) {
                        listIndex++;
                    } else {
                        tableIndex++;
                        listIndex = -1;
                        continue;
                    }

                    temp = table[i].get(listIndex);
                    break;
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
        int index = getIndex(o);

        if (table[index] == null) {
            return false;
        }

        return table[index].contains(o);
    }

    @Override
    public boolean add(T t) {
        int index = getIndex(t);

        if (table[index] == null) {
            table[index] = new ArrayList<>();
        }

        table[index].add(t);
        count++;
        modCount++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);

        if (table[index] == null) {
            return false;
        }

        boolean isDeleted = table[index].remove(o);

        if (isDeleted) {
            count--;
            modCount++;
        }

        return isDeleted;
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
    public boolean addAll(Collection<? extends T> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        int modCount = this.modCount;

        for (T t : collection) {
            add(t);
        }

        return modCount != this.modCount;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Переданная коллекция не может быть null.");
        }

        int temp = count;

        for (ArrayList<T> arrayList : table) {
            if (arrayList != null) {
                int initialSize = arrayList.size();

                arrayList.removeAll(collection);

                count -= initialSize - arrayList.size();
            }
        }

        if (temp != count) {
            modCount++;
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
                int initialSize = arrayList.size();

                arrayList.retainAll(collection);

                count -= initialSize - arrayList.size();
            }
        }

        if (temp != count) {
            modCount++;
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
        T1[] temp = (T1[]) toArray();

        if (count <= a.length) {
            System.arraycopy(temp, 0, a, 0, count);

            if (count < a.length) {
                a[count] = null;
            }
        } else {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(temp, count, a.getClass());
        }

        return a;
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        count = 0;
        modCount++;
    }
}