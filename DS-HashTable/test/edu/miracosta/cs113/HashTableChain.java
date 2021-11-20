package edu.miracosta.cs113;
import java.util.*;
import java.util.function.Predicate;

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
                iter=null;index++; // advance index if either index points to a null item, or if the defined LinkedList is empty Also set iter to null

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
        table = new LinkedList[DEFAULT_CAPACITY];
    }

    // returns number of keys
    @Override
    public int size() {
        return numKeys;
    }

    // returns boolean if table has no keys
    @Override
    public boolean isEmpty() {
        return numKeys==0;
    }

    // returns boolean if table has the searched for key
    @Override
    public boolean containsKey(Object key) {
        if(key==null){
            return table[0].stream().anyMatch(obj-> obj.getKey()==null);
        }
        int hashIndex = key.hashCode()% table.length;
        if(table[hashIndex]!=null){
            return table[hashIndex].stream().anyMatch(obj-> obj.getKey().equals(key));
        }
        return false;
    }

    // returns boolean if table has the searched for value
    @Override
    public boolean containsValue(Object value) {
        for (LinkedList<Entry<K, V>> entries : table) {
            if (entries == null) {
                continue;
            }
            for (Entry<K, V> entry : entries) {
                if (entry.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    // returns Value if table has the searched for key
    @Override
    public V get(Object key) {
        if(key==null){
            return getNull();
        }
        int hashIndex = key.hashCode()%table.length;// check if value is negative if so you need to add the table length to it @TODO
        LinkedList<Entry<K,V>> temp = table[hashIndex];
        if(temp!=null && !temp.isEmpty()){
            return temp.stream().filter(obj-> obj.getKey().equals(key)).map(Entry::getValue).findFirst().orElse(null);
        }
        return null;
    }

    /**
     * Private helper method to find null key
     * @return the null key value if one is present else null
     */
    private V getNull(){
        return table[0]!=null?table[0].stream().filter(obj-> obj.getKey()==null).map(Entry::getValue).findFirst().orElse(null):null;
    }

    // adds the key and value pair to the table using hashing
    @Override
    public V put(K key, V value) {
        if(key==null){
            return putNull(value);
        }
        int hashIndex = key.hashCode()%table.length;
        V valTemp = null;
        if(table[hashIndex]==null){
            table[hashIndex] = new LinkedList<>();
            table[hashIndex].add(new Entry<>(key, value));
        }else{
            //wanted to use a more modern approach to this instead of using a for loop
            LinkedList<Entry<K,V>> temp = table[hashIndex];
            Optional<Entry<K, V>> optional =  temp.stream().filter(obj-> obj.getKey().equals(key)).findFirst();
            if(optional.isPresent()){
                valTemp = optional.get().getValue();
                optional.get().setValue(value);
            }else{
                temp.add(new Entry<>(key, value));
            }
            /*for(Entry<K,V> map : temp){
                if(map.getKey().equals(key)){
                    valTemp = map.value;
                    map.setValue(value);
                }
            }
            temp.add(new Entry<>(key, value));*/
        }
        numKeys++;
        checkForRehash();
        return valTemp;
    }
    private V putNull(V value){
        V temp = null;
        if(table[0]==null){
            LinkedList<Entry<K,V>> list = new LinkedList<>();
            list.add(new Entry<>(null,value));
            table[0]=list;
        }else{
            Optional<Entry<K,V>> optional = table[0].stream().filter(obj-> obj.getKey()==null).findFirst();
            if(optional.isPresent()){
                temp = optional.get().getValue();
                optional.get().setValue(value);
            }
            table[0].add(new Entry<>(null,value));
        }
        numKeys++;
        checkForRehash();
        return temp;
    }

    private void checkForRehash(){
        if((numKeys/(double)table.length)>LOAD_THRESHOLD){
            rehash();
        }
    }
    /**
     * Resizes the table to be 2X +1 bigger than previous
     */
    private void rehash() {
    	// FILL HERE
        //@TODO last thing to do
        //first make a new array
        LinkedList<Entry<K,V>>[] temp = new LinkedList[(2* table.length+1)];
        for(LinkedList<Entry<K,V>> entries:table){
            if(entries!=null){
                for(Entry<K,V> entry:entries){
                    if(entry.getKey()==null){
                        if(temp[0]==null){
                            temp[0] = new LinkedList<>();
                        }
                        temp[0].add(entry);
                    }
                    else{
                        int hashIndex = entry.getKey().hashCode()% temp.length;
                        if(temp[hashIndex]==null){
                            temp[hashIndex] = new LinkedList<>();
                        }
                        temp[hashIndex].add(entry);
                    }
                }
            }
        }
        table = temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder() ;
        for (LinkedList<Entry<K, V>> list : table) {
            if (list != null) {
                for (Entry<K, V> nextItem : list) {
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
        if(containsKey(key)){
            int hashIndex;
            if(key ==null){
                hashIndex =0;
            }else {
                hashIndex = key.hashCode() % table.length;
            }
            Predicate<Entry<K,V>> findKey = key==null?Kv-> Kv.getKey()==null:Kv-> Kv.getKey().equals(key);
            Iterator<Entry<K,V>> iterator = table[hashIndex].iterator();
            while(iterator.hasNext()) {
                Entry<K,V> temp;
                if(findKey.test((temp=iterator.next()))){
                    iterator.remove();
                    numKeys--;
                    return temp.getValue();
                }
            }
        }
        return null;
    }

    // throws UnsupportedOperationException
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    // empties the table
    @Override
    public void clear() {
        Arrays.fill(table,null);//sets all index to null
        numKeys=0;
    }

    @FunctionalInterface
    interface KeysInter<K,V>{
        void keyIter(LinkedList<Entry<K,V>>list);
    }
    // returns a view of the keys in set view
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        KeysInter<K,V> func = (LinkedList<Entry<K,V>> list) -> {
          if(list!=null){
              list.forEach(obj-> keys.add(obj.getKey()));
          }
        };
        Arrays.stream(table).forEach(func::keyIter);
        return keys;
    }

    // throws UnsupportedOperationException
    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException() ;
    }


    // returns a set view of the hash table
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public boolean equals(Object o) {
    	// FILL HERE
        //Fill in map version of equals
        //@TODO
        return true;
    }

    @Override
    public int hashCode() {
    	//FILL HERE
        //fill in map version of this function
        //@TODO
        return 0;
    }
}
