import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Name: Aryaman Srivastava
 * Pledge: I pledge my honor that I have abided by the Stevens Honors System
 * CS284 HW3
 */
public class TaskList<E> {
    private ListQueue<E> all;
    private ListQueue<E> active;
    private ListQueue<E> completed;
    private int LOW_PRIORITY = Integer.MAX_VALUE;
    private int HIGH_PRIORITY = 1;

    public TaskList() {
        // constructor for a taskList: initializes all of the ListQueue attributes
        all = new ListQueue<E>();
        active = new ListQueue<E>();
        completed = new ListQueue<E>();
    }

    public boolean createTask(E item) {
        // adds task with given item and the lowest priority to the all and active
        // ListQueue. returns false if the item is null, otherwise returns true
        if (item == null) {
            return false;
        }
        active.addRear(item);
        all.addRear(item);
        return true;
    }

    public boolean createTask(E item, int priority) {
        // adds task with given item and priority to the all and active ListQueue.
        // returns false if the item is null, otherwise returns true
        if (item == null) {
            return false;
        }
        all.offer(item, priority);
        active.offer(item, priority);
        return true;
    }

    public ListQueue<E> getAll() {
        // getter method for all ListQueue
        return all;
    }

    public ListQueue<E> getActive() {
        // getter method for active ListQueue
        return active;
    }

    public ListQueue<E> getCompleted() {
        // getter method for completed ListQueue
        return completed;
    }

    public void printTopThreeTasks() {
        // prints the top 3 highest priority tasks, or the highest priority tasks if the
        // length of the all queue is less than 3.
        Iterator<E> iter = active.iterator();
        int i = 0;
        while (iter.hasNext() && i < 3) {
            System.out.println(iter.next());
            i++;
        }
    }

    private void printTasks(ListQueue<E> queue) {
        // helper method for printing all the tasks in the given queue.
        Iterator<E> iter = queue.iterator();
        while (iter.hasNext()) {
            System.out.println(HIGH_PRIORITY + ". " + iter.next());
            HIGH_PRIORITY++;
        }
        HIGH_PRIORITY = 1;
    }

    public void showActiveTasks() {
        // print all the tasks in the active list queue
        printTasks(active);
    }

    public void showAllTasks() {
        // print all the tasks in the all list queue
        printTasks(all);
    }

    public void showCompletedTasks() {
        // print all the tasks in the completed list queue
        printTasks(completed);
    }

    public boolean crossOfMostUrgent() {
        // removes the first item in the active queue(the item with the highest
        // priority), returns true if it is successfully removed, otherwise print an
        // error and returns false
        try {
            completed.addRear(active.getFront().getData());
            active.poll();
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Task List is empty!");
        }
        return false;
    }

    public boolean crossOffTask(int taskNumber) {
        // removes the task with the given task number, returns true if it was removed
        // successfully, otherwise return false
        if (taskNumber > active.getSize() || taskNumber < 1) {
            return false;
        }
        if (taskNumber == 1) { // removing the first task
            crossOfMostUrgent();
            return true;
        }
        Iterator<E> activeIterator = active.iterator();
        ListQueue<E>.Node<E> front = active.getFront();
        while (activeIterator.hasNext()) {
            E data = activeIterator.next();
            if (HIGH_PRIORITY == taskNumber) {
                completed.offer(data, HIGH_PRIORITY);
                active.remove(front);
                HIGH_PRIORITY = 1;
                return true;
            }
            front = front.getNext();
            HIGH_PRIORITY++;
        }
        HIGH_PRIORITY = 1; // couldn't find the task so return false
        return false;
    }

}
