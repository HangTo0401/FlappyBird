package com.company;

public class QueueList<T> {
    private Object head, foot;
    private int size = 0;

    //FIFO data structure
    public QueueList() {
        head = foot = null;
    }

    //Add item on the top
    public void push(T element){
        Object obj = new Object(element);

        if (head == null){
            head = foot = obj;
        } else {
            foot.next = obj;
            foot = obj;
        }

        size++;
    }

    //Remove first element on queue
    public T pop(){
        T value = head.value;
        head = head.next;
        size--;
        return value;
    }

    public T get(int id){
        Object obj = head;

        if(head == null) return null;

        for(int i =0; i < id; i++) {
            obj = obj.next;
            if(obj == null) return null;
        }

        return obj.value;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    private class Object {
        Object(T value) {
            this.value = value;
        }

        T value;
        Object next;
    }
}
