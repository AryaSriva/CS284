import java.util.Scanner;

/*
 * Name: Aryaman Srivastava
 * Pledge: I pledge my honor that I have abided by the Stevens Honors System
 * CS284 HW3
 */
public class TestTaskList<E> {
    private TaskList<E> toDoList = new TaskList<E>();
    private Scanner scan;

    public static void main(String[] args) {
        // main method, where we initialize a new TestTaskList object and call the
        // printMenu method on it.
        TestTaskList<?> tester = new TestTaskList<>();
        System.out.println("~~~ TO-DO List Program, created by truly yours ~~~");
        tester.printMenu();
    }

    public void printMenu() {
        // method to print the menu for the user to navigate through the program, calls
        // the processMenuItem method to aid in processing user input
        if (toDoList.getActive().getSize() == 0) {
            System.out.println("==> Currently there are NO items in the To-Do List");
        } else {
            System.out.println("Current TO-DO List");
            System.out.println("-------------------");
            toDoList.showActiveTasks();
        }
        System.out.println("To add a new task without priority information, press 1.");
        System.out.println("To add a new task with a priority information, press 2.");
        System.out.println("To cross off the task at the top of the list, press 3.");
        System.out.println("To cross off a certain task in the list, press 4.");
        System.out.println("To see the top 3 highest priority tasks, press 5.");
        System.out.println("To see the completed tasks, press 6.");
        System.out.println("To see the all tasks that has been completed or still active, press 7.");
        System.out.println("To quit the program, press 8.");
        int menuItem = 0;
        scan = new Scanner(System.in);
        try { //check that the user's input is valid
            menuItem = Integer.parseInt(scan.nextLine());
        } catch (NumberFormatException e) {
            processMenuItem(0);
        }
        while (processMenuItem(menuItem)) { //keep running the loop until the user enters 8
            if (toDoList.getActive().getSize() == 0) {
                System.out.println("==> Currently there are NO items in the To-Do List");
            } else {
                System.out.println("Current TO-DO List");
                System.out.println("-------------------");
                toDoList.showActiveTasks();
            }
            System.out.println("To add a new task without priority information, press 1.");
            System.out.println("To add a new task with a priority information, press 2.");
            System.out.println("To cross off the task at the top of the list, press 3.");
            System.out.println("To cross off a certain task in the list, press 4.");
            System.out.println("To see the top 3 highest priority tasks, press 5.");
            System.out.println("To see the completed tasks, press 6.");
            System.out.println("To see the all tasks that has been completed or still active, press 7.");
            System.out.println("To quit the program, press 8.");
            try { //check that the user's input is valid
                menuItem = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR! please enter a valid number");
                menuItem = Integer.parseInt(scan.nextLine());
            }
        }
    }

    private boolean processMenuItem(int menuItem) {
        // processes the user's input and calls the appropriate methods to perform the
        // functionality
        if (menuItem < 1 || menuItem > 8) {
            System.out.println("ERROR! Please enter a number between 1 and 8 (included).");
            printMenu();
        }
        if (menuItem == 1) { //enter a task into the priority queue
            System.out.println("Please enter the task description");
            scan = new Scanner(System.in);
            String task = scan.nextLine();
            if (toDoList.createTask((E) task)) {
                System.out.println("Successfully entered the task to the to-do list!");
                return true;
            }
            System.out.println("Couldn't add task to the to-do list!");
            return true;
        }
        if (menuItem == 2) { //enter a task with a given priority
            System.out.println("Please enter the task description");
            scan = new Scanner(System.in);
            String task = scan.nextLine();
            int priority = 0;
            System.out.println(
                    "Please enter a priority number (1 indicates highest priority, increasing numbers show lower priority) :");
            try {
                priority = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR! Please enter a number for priority");
                processMenuItem(menuItem);
            }
            if (toDoList.createTask((E) task, priority)) {
                System.out.println("Successfully entered the task to the to-do list!");
            } else {
                System.out.println("Couldn't add a task to the task list!");
            }
            return true;
        }
        if (menuItem == 3) { //remove the first(highest priority) item in the queue
            E data;
            try {
                data = toDoList.getActive().getFront().getData();
            } catch (NullPointerException e) {
                System.out.println("Task list is empty!");
                return true;
            }
            if (toDoList.crossOfMostUrgent()) {
                System.out.println("Task is completed and removed from the list: " + data);
                System.out.println("Successfully removed the most urgent task/top of the list task!");
            }
            return true;
        }
        if (menuItem == 4) { //cross of an task with the given task number
            System.out.println("Please enter the task number you would like to cross off the list: ");
            int taskNumber = 0;
            scan = new Scanner(System.in);
            try {
                taskNumber = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR! Please enter a number for the task number");
                processMenuItem(menuItem);
            }
            if (!toDoList.crossOffTask(taskNumber)) {
                System.out.println("Unsuccessful operation! Please try again!");
            } else {
                System.out.println("Successfully removed the task number: " + taskNumber);
            }
            return true;
        }
        if (menuItem == 5) { //print the top three tasks in the to-do list
            System.out.println("Top 3 highest priority tasks:");
            System.out.println("------------------------------");
            System.out.println("Printing Top Three Tasks...");
            toDoList.printTopThreeTasks();
            return true;
        }
        if (menuItem == 6) { //show all the completed tasks
            System.out.println("Completed Tasks:");
            System.out.println("----------------");
            toDoList.showCompletedTasks();
            return true;
        }
        if (menuItem == 7) { //show all tasks(completed and tasks to-do)
            System.out.println("All of the Tasks - Both completed and active:");
            System.out.println("---------------------------------------------");
            toDoList.showAllTasks();
            return true;
        } else {
            return false;
        }
    }
}
