import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Created by mac on 15/10/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
    }

    public RandomizedQueue() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;

    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(1, size() + 1);
//        int index = size();
        Node pre = null;
        Node current = first;
        index--;
        while (index > 0) {
            pre = current;
            current = current.next;
            index--;
        }
        if (current == first) {
            first = first.next;
            if (isEmpty()) last = first;
        } else {
            pre.next = current.next;
            last = pre;
        }
        n--;
        return current.item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(1, size() + 1);
        Node current = first;
        index--;
        while (index > 0) {
            current = current.next;
            index--;
        }
        return current.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListItator();
    }


    private class ListItator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
