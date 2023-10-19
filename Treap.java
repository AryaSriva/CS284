import java.util.Random;
import java.util.Stack;

/*
 * Name: Aryaman Srivastava
 * Pledge: I pledge my honor that I have abided by the Stevens Honors System
 * CS284 HW4
 */
public class Treap<E extends Comparable<E>> {
    private static class Node<E> {
        public E data;
        public int priority;
        public Node<E> left;
        public Node<E> right;

        /**
         * @param data
         * @param priority
         *                 Constructs a node object, with initial left and right
         *                 pointers being null. If the data given is null, throw an
         *                 exception
         */
        public Node(E data, int priority) {
            if (data == null) {
                throw new IllegalArgumentException("data is null!");
            }
            if (priority < 0) {
                throw new IllegalArgumentException("priority must be >= 0!");
            }
            this.data = data;
            this.priority = priority;
            this.left = null;
            this.right = null;

        }

        /**
         * @return
         *         is called on the parent node, and rotates the node right and
         *         reassigns child nodes accordingly
         *         returns the new parent node
         */
        public Node<E> rotateRight() {
            Node<E> nodeRef = this.left;
            this.left = nodeRef.right;
            nodeRef.right = this;
            return nodeRef;
        }

        /**
         * @return
         *         is called on the parent node, and rotates the node left and reassigns
         *         child nodes accordingly
         *         returns the new parent node
         */
        public Node<E> rotateLeft() {
            Node<E> nodeRef = this.right;
            this.right = nodeRef.left;
            nodeRef.left = this;
            return nodeRef;
        }

        /*
         * @return
         * returns a string representing the Node with its key and priority
         */
        public String toString() {
            String str = "(key = " + data + ", priority = " + priority + ")";
            return str;
        }
    }

    private Random priorityGenerator;
    private Node<E> root;
    private Stack<Integer> priorities = new Stack<Integer>();

    /**
     * constructs the Treap with a null root and intializes the random priority
     * generator
     */
    public Treap() {
        root = null;
        priorityGenerator = new Random(0);
    }

    /**
     * @param seed
     *             constructs the Treap with a null root and intializes the random
     *             priority generator with the given seed
     */
    public Treap(long seed) {
        root = null;
        priorityGenerator = new Random(seed);
    }

    /**
     * @param key
     * @return
     *         calls the add(E key, int priority) method with a random priority and
     *         returns the result of that method call(true if node was added, false
     *         otherwise)
     */
    public boolean add(E key) {
        int priority = priorityGenerator.nextInt(Integer.MAX_VALUE);
        return add(key, priority);
    }

    /**
     * @param key
     * @param priority
     * @return
     *         returns true if the node was successfuly added to the Treap, or false
     *         if a node with the same key or priority already exists
     */
    public boolean add(E key, int priority) {
        Node<E> toInsert = new Node<E>(key, priority);
        Stack<Node<E>> stack = new Stack<Node<E>>();
        if (root == null) { // if the new node is the first node to be added to the Treap, make it the root
            root = toInsert;
            priorities.push(priority);
            return true;
        }
        if (find(key) || priorities.contains(priority)) { // check if there is already a node with the same priority or
                                                          // key
            return false;
        } else {
            Node<E> currNode = root;
            while (currNode != null) { // continually move down the tree until we find an empty leaf to insert the
                                       // node, while also maintaining the BST property(maintained by keys)
                if (currNode.data.compareTo(key) < 0) {
                    stack.push(currNode);
                    if (currNode.right == null) {
                        currNode.right = toInsert;
                        break;
                    } else {
                        currNode = currNode.right;
                    }
                } else {
                    stack.push(currNode);
                    if (currNode.left == null) {
                        currNode.left = toInsert;
                        break;
                    } else {
                        currNode = currNode.left;
                    }
                }
            }
            reheap(stack, toInsert); // reheap the binary tree to make it a treap
            priorities.push(priority); // add the new priority into our stack of priorities
            return true;
        }
    }

    /**
     * @param stack
     * @param toInsert
     *                 rotates the given node upwards to restore the heap property
     *                 which is maintained by the priority attribute of the node,
     *                 making the binary tree into a "treap"
     */
    private void reheap(Stack<Node<E>> stack, Node<E> toInsert) {
        Node<E> currRoot = stack.pop();
        while (!stack.isEmpty()) { // while the node still has ancestors, continually check if we need to rotate
                                   // the node upwards, and if we do, do so while also editing the node relations
                                   // in the tree to reflect this
            if (currRoot.priority < toInsert.priority) {
                if (currRoot.right != null) {
                    if (currRoot.right.data.compareTo(toInsert.data) == 0) {
                        if (stack.peek().right == currRoot) {
                            stack.peek().right = currRoot.rotateLeft();
                        } else {
                            stack.peek().left = currRoot.rotateLeft();
                        }
                    }
                }
                if (currRoot.left != null) {
                    if (currRoot.left.data.compareTo(toInsert.data) == 0) {
                        if (stack.peek().right == currRoot) {
                            stack.peek().right = currRoot.rotateRight();
                        } else {
                            stack.peek().left = currRoot.rotateRight();
                        }
                    }
                }
            }
            currRoot = stack.pop();
        }
        if (root.left != null && root.priority < root.left.priority) { // if the node has a higher priority than the
                                                                       // root, we must rotate the node upwards and
                                                                       // change the root attribute to reflect the new
                                                                       // root
            root = root.rotateRight();
        } else if (root.right != null && root.priority < root.right.priority) {
            root = root.rotateLeft();
        }
    }

    /**
     * @param key
     * @return
     *         return true if the node with given key value was successfully
     *         deleted, return false otherwise(node doesn't exist in the Treap)
     */
    public boolean delete(E key) {
        if (!find(key)) { // first check if the node is in the tree
            return false;
        }
        Stack<Node<E>> parents = new Stack<Node<E>>();
        Node<E> currRoot = root;
        while (currRoot.data.compareTo(key) != 0) { // find the node while storing all its ancestors as well
            if (currRoot.data.compareTo(key) < 0) {
                parents.push(currRoot);
                currRoot = currRoot.right;
            } else {
                parents.push(currRoot);
                currRoot = currRoot.left;
            }
        }
        if (parents.isEmpty()) { // if we are removing the root, the stack will be empty, so first reassign the
                                 // root and then store the new root in the stack
            if (root.right == null) {
                root = root.rotateRight();
            }
            if (root.left == null) {
                root = root.rotateLeft();
            } else if (root.right.priority > root.left.priority) {
                root = root.rotateLeft();
            } else {
                root = root.rotateRight();
            }
            parents.push(root);
        }
        while (currRoot.right != null || currRoot.left != null) {
            /*
             * while the node is not a leaf, keep on rotating it downwards,
             * while also editing parent/ancestor relations to reflect the node being
             * removed and adding these
             * parents/ancestors into the stack
             */
            if (currRoot.right == null) {
                if (parents.peek().right == currRoot) {
                    parents.peek().right = currRoot.left;
                } else {
                    parents.peek().left = currRoot.left;
                }
                parents.push(currRoot.rotateRight());
            } else if (currRoot.left == null) {
                if (parents.peek().right == currRoot) {
                    parents.peek().right = currRoot.right;
                } else {
                    parents.peek().left = currRoot.right;
                }
                parents.push(currRoot.rotateLeft());
            } else {
                if (currRoot.right.priority < currRoot.left.priority) {
                    if (parents.peek().right == currRoot) {
                        parents.peek().right = currRoot.left;
                    } else {
                        parents.peek().left = currRoot.left;
                    }
                    parents.push(currRoot.rotateRight());
                } else {
                    if (parents.peek().right == currRoot) {
                        parents.peek().right = currRoot.right;
                    } else {
                        parents.peek().left = currRoot.right;
                    }
                    parents.push(currRoot.rotateLeft());
                }
            }
        }
        if (parents.peek().right != null && parents.peek().right.data.compareTo(key) == 0) { // check if the node to
                                                                                             // remove is the right or
                                                                                             // left child of the
                                                                                             // parent, then make the
                                                                                             // right/left child null to
                                                                                             // reflect the deletion
            parents.peek().right = null;
        } else {
            parents.peek().left = null;
        }
        return true; // since the node exists, it was therefore successfully deleted, so return true
    }

    /**
     * @param root
     * @param key
     * @return
     *         Returns true if a node with the given key exists in the tree with the
     *         given root, otherwise return false
     */
    private boolean find(Node<E> root, E key) {
        while (root != null) {
            if (root.data.compareTo(key) == 0) {
                return true;
            }
            if (root.data.compareTo(key) < 0) {
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return false;
    }

    /**
     * @param key
     * @return
     *         Calls and returns the value of the find(Node<E> root, E key) method
     *         with the given param key and the root of the treap as parameters
     */
    public boolean find(E key) {
        return find(root, key);
    }

    /**
     * @param current
     * @param level
     * @return
     *         BinaryTree/BinarySearchTree toString method(taken from class modules)
     *         Takes in the current node and the level of that node and returns a
     *         string representing the tree with that node as the root
     */
    private String toString(Node<E> current, int level) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < level; i++)
            s.append(" ");
        if (current == null)
            s.append("null\n");
        else {
            s.append(current.toString() + "\n");
            s.append(toString(current.left, level + 1));
            s.append(toString(current.right, level + 1));
        }
        return s.toString();
    }

    /*
     * @return
     * Returns a string representing the Treap
     */
    public String toString() {
        String s = toString(root, 0);
        return s;
    }
}
