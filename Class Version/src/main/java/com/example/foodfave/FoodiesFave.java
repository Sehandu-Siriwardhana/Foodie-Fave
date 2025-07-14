package com.example.foodfave;

import java.util.*;
import java.util.Scanner;

class FoodiesFave {
    public static final int MAX_STOCK = 50;
    public static final int WARNING_THRESHOLD = 10;
    public static final int[] QUEUE_CAPACITIES = {2, 3, 5};
    public static final String[] CASHIER_NAMES = {"Cashier 1", "Cashier 2", "Cashier 3"};
    public static final double BURGER_PRICE = 650;

    static int stock = MAX_STOCK;
    static List<FoodQueue> queues = new ArrayList<>();
    static Queue<Customer> waitingList = new LinkedList<>();

    public static void main(String[] args) {
        initializeQueues();
        displayMenu();
    }

    private static void initializeQueues() {
        for (int i = 0; i < CASHIER_NAMES.length; i++) {
            queues.add(new FoodQueue(QUEUE_CAPACITIES[i]));
        }
    }

    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===================================");
            System.out.println("Burger Queue Management System Menu");
            System.out.println("===================================");
            System.out.println("100 or VFQ: View all Queues");
            System.out.println("101 or VEQ: View all Empty Queues");
            System.out.println("102 or ACQ: Add customer to a Queue");
            System.out.println("103 or RCQ: Remove a customer from a Queue");
            System.out.println("104 or PCQ: Remove a served customer");
            System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
            System.out.println("106 or SPD: Store Program Data into file");
            System.out.println("107 or LPD: Load Program Data from file");
            System.out.println("108 or STK: View Remaining burgers Stock");
            System.out.println("109 or AFS: Add burgers to Stock");
            System.out.println("110 or IFQ: Income of each queue");
            System.out.println("112 or GUI: Display GUI");
            System.out.println("999 or EXT: Exit the Program");
            System.out.println("===================================");

            System.out.print("Enter your choice: ");
            String choice = scanner.next();

            switch (choice) {
                case "100":
                case "VFQ":
                    FoodQueue.viewAllQueues();
                    break;
                case "101":
                case "VEQ":
                    FoodQueue.viewAllEmptyQueues();
                    break;
                case "102":
                case "ACQ":
                    FoodQueue.addCustomerToQueue(scanner);
                    break;
                case "103":
                case "RCQ":
                    FoodQueue.removeCustomerFromQueue(scanner);
                    break;
                case "104":
                case "PCQ":
                    FoodQueue.removeServedCustomer();
                    break;
                case "105":
                case "VCS":
                    FoodQueue.viewCustomersSorted();
                    break;
                case "106":
                case "SPD":
                    FoodQueue.storeProgramData();
                    break;
                case "107":
                case "LPD":
                    FoodQueue.loadProgramData();
                    break;
                case "108":
                case "STK":
                    FoodQueue.viewRemainingBurgerStock();
                    break;
                case "109":
                case "AFS":
                    FoodQueue.addBurgersToStock(scanner);
                    break;
                case "110":
                case "IFQ":
                    FoodQueue.viewQueueIncome();
                    break;
                case "112":
                case "GUI":
                    HelloApplication.launch(HelloApplication.class);
                    break;
                case "999":
                case "EXT":
                    FoodQueue.exitProgram();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}