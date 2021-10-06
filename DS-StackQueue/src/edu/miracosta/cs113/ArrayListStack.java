package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class ArrayListStack<E> implements StackInterface<E>{
    private final ArrayList<E> queue;
    public ArrayListStack(){
        queue = new ArrayList<>();
    }


    @Override
    public boolean empty() {
        return queue.size()==0;
    }

    @Override
    public E peek() {
        checkSize();
        return queue.get(queue.size()-1);
    }

    @Override
    public E pop() {
        checkSize();
        return queue.remove(queue.size()-1);
    }

    @Override
    public E push(E obj) {
        queue.add(obj);
        return obj;
    }
    private void checkSize(){
        if (empty()){
            throw new EmptyStackException();
        }
    }
}
