package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class ArrayListStack<E> implements StackInterface<E>{
    private final ArrayList<E> stack;
    public ArrayListStack(){
        stack = new ArrayList<>();
    }


    @Override
    public boolean empty() {
        return stack.size()==0;
    }

    @Override
    public E peek() {
        checkSize();
        return stack.get(stack.size()-1);
    }

    @Override
    public E pop() {
        checkSize();
        return stack.remove(stack.size()-1);
    }

    @Override
    public E push(E obj) {
        stack.add(obj);
        return obj;
    }
    private void checkSize(){
        if (empty()){
            throw new EmptyStackException();
        }
    }
}
