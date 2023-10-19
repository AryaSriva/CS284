
/*
 * Name: Aryaman Srivastava
 * Pledge: I pledge my honor that I have abided by the Stevens Honors System
 * CS284 HW3
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListQueue<E> {
    public class Node<E> {
        // Node subclass for Node Object
        private E data;
        private Node<E> next;
        private int priority;

        public Node(E dataItem) {
            // constructor for a Node object with given data
            data = dataItem;
            next = null;
            priority = Integer.MAX_VALUE;
        }

        public Node(E dataItem, int priority) {
            // constructor for a Node object with given dataItem and priority
            this.priority = priority;
            data = dataItem;
            next = null;
        }

        public Node(E dataItem, Node<E> ref, int priority) {
            // constructor for a Node object with given dataItem, priority, and ref(next
            // node)
            this.next = ref;
            this.priority = priority;
            data = dataItem;
        }

        public E getData() {
            // returns data of this node
            return data;
        }

        public Node<E> getNext() {
            // returns the next node after this node
            return next;
        }
    }

    private class Iter implements Iterator<E> {
        // Iter Subclass for Iter Object
        private Node<E> next = front;

        public boolean hasNext() {
            // check if the next Node is null
            return next != null;
        }

        public E next() {
            // returns the data stored in next Node(attribute) and assigns the next
            // Node(attribute) to the next Node of next Node(attribute).
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E data = next.getData();
            next = next.getNext();
            return data;
        }

        public void remove() throws UnsupportedOperationException {
            /*
             * removes the node, but operation is not supported for this class as we don't
             * want to remove nodes with this method(has to be implemented for interface
             * Iterator<E>)
             */}
    }

    private Node<E> front;
    private int size;

    public ListQueue() {
        // constructor for an empty priority queue(single linked list)
        size = 0;
        front = null;
    }

    public ListQueue(Node<E> first) {
        // constructor for a priority queue with one item(single linked list)
        front = first;
        size = 1;
    }

    public Node<E> getFront() {
        // return the front node of the priority queue
        return front;
    }

    public void setFront(Node<E> front) {
        // set the front node of the priority queue to the given node
        this.front = front;
    }

    public int getSize() {
        // return the size of the priority queue
        return size;
    }

    public E peek() {
        // returns the information/data at the front of the queue
        Node<E> ref = this.getFront();
        return ref.getData();
    }

    public boolean offer(E item, int priority) {
        // adds item to priority queue according to its priority. If there are multiple
        // items with the same priority, this new item will be added after them. This
        // method will throw a NullPointer Exception if the item is null, otherwise it
        // will always return true.
        if (item == null) {
            throw new NullPointerException();
        }
        if (front == null) { // if task queue is empty, just set the front to the new node
            this.setFront(new Node(item, null, priority));
            size++;
            return true;
        }
        Node<E> frontNode = this.getFront();
        if (frontNode.priority > priority) { // the first task in the queue has a lower priority than the item we wish
                                             // to add, so the new item should be the first item
            this.setFront(new Node<E>(item, this.getFront(), priority));
            size++;
            return true;
        }
        while (frontNode.getNext() != null && frontNode.getNext().priority <= priority) { // loop until the node has a
                                                                                          // greater priority of the
                                                                                          // node we want to enter, or
                                                                                          // if we reach the end of the
                                                                                          // queue
            frontNode = frontNode.getNext();
        }
        Node<E> nodeToInsert = new Node<E>(item, frontNode.getNext(), priority);
        frontNode.next = nodeToInsert;
        size++;
        return true;
    }

    public boolean addRear(E item) {
        // adds item to the end of the priority queue unless the item is null, in which
        // case it will throw an exception. This method will always return true.
        if (item == null) {
            throw new NullPointerException();
        }
        Node<E> nodeToInsert = new Node<E>(item);
        if (front == null) { // if task queue is empty, just set the front to the new node
            this.setFront(nodeToInsert);
            size++;
            return true;
        }
        Node<E> frontNode = this.getFront();
        while (frontNode.getNext() != null) { // stop one node before the last one so we can set the "next node" of the
                                              // last node to the node we want to add
            frontNode = frontNode.getNext();
        }
        frontNode.next = nodeToInsert;
        size++;
        return true;
    }

    public E poll() {
        // returns the data from the first item of the queue and removes that item. If
        // the queue is empty(front is null), throw a NullPointerException
        if (this.getFront() == null) {
            throw new NullPointerException();
        }
        E data = this.peek();
        front = front.getNext();
        size--;
        return data;
    }

    public boolean remove(Node<E> toBeRemoved) {
        // removes the given node from the priority queue
        if (this.getFront() == null) { // no nodes in the queue means we can't remove anything from the queue(it is
                                       // empty)
            return false;
        }
        if (toBeRemoved == null) { // node we want to remove is null, so there is nothing to remove
            return false;
        }
        Node<E> frontNode = this.getFront();
        if (frontNode.getData() == toBeRemoved.getData()) { // the node we want to remove is the front node
            this.setFront(frontNode.getNext());
            size--;
            return true;
        }
        while (frontNode.getNext() != null && frontNode.getNext().getData() != toBeRemoved.getData()) { // stop the loop
                                                                                                        // one node
                                                                                                        // before the
                                                                                                        // node we want
                                                                                                        // to remove
            frontNode = frontNode.getNext();
        }
        if (frontNode.getNext() == null) { // couldn't find the node in the queue, meaning it does not exist
            return false;
        }
        frontNode.next = toBeRemoved.getNext();
        size--;
        return true;
    }

    public Iterator<E> iterator() {
        // returns an Iter object from the subclass we defined.
        return new Iter();
    }
}
