package com.tbf;

public class Node<T> {
    private T item;
    private Node<T> next;
    private Node<T> previous;

    public Node(T item) {
        this.item = item;
        this.next = null;
        this.previous = null;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    public boolean hasNext() {
        return this.next != null;
    }
}
