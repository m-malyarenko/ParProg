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
     * @return {@code true}, если все элементы колссекции {@code c} находятся в
     *         коллекции; {@code false} в обратном случае
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
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

            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
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

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <V> V[] toArray(V[] a) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Класс итератора <code>MyLinkedHashSet</code>
     */
    public class MySetIterator implements Iterator<T> {

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public T next() {
            // TODO Auto-generated method stub
            return null;
        }

    } // MySetIterator
    
} // MyLinkedHashSet
