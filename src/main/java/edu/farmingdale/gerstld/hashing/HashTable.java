/**
 * Gerstl CSC329 code. (c) 2023, v1.3 David Gerstl, all rights reserved
 * for use in my CSC programming classes only. No consent is given for posting 
 * except to the Farmingdale State College LMS.
 * 
 */
package edu.farmingdale.gerstld.hashing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author gerstl
 * @param <K> Key type
 * @param <V> Value type
 */
// Note that by definition, all K should have a hashCode() method and an 
// equals() method. Thus we don't need, e., extends Hashable (there is no 
// such interface)
public class HashTable<K extends Comparable<K>, V> {

    static int DEFAULT_SIZE = 1_001;

    class Node<K, V> {

        K key;
        V value;
        Node<K, V> next;
        
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
        
        public boolean isKeyEqual(Node a) {
            if (this.key == a.key) {
                return true;
            } else {
                return false;
            }
        }
    }
    ArrayList<Node<K, V>> internalTable;
    int tableSize; // table array capacity)
    int count;
    int a;
    int b;
    

    public HashTable(int size) {
        Random rand = new Random();
        a = rand.nextInt(100);
        b = rand.nextInt(100);
        internalTable = new ArrayList<Node<K, V>>();
        tableSize = size;
        for (int i = 0; i < tableSize; i++) {
            internalTable.add(i, null);
        }
    } // int CTOR

    public HashTable() {
        this(DEFAULT_SIZE);
    } // default ctor

    public void clear() {
    }

    public int size() {
        return 0;
    }

    // iterator is private since Node<> is private. Used by sameAs()
    // and toString
    private Iterator<Node<K, V>> iterator() {
        var iterRv = new Iterator<Node<K, V>>() {
            int traversed = 0;
            Node<K, V> current = null;
            int row = 0;

            @Override
            public boolean hasNext() {
              return traversed < count;
            } // hasNext()

            @Override
            public Node<K, V> next() {
                while (current == null) {
                    if (!hasNext()) {
                        return null;
                    }
                    
                    current = internalTable.get(row);
                    if (current != null) {
                        traversed++;
                        Node<K, V> toReturn = current;
                        current = current.next;
                        if (current == null) {
                            row++;
                        }
                        //System.out.println(current);
                        //System.out.printf("TO RETURN: %s, %s\n", toReturn.key, toReturn.value);
                        
                        System.out.printf("row %d, key %s\n", row, toReturn.key);
                        return toReturn;
                    } // find chain and return first
                    row++;
                } 
                if (current != null) {
                    traversed++;
                    Node<K, V> toReturn = current;
                    current = current.next;
                    //System.out.printf("TO RETURN: %s, %s\n", toReturn.key, toReturn.value);
                    System.out.printf("row %d, key: %s\n", row, toReturn.key);
                    return toReturn;
                } // continue down chain
                return null;
            } // next()

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            } // remove()
        }; // anon inner class iterator
        return iterRv;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Node<K, V>> iter = this.iterator();
        sb.append("{");
        while (iter.hasNext()) {
            Node<K, V> n = iter.next();
            String k = n.key.toString();
            String v = n.value.toString();
            
            sb.append(k);
            sb.append("=");
            sb.append(v);
            sb.append(", ");
            
        }
        sb.append("}");
        return sb.toString();
    }

    public V get(K key) {
        return null;
    }

    public V put(K key, V value) {
        int hashedKey = hash(key);
        Node newNode = new Node(key, value);
        System.out.printf("hashed key: %d, key: %s, value: %s\n", hashedKey, key, value);
        System.out.println(this.toString());
        Node current = internalTable.get(hashedKey);
        

        if (current == null) {
            System.out.println("1st if");
            internalTable.set(hashedKey, newNode);
            count++;
            return null;
        }
        
        while (current.next != null) {
            if (current.isKeyEqual(newNode)) { // if same key replace and return old value
                System.out.println("second if");
                V oldValue = (V) current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }
        
        if (current.isKeyEqual(newNode)) { // if same key replace and return old value
            System.out.println("third if");
            V oldValue = (V) current.value;
            current.value = value;
            return oldValue;
        }
        count++;
        current.next = newNode;
        System.out.println("Worked!");
        return null;
    }

    public V remove(K key) {
        int hashed = hash(key);
        V removed;
        Node<K, V> current = internalTable.get(hashed);
        
        if (current.key == key) { // if first node in chain replace with its next
            Node<K, V> next = current.next;
            internalTable.set(hashed, next);
            return current.value;
        }
        while (true) {
            if (current.next == null) {
                return null;
            }
            if (current.next.key != key) { // if next is not the right node continue
                current = current.next;
            } else { // if it is the right node set its next to next.next to remove the desired node
                removed = current.next.value;
                current = current.next.next;
                return removed;
            }
            
        }
        
        //return null;
    }


    public Boolean sameAs(java.util.HashMap<K, V> other) {
        if (this.count != other.size()) {
            //System.out.printf("this table: %d, other table: %d\n", this.count, other.size());
            return false;
        }

        Iterator<Node<K, V>> iter = this.iterator();
        Node<K, V> current = iter.next();
        //System.out.printf("%s, %s\n", current.key, current.value);
        K key = current.key;
        V val = current.value;
        
        if (other.get(key) != val) {
            //System.out.printf("our key: %s\n", key);
            //System.out.printf("our val: %s, other val: %s\n", val, other.get(key));
            return false;
        }
        
        while (iter.hasNext()) {
            current = iter.next();
            key = current.key;
            val = current.value;
            //System.out.printf("our key and value: %s, %s| their value: %s\n", key, val, other.get(key));
            if (other.get(key) != val) {
                return false;
            }
        }
        //System.out.println("reaching end of same as");
        return true;
    }
    
    private int hash(K key) {
        return (key.hashCode() + a) * b % tableSize;
    }
    
//    private Boolean 

}
