package com.tbf;

import java.util.Iterator;

public class LinkedListIterator<T> implements Iterator<T> {
    Node<T> curr;

    public LinkedListIterator(MyLinkedList<T> list){
        curr = list.getHead();
    }
    @Override
    public boolean hasNext() {
        return curr != null;
    }

    @Override
    public T next() {
        T item = curr.getItem();
        curr = curr.getNext();
        return item;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
