package ru.spbstu.telematics.malyarenko.lab_2;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class MyLinkedHashSet<T> implements Set<T> {

    /** Хеш-таблица */
    private Vector<MySetNode<T>> _hashTable;

    /** Размер хеш-таблицы */
    private final int HASH_TABLE_SIZE = 30;

    /** Фиктивный первый узел */
    private MySetNode<T> _root;

    /** Ссылка на последный добавленный элемент */
    private MySetNode<T> _last;

    /** Размер коллекции */
    private int _size;

    /**
     * Класс узла <code>MyLinkedHashSet</code>
     */
    private class MySetNode<V> {
        /** Ссылка на данные типа <T> */
        V _data;

        /** Ссылка на узел, добавленный после <code>this</code> */
        MySetNode<V> _next;

        /** Ссылка на узел, добавленный перед <code>this</code> */
        MySetNode<V> _prev;

        /**
         * Ссылка на узел, идущий после <code>this</code> в хеш-таблице
         * @see _hashTable
         */
        MySetNode<V> _nextInHashTable;

        public MySetNode(V data) {
            _data = data;
            _next = null;
            _prev = null;
            _nextInHashTable = null;
        }
    } // MySetNode

    public MyLinkedHashSet(T value) {
        _size = 0;
        _hashTable = new Vector<MySetNode<T>>(HASH_TABLE_SIZE);
        _root = new MySetNode<T>(null);
        _last = _root;
    }

    /**
     * Добавление нового элемента в коллекцию
     * 
     * @param data - данные
     * @return {@code true}, если новые данные добавлены; {@code false}, если данные
     *         не были добавлены
     */
    @Override
    public boolean add(T data) {
        if (!contains(data)) {
            int index = data.hashCode() % HASH_TABLE_SIZE;
            MySetNode<T> node = _hashTable.get(index);

            if (node == null) {
                node = new MySetNode<T>(data);
            } else {
                while (node._nextInHashTable != null) {
                    node = node._nextInHashTable;
                }

                node._nextInHashTable = new MySetNode<T>(data);
            }

            _last._next = node;
            node._prev = _last;
            _last = node;
            _size++;

            return true;
        } else {
            return false;
        }
    }

    /**
     * Добавление всех элементов коллекции
     * 
     * @param c - коллекция, элементы которой должны быть добавлены
     * @return {@code true}, если хотя бы отдин элемент был добавлен; {@code false}
     *         в обратном случае
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        int sizeOld = _size;

        for (T t : c) {
            add(t);
        }

        if (sizeOld == _size) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Удаление всех элементов коллекции
    */
    @Override
    public void clear() {
        MySetNode<T> node = _root;

        while (node._next != null) {
            node = node._next;
            remove(node);
        }
    }

    /**
     * Проверка на наличие элемента в коллекции
     * 
     * @param o - проверяемый элемент
     * @return {@code true}, если элемент находится в коллекции; {@code false} в
     *         обратном случае
     */
    @Override
    public boolean contains(Object o) {
        int index = o.hashCode() % HASH_TABLE_SIZE;
        MySetNode<T> node = _hashTable.get(index);

        if (node == null) {
            return false;
        } else {
            while (node != null) {
                if (o.equals(node._data)) {
                    return true;
                } else {
                    node = node._nextInHashTable;
                }
            }

            return false;
        }
    }

    /**
     * Проверка на наличие всех элементов другой колеекции в коллекции
     * 
     * @param c - коллекция
     * @return {@code true}, если все элементы коллекции {@code c} находятся в
     *         коллекции; {@code false} в обратном случае
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        int count = 0;

        for (Object o : c) {
            if(contains(o)) {
                count++;
            }
        }

        return (count == c.size()) ? true : false;
    }

    /** Проверка на пустую коллекцию
     * 
     * @return {@code true}, если коллекция пуста; {@code false} в обратном случае
     */
    @Override
    public boolean isEmpty() {
        return (_size == 0) ? true : false;
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    /** 
     * Удаление элемента из коллекции в случае, если он там присутствовал
     * 
     * @param o - элемент подлежащий удалению
     * @return {@code true}, если элемент был удалён из коллекции; {@code false} в обратном случае
     */
    @Override
    public boolean remove(Object o) {
        if(contains(o)) {
            int index = o.hashCode() % HASH_TABLE_SIZE;
            MySetNode<T> node = _hashTable.get(index);
            MySetNode<T> nodePrevInHashTable = null;

            while (!o.equals(node._data)) {
                nodePrevInHashTable = node;
                node = node._nextInHashTable;
            }

            if (node._prev != null) {
                node._prev._next = node._next;
            }  
            if (node._next != null) {
                node._next._prev = node._prev;
            }

            if (_hashTable.get(index) == node) {
                _hashTable.set(index, node._nextInHashTable);
            }
            else {
                nodePrevInHashTable._nextInHashTable = (node == null) ? null : node._nextInHashTable;
            }
            _size--;

            return true;
        }
        else {
            return false;
        }
    }

    /** 
     * Удаление всех вхождений элементов другой коллекции, которые присутствуют в коллекции
     * 
     * @param o - коллекция
     * @return {@code true}, если  хотя бы один элемент был удалён из коллекции; {@code false} в обратном случае
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        int sizeOld = _size;

        for (Object o : c) {
            remove(o);
        }

        return (sizeOld > _size) ? true : false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int size() {
        return _size;
    }

    /** Преобразование в массив
     * @return массив {@code Object[]} из элементов коллекции в порядке их добавления 
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[_size];
        
        int count = 0;
        for (Object t : this) {
            array[count] = t;
            count++;
        }

        return array;
    }

    /**
     * Запись элементов коллекции в массив
     * 
     * @param U - параметр типа
     * @return массив {@code U[]} из элементов коллекции в порядке их добавления 
     */
    @Override
    public <U> U[] toArray(U[] a) {
        U[] array;

        if (a.length >= _size) {
            array = a;
        }
        else {
            array = (U[]) new Object[_size];
            int count = 0;
            for (Object o : this) {
                array[count] = (U) o;
                count++;
            }
        }
        return array;
    }

    /**
     * Класс итератора <code>MyLinkedHashSet</code>
     */
    private class MySetIterator implements Iterator<T> {
        /** Текущий узел */
        private MySetNode<T> _node;

        public MySetIterator() {
            _node = _root;
        }

        @Override
        public boolean hasNext() {
            return (_node._next != null) ? true : false;
        }

        @Override
        public T next() {
            T data = _node._data;

            if (hasNext()) {
                _node = _node._next;
            }

            return data;
        }

    } // MySetIterator
    
} // MyLinkedHashSet
