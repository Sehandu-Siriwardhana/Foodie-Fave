package com.example.foodfave;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
class FoodQueue {
    private int capacity;
    private List<Customer> customers;

    public FoodQueue(int capacity) {
        this.capacity = capacity;
        customers = new ArrayList<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCustomerCount() {
        return customers.size();
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }

    public String getName() {
        // Assuming the queue name is based on its index in the queues list of FoodiesFave class
        int index = getQueues().indexOf(this);
        return FoodiesFave.CASHIER_NAMES[index];
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer removeCustomer(int index) {
        return customers.remove(index);
    }

    public double getTotalIncome() {
        double income = 0;
        for (Customer customer : customers) {
            income += customer.getBurgersRequired() * FoodiesFave.BURGER_PRICE;
        }
        return income;
    }
    static void viewAllQueues() {
        System.out.println("\nAll Queues");
        System.out.println("============");

        System.out.println("FOOD CENTER QUEUE ");
        System.out.println("==================");
        System.out.println("X       X        X");
        System.out.println("X       X        X");
        System.out.println("        X        X");
        System.out.println("                 X");
        System.out.println("                 X");
        System.out.println("X - Not Occupied, O - Occupied");

        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");




        int maxLength = Math.max(FoodiesFave.queues.get(0).getCustomerCount(), Math.max(FoodiesFave.queues.get(1).getCustomerCount(), FoodiesFave.queues.get(2).getCustomerCount()));

        for (int i = 0; i < maxLength; i++) {
            if (i < FoodiesFave.queues.get(0).getCustomerCount()) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }
            System.out.print("\t\t");

            if (i < FoodiesFave.queues.get(1).getCustomerCount()) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }
            System.out.print("\t\t");

            if (i < FoodiesFave.queues.get(2).getCustomerCount()) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }

            System.out.println();
        }

    }

    static void viewAllEmptyQueues() {
        System.out.println("\nEmpty Queues");
        System.out.println("============");

        System.out.println("Empty Queues:");
        for (int i = 0; i < FoodiesFave.queues.size(); i++) {
            FoodQueue queue = FoodiesFave.queues.get(i);
            if (queue.getCustomerCount() == 0) {
                System.out.println(FoodiesFave.CASHIER_NAMES[i]);
            }
        }
    }

    static void addCustomerToQueue(Scanner scanner) {
        System.out.println("\nAdd Customer");
        System.out.println("============");

        int shortestQueueIndex = getShortestQueueIndex();
        if (shortestQueueIndex == -1) {
            System.out.println("All queues are full! Adding customer to the waiting list.");
            addCustomerToWaitingList(scanner);
            return;
        }

        System.out.print("Enter customer's first name: ");
        String firstName = scanner.next();
        System.out.print("Enter customer's last name: ");
        String lastName = scanner.next();
        System.out.print("Enter the number of burgers required: ");
        int burgersRequired = scanner.nextInt();

        if (FoodiesFave.stock < FoodiesFave.WARNING_THRESHOLD) {
            System.out.println("Warning: Burger stock is low!");
        }

        Customer customer = new Customer(firstName, lastName, burgersRequired);
        FoodiesFave.queues.get(shortestQueueIndex).addCustomer(customer);
        FoodiesFave.stock -= burgersRequired;
        System.out.println("Customer added to " + FoodiesFave.CASHIER_NAMES[shortestQueueIndex] + " queue.");
    }

    private static int getShortestQueueIndex() {
        int shortestQueueIndex = -1;
        int shortestQueueSize = Integer.MAX_VALUE;

        for (int i = 0; i < FoodiesFave.queues.size(); i++) {
            FoodQueue queue = FoodiesFave.queues.get(i);
            if (queue.getCustomerCount() < queue.getCapacity() && queue.getCustomerCount() < shortestQueueSize) {
                shortestQueueIndex = i;
                shortestQueueSize = queue.getCustomerCount();
            }
        }

        return shortestQueueIndex;
    }
    private static void addCustomerToWaitingList(Scanner scanner) {
        System.out.print("Enter customer's first name: ");
        String firstName = scanner.next();
        System.out.print("Enter customer's last name: ");
        String lastName = scanner.next();
        System.out.print("Enter the number of burgers required: ");
        int burgersRequired = scanner.nextInt();

        if (FoodiesFave.stock < FoodiesFave.WARNING_THRESHOLD) {
            System.out.println("Warning: Burger stock is low!");
        }

        Customer customer = new Customer(firstName, lastName, burgersRequired);
        FoodiesFave.waitingList.add(customer);
        FoodiesFave.stock -= burgersRequired;
        System.out.println("Customer added to the waiting list.");
    }
    static void removeCustomerFromQueue(Scanner scanner) {
        System.out.println("\nRemove Customer");
        System.out.println("===============");

        System.out.print("Enter cashier index (0-2): ");
        int cashierIndex = scanner.nextInt();

        if (cashierIndex < 0 || cashierIndex >= FoodiesFave.queues.size()) {
            System.out.println("Invalid cashier index!");
            return;
        }

        FoodQueue queue = FoodiesFave.queues.get(cashierIndex);
        if (queue.getCustomerCount() == 0) {
            System.out.println(FoodiesFave.CASHIER_NAMES[cashierIndex] + " queue is empty.");
        } else {
            System.out.print("Enter customer index (0-" + (queue.getCustomerCount() - 1) + "): ");
            int customerIndex = scanner.nextInt();

            if (customerIndex < 0 || customerIndex >= queue.getCustomerCount()) {
                System.out.println("Invalid customer index!");
                return;
            }

            Customer removedCustomer = queue.removeCustomer(customerIndex);
            System.out.println("Removed customer '" + removedCustomer.getFullName() + "' from " + FoodiesFave.CASHIER_NAMES[cashierIndex] + " queue.");
        }
    }

    static void removeServedCustomer() {
        System.out.println("\nRemove Served Customer");
        System.out.println("======================");

        boolean customerRemoved = false;

        for (FoodQueue queue : FoodiesFave.queues) {
            if (!queue.isEmpty()) {
                Customer removedCustomer = queue.removeCustomer(0);
                System.out.println("Removed served customer '" + removedCustomer.getFullName() + "'.");
                customerRemoved = true;
                if (!FoodiesFave.waitingList.isEmpty()) {
                    Customer nextCustomer = FoodiesFave.waitingList.poll();
                    queue.addCustomer(nextCustomer);
                    System.out.println("Moved customer '" + nextCustomer.getFullName() + "' from waiting list to " + FoodiesFave.CASHIER_NAMES[FoodiesFave.queues.indexOf(queue)] + " queue.");
                }
                break;
            }
        }

        if (!customerRemoved) {
            System.out.println("No served customers found in the queues.");
        }
    }


    static void viewCustomersSorted() {
        System.out.println("\nView Customers Sorted in alphabetical order");
        System.out.println("===========================================");

        List<Customer> customers = new ArrayList<>();
        for (FoodQueue queue : FoodiesFave.queues) {
            customers.addAll(queue.getCustomers());
        }

        customers.sort((c1, c2) -> c1.getFullName().compareToIgnoreCase(c2.getFullName()));

        System.out.println("Customers Sorted in alphabetical order:");
        for (Customer customer : customers) {
            System.out.println(customer.getFullName());
        }
    }

    static void storeProgramData() {
        System.out.println("\nStore Program Data");
        System.out.println("==================");
        try {
            FileWriter writer = new FileWriter("customer_data.txt");

            for (FoodQueue queue : FoodiesFave.queues) {
                for (Customer customer : queue.getCustomers()) {

                    String data =  "Customer" + " " + customer.getFullName()  +" "+ "Burgers Required: " + customer.getBurgersRequired() + "\n";
                    writer.write(data);
                }
            }

            writer.close();
            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing program data: " + e.getMessage());
        }
    }
    static void loadProgramData() {
        System.out.println("\nLoad Program Data");
        System.out.println("=================");

        try {
            BufferedReader reader = new BufferedReader(new FileReader("customer_data.txt"));
            String line;

            System.out.println("Loaded program data:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading program data: " + e.getMessage());
        }
    }

    static void viewRemainingBurgerStock() {
        System.out.println("\nView Remaining burgers Stock");
        System.out.println("============================");
        System.out.println("Remaining burger stock: " + FoodiesFave.stock);
    }

    static void addBurgersToStock(Scanner scanner) {
        System.out.println("\nAdd Burgers to Stock");
        System.out.println("====================");

        System.out.print("Enter number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();

        if (FoodiesFave.stock + burgersToAdd > FoodiesFave.MAX_STOCK) {
            System.out.println("Cannot add more burgers. Stock capacity exceeded.");
        } else {
            FoodiesFave.stock += burgersToAdd;
            System.out.println("Burgers added to stock. Remaining stock: " + FoodiesFave.stock);
        }
    }

    static void viewQueueIncome() {
        System.out.println("\nIncome per Queue");
        System.out.println("================");

        System.out.println("Queue Incomes:");
        for (FoodQueue queue : FoodiesFave.queues) {
            double income = queue.getTotalIncome();
            System.out.println(queue.getName() + ": " + income);
        }
    }

    static void exitProgram() {
        System.out.println("\nExiting Program");
        System.out.println("===============");

        System.out.println("Exiting the program...");
        // Implement any necessary cleanup or save operations before exiting
    }

    public static List<FoodQueue> getQueues() {
        return FoodiesFave.queues;
    }
}


