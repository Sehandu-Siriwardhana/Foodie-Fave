import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Foodies_Fave_Queue_Management_System {
    private static final int[] Cashier = {2, 3, 5}; // Maximum number of customers in each queue
    private static final int MAX_STOCK = 50; // Maximum number of burgers in stock
    private static final int WARNING_THRESHOLD = 10; // Stock threshold for warning message

    private static final String[] queue1 = new String[Cashier[0]];
    private static final String[] queue2 = new String[Cashier[1]];
    private static final String[] queue3 = new String[Cashier[2]];
    private static int stock = MAX_STOCK;

    public static void main(String[] args) {
        displayMenu();

        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "100":
                case "VFQ":
                    viewAllQueues();
                    break;
                case "101":
                case "VEQ":
                    viewEmptyQueues();
                    break;
                case "102":
                case "ACQ":
                    addCustomer(scanner);
                    break;
                case "103":
                case "RCQ":
                    removeCustomer(scanner);
                    break;
                case "104":
                case "PCQ":
                    removeServedCustomer();
                    break;
                case "105":
                case "VCS":
                    viewCustomersSorted();
                    break;
                case "106":
                case "SPD":
                    storeProgramData();
                    break;
                case "107":
                case "LPD":
                    loadProgramData();
                    break;
                case "108":
                case "STK":
                    viewRemainingStock();
                    break;
                case "109":
                case "AFS":
                    addToStock(scanner);
                    break;
                case "999":
                case "EXT":
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        } while (!choice.equals("999") && !choice.equals("EXT"));

        scanner.close();
    }

    //Displaying Menu
    private static void displayMenu() {
        System.out.println("Menu Options:");
        System.out.println("100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue.");
        System.out.println("103 or RCQ: Remove a customer from a Queue. (From a specific location)");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("106 or SPD: Store Program Data into file.");
        System.out.println("107 or LPD: Load Program Data from file.");
        System.out.println("108 or STK: View Remaining burgers Stock.");
        System.out.println("109 or AFS: Add burgers to Stock.");
        System.out.println("999 or EXT: Exit the Program.");
        System.out.print("Enter your choice: ");
    }
    //Viewing all queues
    private static void viewAllQueues() {
        System.out.println("*******");
        System.out.println("*   Cashiers    *");
        System.out.println("*******");

        int maxLength = Cashier[2];

        for (int i = 0; i < maxLength; i++) {
            if (i < Cashier[0]) {
                System.out.print(queue1[i] != null ? "O " : "X ");
            } else {
                System.out.print("  ");
            }
            System.out.print("\t\t");


            if (i < Cashier[1]) {
                System.out.print(queue2[i] != null ? "O " : "X ");
            } else {
                System.out.print("  ");
            }
            System.out.print("\t\t");

            System.out.print(queue3[i] != null ? "O " : "X ");

            System.out.println();

        }
        System.out.println("X-Not Occupied O-Occupied");
    }

    //Displaying Empty Queues
    private static void viewEmptyQueues() {
        System.out.println("Empty Queues:");

        if (isQueueEmpty(queue1)) {
            System.out.println("Queue 1 is empty.");
        }

        if (isQueueEmpty(queue2)) {
            System.out.println("Queue 2 is empty.");
        }

        if (isQueueEmpty(queue3)) {
            System.out.println("Queue 3 is empty.");
        }
    }

    private static boolean isQueueEmpty(String[] queue) {
        for (String customer : queue) {
            if (customer != null) {
                return false;
            }
        }
        return true;
    }
    //Adding new customer to Specific Queue
    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter the customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String[] selectedQueue;

        switch (queueNumber) {
            case 1:
                selectedQueue = queue1;
                break;
            case 2:
                selectedQueue = queue2;
                break;
            case 3:
                selectedQueue = queue3;
                break;
            default:
                System.out.println("Invalid queue number.");
                return;
        }

        if (isQueueFull(selectedQueue)) {
            System.out.println("Selected queue is full. Cannot add customer.");
        } else {
            for (int i = 0; i < selectedQueue.length; i++) {
                if (selectedQueue[i] == null) {
                    selectedQueue[i] = name;
                    System.out.println("Customer " + name + " added to the queue " + queueNumber + ".");
                    break;
                }
            }
        }

        updateStock(-5);
    }

    private static boolean isQueueFull(String[] queue) {
        return queue[queue.length - 1] != null;
    }
    //Removing a customer
    private static void removeCustomer(Scanner scanner) {
        System.out.print("Enter the queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        String[] selectedQueue;

        switch (queueNumber) {
            case 1:
                selectedQueue = queue1;
                break;
            case 2:
                selectedQueue = queue2;
                break;
            case 3:
                selectedQueue = queue3;
                break;
            default:
                System.out.println("Invalid queue number.");
                return;
        }

        System.out.print("Enter the customer position to remove (1 to " + selectedQueue.length + "): ");
        int position = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (position < 1 || position > selectedQueue.length) {
            System.out.println("Invalid customer position.");
            return;
        }

        if (selectedQueue[position - 1] == null) {
            System.out.println("No customer at the specified position.");
        } else {
            String removedCustomer = selectedQueue[position - 1];
            selectedQueue[position - 1] = null;
            System.out.println("Customer " + removedCustomer + " removed from the queue " + queueNumber + ".");
        }
    }
    //Removing Served Customer
    private static void removeServedCustomer() {
        String removedCustomer = null;

        for (int i = 0; i < queue1.length; i++) {
            if (queue1[i] != null) {
                removedCustomer = queue1[i];
                queue1[i] = null;
                break;
            }
        }

        if (removedCustomer == null) {
            for (int i = 0; i < queue2.length; i++) {
                if (queue2[i] != null) {
                    removedCustomer = queue2[i];
                    queue2[i] = null;
                    break;
                }
            }
        }

        if (removedCustomer == null) {
            for (int i = 0; i < queue3.length; i++) {
                if (queue3[i] != null) {
                    removedCustomer = queue3[i];
                    queue3[i] = null;
                    break;
                }
            }
        }

        if (removedCustomer != null) {
            System.out.println("Customer " + removedCustomer + " has been served and removed from the queue.");
        } else {
            System.out.println("No customers in any queue.");
        }
    }
    //Displaying Customers in alphabetical order
    private static void viewCustomersSorted() {
        String[] customers = new String[getTotalCustomers()];

        int index = 0;
        for (String customer : queue1) {
            if (customer != null) {
                customers[index] = customer;
                index++;
            }
        }

        for (String customer : queue2) {
            if (customer != null) {
                customers[index] = customer;
                index++;
            }
        }

        for (String customer : queue3) {
            if (customer != null) {
                customers[index] = customer;
                index++;
            }
        }

        sortCustomers(customers);

        System.out.println("Customers in alphabetical order:");
        for (String customer : customers) {
            System.out.println(customer);
        }
    }
    //Getting total number of Customers
    private static int getTotalCustomers() {
        int totalCustomers = 0;

        for (String customer : queue1) {
            if (customer != null) {
                totalCustomers++;
            }
        }

        for (String customer : queue2) {
            if (customer != null) {
                totalCustomers++;
            }
        }

        for (String customer : queue3) {
            if (customer != null) {
                totalCustomers++;
            }
        }

        return totalCustomers;
    }

    private static void sortCustomers(String[] customers) {
        for (int i = 0; i < customers.length - 1; i++) {
            for (int j = i + 1; j < customers.length; j++) {
                if (customers[i].compareTo(customers[j]) > 0) {
                    String temp = customers[i];
                    customers[i] = customers[j];
                    customers[j] = temp;
                }
            }
        }
    }
    //Store Program Data into text file
    private static void storeProgramData() {
        try {
            FileWriter writer = new FileWriter("queue.txt");
            saveQueueToFile(writer, 1, queue1);
            saveQueueToFile(writer, 2, queue2);
            saveQueueToFile(writer, 3, queue3);
            writer.close();
            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing program data: " + e.getMessage());
        }
    }

    private static void saveQueueToFile(FileWriter writer, int queueNumber, String[] queue) throws IOException {
        writer.write("Queue " + queueNumber + ":");
        if (!isQueueEmpty(queue)) {
            for (String customer : queue) {
                if (customer != null) {
                    writer.write(customer + ",");
                }
            }
            writer.write("\n");
        } else {
            writer.write("Empty\n");
        }
    }
    //Load Program Data from text file
    private static void loadProgramData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("queue.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            System.out.println("Program data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading program data: " + e.getMessage());
        }
    }

    //Displaying Remaining burgers Stock.
    private static void viewRemainingStock() {
        System.out.println("Remaining Burgers Stock: " + stock);
    }
    //Add burgers to Stock.
    private static void addToStock(Scanner scanner) {
        System.out.print("Enter the number of burgers to add: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        updateStock(quantity);
        System.out.println(quantity + " burgers added to the stock.");
    }
    //Updating Burgers in stock.
    private static void updateStock(int quantity) {
        stock += quantity;

        if (stock <= WARNING_THRESHOLD) {
            System.out.println("Warning: Remaining stock is low. Stock: " + stock);
        }
    }
}