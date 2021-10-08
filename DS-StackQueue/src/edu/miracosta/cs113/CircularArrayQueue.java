package edu.miracosta.cs113;

import java.lang.reflect.Array;
import java.util.*;
@SuppressWarnings("unchecked") // does not matter here because the compiler will prevent non generic objects
public class CircularArrayQueue<E> implements Queue<E> {
    /**
     * index of the Front of the queue
     */
    private int front;
    /**
     * index of the Rear of the queue
     */
    private int rear;
    /**
     * How many elements in the queue
     */
    private int size;
    /**
     * The amount of space the array has in terms of elements
     */
    private int capacity;
    /**
     * the array that holds the data
     */
    private E[] circularArrayQueue; // should be an object array to avoid generic casting issue
    private static final int DEFAULT_CAPACITY = 10;
    public CircularArrayQueue(){
        this(DEFAULT_CAPACITY); // makes queue with default capacity
    }
    public CircularArrayQueue(int initialCapacity){
        circularArrayQueue = (E[]) new Object[initialCapacity];
        front=0;
        rear=initialCapacity-1;
        size=0;
        capacity = initialCapacity;
    }
    @Override
    public int size() {
        return size;
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
       return offer(e);
    }

    @Override
    public boolean remove(Object o) { //if object is not rear or head
        if (o.equals(circularArrayQueue[rear])){

        }else if (o.equals(circularArrayQueue[front])){
            try{
                remove();
                return true;
            }catch (Exception e){
                return false;
            }
        }
        else{

        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E thing: c){
            add(thing);
        }
        return true;
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
        rear=capacity-1;
    }

    @Override
    public boolean offer(E e) {
        if (size == capacity) {
            reallocate();
        }
        circularArrayQueue[(rear=(rear+1)%capacity)] = e;
        size++;
        return true;
    }

    @Override
    public E remove() {
        if (!isEmpty()) {
            E data = circularArrayQueue[front];
            circularArrayQueue[front] = null;
            front = (front+1)%capacity;
            size--;
            return data;
        }else{
            throw new NoSuchElementException("the queue is empty");
        }

    }

    @Override
    public E poll() {
        if (isEmpty()){
            return null;
        }
        E data = circularArrayQueue[front];
        circularArrayQueue[front] = null;
        front = (front+1)%capacity;
        size--;
        return data;
    }

    @Override
    public E element() {
        if (!isEmpty()){
            return circularArrayQueue[front];
        }else{
            throw new NoSuchElementException("Queue is emtpy");
        }
    }

    @Override
    public E peek() {
        if (isEmpty()){
            return null;
        }
        return circularArrayQueue[front];
    }

    /**
     * reallocate internal array to capacity*2 size
     */
    private void reallocate(){
        E[] temp = (E[]) new Object[(capacity*=2)]; //needes to be changed to fix issue with rear being larger than front
        copy(temp);
        front= 0;
        rear=size-1;
        circularArrayQueue = temp;
    }
    /**
     * trim array to size
     */
    public void trim(){
        E[] temp = (E[]) new Object[size];
        copy(temp);
        front =0;
        rear=size-1;
        capacity=size;
        circularArrayQueue = temp;
    }
    private void copy(E[] array){
        if (rear<front) // case where array loops back to index 0-front
        {
            System.arraycopy(circularArrayQueue,front,array,0,size-front);
            System.arraycopy(circularArrayQueue,rear,array,size-front,front-rear);
        }else{
            //@TODO need to finish this side of the reallocation
            System.arraycopy(circularArrayQueue,0,array,0,size);
        }
    }
}
