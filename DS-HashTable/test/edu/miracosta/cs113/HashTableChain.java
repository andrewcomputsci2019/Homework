package edu.miracosta.cs113;
import java.util.*;

/**
 * HashTable implementation using chaining to tack a pair of key and value pairs.
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class HashTableChain<K, V> implements Map<K, V>  {

    private LinkedList<Entry<K, V>>[] table;
    private  int numKeys;
    /*
    * For rehashing use 2n+1 the other option is to use xor and bitshift greats bit to lower and user a power of two size
    * */
    private static final int DEFAULT_CAPACITY = 101;
    private static final double LOAD_THRESHOLD = 1.5;
    ///////////// ENTRY CLASS ///////////////////////////////////////

    /**
     * Contains key-value pairs for HashTable
     * @param <K> the key
     * @param <V> the value
     */
    private static class Entry<K, V> implements Map.Entry<K, V>{
        private K key;
        private V value;

        /**
         * Creates a new key-value pair
         * @param key the key
         * @param value the value
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Returns the key
         * @return the key
         */
        public K getKey() {
            return  key;
        }

        /**
         * Returns the value
         * @return the value
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets the value
         * @param val the new value
         * @return the old value
         */
        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }
        @Override
        public String toString() {
            return  key + "=" + value  ;
        }

    }

    ////////////// end Entry Class /////////////////////////////////

    ////////////// EntrySet Class //////////////////////////////////

    /**
     * Inner class to implement set view, basically an iterator in map form
     */
    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {


        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new SetIterator();
        }

        @Override
        public int size() {
            return numKeys ;
        }
    }

    ////////////// end EntrySet Class //////////////////////////////

    //////////////   SetIterator Class ////////////////////////////

    /**
     * Class that iterates over the table. Index is table location
     * and lastItemReturned is entry
     */
    private class SetIterator implements Iterator<Map.Entry<K, V>> {

        private int index = 0;
        private Entry<K,V> lastItemReturned = null;
        private Iterator<Entry<K, V>> iter = null; // used to iterate the linkedList

        @Override
        public boolean hasNext() {
            return index<table.length;
        }

        @Override
        public Map.Entry<K, V> next() {
            if(this.iter!=null){ // if an iter is declared to exist
                if(iter.hasNext()){//verify that it contains an item
                    lastItemReturned = iter.next(); // assign lastItemReturn and return it
                    return lastItemReturned;
                }iter = null;//if iter does not contain an item, set it to null and advance to next index
                index++;
            }
            while (this.hasNext()){//while index is a valid index in table, keep searching
                if(table[index]!=null){ // if the index points to a defined LinkedList grabs its iterator
                    iter = table[index].iterator();
                    if(iter.hasNext()){//verify that the iterator has a next item
                           lastItemReturned = iter.next();//return item found in the iterator
                           return lastItemReturned;
                    }
                }
                index++; // advance index if either index points to a null item, or if the defined LinkedList is empty
            }
            throw new NoSuchElementException("No element exist in map"); // out of indexes
        }

        @Override
        public void remove() {
            if(lastItemReturned==null) {
                throw new IllegalStateException("next has not been called");
            }
            HashTableChain.this.remove(lastItemReturned.key);
            lastItemReturned = null;
        }
    }

    ////////////// end SetIterator Class ////////////////////////////

    /**
     * Default constructor, sets the table to initial capacity size
     */
    public HashTableChain() {
        table = (LinkedList<Entry<K, V>>[]) new LinkedList[DEFAULT_CAPACITY];
    }

    // returns number of keys
    @Override
    public int size() {
        return numKeys;
    }

    // returns boolean if table has no keys
    @Override
    public boolean isEmpty() {
        return numKeys>0;
    }

    // returns boolean if table has the searched for key
    @Override
    public boolean containsKey(Object key) {
    	// Fill Here
    }

    // returns boolean if table has the searched for value
    @Override
    public boolean containsValue(Object value) {
    	// FILL HERE
    	
    }

    // returns Value if table has the searched for key
    @Override
    public V get(Object key) {
    	// FILL HERE
    }

    // adds the key and value pair to the table using hashing
    @Override
    public V put(K key, V value) {
    	// FILL HERE
    }


    /**
     * Resizes the table to be 2X +1 bigger than previous
     */
    private void rehash() {
    	// FILL HERE
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder() ;
        for (int i = 0; i < table.length; i++ ) {
            if (table[i] != null) {
                for (Entry<K, V> nextItem : table[i]) {
                    sb.append(nextItem.toString()).append(" ");
                }
                sb.append(" ");
            }
        }
        return sb.toString();

    }

    // remove an entry at the key location
    // return removed value
    @Override
    public V remove(Object key) {
    	// FILL HERE

    }

    // throws UnsupportedOperationException
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    // empties the table
    @Override
    public void clear() {
    	// Fill HERE
        Arrays.fill(table,null);
    }

    // returns a view of the keys in set view
    @Override
    public Set<K> keySet() {
    	// FILL HERE
    }

    // throws UnsupportedOperationException
    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException() ;
    }


    // returns a set view of the hash table
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
    	// FILL HERE
        return new EntrySet();
    }

    @Override
    public boolean equals(Object o) {
    	// FILL HERE

    }

    @Override
    public int hashCode() {
    	//FILL HERE

    }
}
