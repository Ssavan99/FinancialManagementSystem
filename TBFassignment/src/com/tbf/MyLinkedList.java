package com.tbf;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class MyLinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private int size;
    private Comparator<T> comparator;

    public MyLinkedList(Comparator<T> comparator) {
        this.head = null;
        this.size = 0;
        this.comparator = comparator;
    }

    public void add(T item){
        if (this.head == null || comparator.compare(this.head.getItem(), item) >= 0){
            insertAtHead(item);
            return;
        } else {
            Node<T> curr = this.head;
            int i = 0;
            while (i < getSize()){
                if (this.comparator.compare(curr.getItem(), item) >= 0){
                    insertAtIndex(item, i);
                    return;
                }
                if(curr.hasNext()){
                    curr = curr.getNext();
                }
                i++;
            }
        }
        insertAtTail(item);
        return;
    }

    public Node<T> getHead() {
        return head;
    }

    public T getElementAtIndex(int index) {
        return getNodeAtIndex(index).getItem();
    }

    private Node<T> getNodeAtIndex(int index) {
        if(index < 0 || index >= this.size) {
            throw new IllegalArgumentException("index out of bounds!");
        }

        Node<T> curr = this.head;
        for(int i = 0; i < index; i++){
            curr = curr.getNext();
        }
        return curr;
    }

    public T removeFromIndex(int index) {
        if(index == 0) {
            T item = this.head.getItem();
            this.removeFromHead();
            return item;
        }
        Node<T> prev = getNodeAtIndex(index - 1);
        Node<T> curr = prev.getNext();

        prev.setNext(curr.getNext());
        this.size--;
        return curr.getItem();
    }

    public void insertAtIndex(T item, int index) {
        if(index < 0 || index > this.size) {
            throw new IllegalArgumentException("index out of bounds");
        }
        if(index == 0) {
            this.insertAtHead(item);
        } else if(index == this.size) {
            this.insertAtTail(item);
        } else {
            Node<T> newNode = new Node(item);
            Node<T> prevNode = this.getNodeAtIndex(index-1);
            Node<T> currNode = prevNode.getNext();
            newNode.setNext(currNode);
            prevNode.setNext(newNode);
            this.size++;
        }


    }

    public void insertAtHead(T item) {
        Node<T> newHead = new Node<>(item);
        newHead.setNext(this.head);
        this.head = newHead;
        this.size++;
    }


    public void insertAtTail(T item) {
        if(this.isEmpty()) {
            insertAtHead(item);
            return;
        }
        Node<T> curr = this.head;
        while(curr.getNext() != null) {
            curr = curr.getNext();
        }
        Node<T> newTail = new Node<>(item);
        curr.setNext(newTail);
        this.size++;
    }

    public T removeFromHead() {
        if(this.isEmpty()) {
            throw new IllegalStateException("You cannot remove from an empty list");
        }
        T item = this.head.getItem();
        this.head = this.head.getNext();
        this.size--;
        return item;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return (this.size == 0);
    }

    public String toString() {
        if(this.isEmpty()) {
            return "[empty]";
        }
        StringBuilder sb = new StringBuilder();
        Node<T> curr = this.head;
        while(curr != null) {
            sb.append(curr.getItem() + ", ");
            curr = curr.getNext();
        }
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator(this);
    }

}
