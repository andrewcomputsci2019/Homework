package edu.miracosta.cs113;

import java.lang.reflect.Array;
import java.util.*;

public class CircularArrayQueue<E> implements Queue<E> {
    private int front;
    private int rear;
    private int size;
    private int capacity;
    private E[] circularArrayQueue;
    private static final int DEFAULT_CAPACITY = 10;
    public CircularArrayQueue(){
        circularArrayQueue = (E[]) new Object[DEFAULT_CAPACITY];
    }
    public CircularArrayQueue(int initialCapacity){
        circularArrayQueue = (E[]) new Object[initialCapacity];
    }
    @Override
    public int size() {
        return circularArrayQueue.length;
    }

    @Override
    public boolean isEmpty() {
        return this.size()==0;
    }

    @Override
    public boolean contains(Object o) {
        for (E thing: circularArrayQueue){ // you can also use the arraylist contains as well
            if (o.equals(thing)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return Arrays.stream(circularArrayQueue).iterator();
    }

    @Override
    public Object[] toArray() {
        if (isEmpty()){
            return new Object[0];
        }else{
            Object[] array = new Object[size];
            int i=0;
            for (Object thing : circularArrayQueue){
                array[i++] = thing;
            }
            return array;
        }
    }
    @SuppressWarnings("unchecked") //personal preferences to disable warnings
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length<size){
            a = (T[]) new Object[size];
        }else if (a.length>size){
            a[size] = null;
        }
        int i=0;
        for (E e:circularArrayQueue){
            a[i++] = (T)e;
        }
        return a;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        for (int i=0; i<size;i++){
            circularArrayQueue[i]= null;
        }
        size=0;
        front=0;
        rear=0;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    /**
     * reallocate method for internal array
     */
    private void reallocate(){

    }
    /**
     * trim array to size
     */
    private void trim(){

    }
}
