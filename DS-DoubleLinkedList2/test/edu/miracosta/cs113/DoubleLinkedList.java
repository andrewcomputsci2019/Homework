package edu.miracosta.cs113;
import java.util.*;

public class DoubleLinkedList<E> extends AbstractSequentialList<E>
{  // Data fields
    	private Node<E> head = null;   // points to the head of the list
    	private Node<E> tail = null;   //points to the tail of the list
    	private int size = 0;    // the number of items in the list
  
  public void add(int index, E obj)
  { // Fill Here
      listIterator(index).add(obj);
   }

  public void addFirst(E obj) { // Fill Here //use list iter in add index method
     this.add(0,obj);
  }
  public void addLast(E obj) { // Fill Here //use list iter in add index method
      this.add(size,obj);
  }

  public E get(int index) 
  { 	ListIterator<E> iter = listIterator(index);
      try {
          return iter.next();
      }catch (NoSuchElementException e){
          throw new IndexOutOfBoundsException("index is out of bounds");
      }
  }  
  public E getFirst() { return head.data;  }
  public E getLast() { return tail.data;  }

  public int size() {  return this.size;  } // Fill Here

  public void clear(){
      ListIterator<E> iter = listIterator();
      while (iter.hasNext()){
          iter.next();
          iter.remove();
      }
  }
  public E remove(int index)
  {     E returnValue = null;
        ListIterator<E> iter = listIterator(index);
        if (iter.hasNext())
        {   returnValue = iter.next();
            iter.remove();
        }
        else {   throw new IndexOutOfBoundsException();  }
        return returnValue;
  }

  public Iterator<E> iterator() { return new ListIter(0);  }
  public ListIterator<E> listIterator() { return new ListIter(0);  }
  public ListIterator<E> listIterator(int index){return new ListIter(index);}
  public ListIterator<E> listIterator(ListIterator iter)
  {     return new ListIter( (ListIter) iter);  }

  // Inner Classes
  private static class Node<E>
  {     private E data;
        private Node<E> next = null;
        private Node<E> prev = null;

        private Node(E dataItem)  //constructor
        {   data = dataItem;   }
  }  // end class Node

  public class ListIter implements ListIterator<E>
  {
        private Node<E> nextItem;      // the current node
        private Node<E> lastItemReturned;   // the previous node
        private int index;   //

    public ListIter(int i)  // constructor for ListIter class
    {   if (i < 0 || i > size)
        {     throw new IndexOutOfBoundsException("Invalid index " + i); }
        lastItemReturned = null;
 
        if (i == size)     // Special case of last item
        {     index = size;     nextItem = null;      }
        else          // start at the beginning
        {   nextItem = head;
            for (index = 0; index < i; index++)  nextItem = nextItem.next;   
        }// end else
    }  // end constructor

    public ListIter(ListIter other)
    {   nextItem = other.nextItem;
        index = other.index;    }

    public boolean hasNext() {  return nextItem!=null; } // Fill Here
    public boolean hasPrevious()
    {   return (nextItem==null && size!=0)||(nextItem!=null&&nextItem.prev!=null);} // Fill Here
    public int previousIndex() {
        if (index==0){
            return -1;
        }else{
            return index-1;
        }
    } // Fill Here
    public int nextIndex() {
        return Math.min(index, size);
    } // Fill here
    public void set(E o)  {
        if(lastItemReturned==null){
            throw new IllegalStateException("next or previous has not been called");
        }else{
            lastItemReturned.data = o;
        }
    }  // not implemented
    public void remove(){
        if (lastItemReturned==null){
            throw new IllegalStateException("next or previous has not been called");
        }
        if (lastItemReturned==head){
            head = head.next;
            if (head!=null) {
                head.prev = null;
            }
            index--;
            size--;
            lastItemReturned=null;
        }else if (lastItemReturned==tail){
                tail = lastItemReturned.prev;
                tail.next = null;
                index--;
                size--;
                lastItemReturned = null;
        }else{
            lastItemReturned.prev.next = lastItemReturned.next;
            lastItemReturned.next.prev = lastItemReturned.prev;
            size--;
            index--;
            lastItemReturned = null;
        }
    }      // not implemented

    public E next()
    {
        if (hasNext()){
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }else{
            //throw new IndexOutOfBoundsException("out of bounds");
            throw new NoSuchElementException("element does not exist");// this is the correct error to throw
        }
    }

    public E previous()
    {
        if(!hasPrevious()){
            throw new NoSuchElementException("no element exist");
        }else if (nextItem == null){
            nextItem = tail;
        }else {
            nextItem= nextItem.prev;
        }
        lastItemReturned = nextItem;
        index--;
        return lastItemReturned.data;
    }

    public void add(E obj) {
        if (head==null){ //empty list
            tail = head = new Node<>(obj); // one liner 😀
            size++;
            index++;
        }
        else if (nextItem==head){ // headInsertion
            Node<E> insert = new Node<>(obj);
            head.prev = insert;
            insert.next = head;
            head = insert;
            index++;
            size++;
            lastItemReturned = null;
        }
        else if (nextItem==null){ // tail insertion
            Node<E> insert = new Node<>(obj);
            insert.prev = tail;
            tail.next = insert;
            tail = insert;
            size++;
            index++;
            lastItemReturned = null;
        }else{ //mid list insertion
            Node<E> insert = new Node<>(obj);
            insert.next = nextItem;
            insert.prev = nextItem.prev;
            nextItem.prev.next = insert;
            nextItem.prev = insert;
            lastItemReturned = null;
            index++;
            size++;
        }

    // Fill Here
    }
  }// end of inner class ListIter
}// end of class DoubleLinkedList
