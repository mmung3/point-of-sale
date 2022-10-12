package ui;

import model.Product;
import model.ProductList;
import model.Purchase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String REMOVE_CMD = "remove";
    private static final String TOTAL_CMD = "total";
    private static final String QUIT_CMD = "quit";
    private static final String HELP_CMD = "help";
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private ProductList allProductsList;
    private boolean running;

    ProductList userProductList = new ProductList();


    public Main() {
        loadAllProductsList();

        System.out.println(" ==== WELCOME TO ____ STORE ==== ");

        handleUserCommand();
    }

    // todo: convert badly formatted but valid strings
    // EFFECTS: understands the users' input. Sends to understandId() or understandCommand() if
    //          it can be successfully converted to int (i.e. it is an ID)
    public void handleUserCommand() {
        Scanner input;
        String command;
        int intInput;

        input = new Scanner(System.in);
        running = true;

        printAfterActionMessage();

        while (running) {
            if (input.hasNext()) {
                command = input.nextLine();

                try { // try converting the user input to an int if it is an ID
                    intInput = Integer.parseInt(command);
                    understandId(intInput);

                } catch (NumberFormatException e) { // otherwise, it is a command
                    understandCommand(command);
                }
            }
        }
    }

    // EFFECTS: handles the command given, distributing to helper functions as necessary
    public void understandCommand(String command) {
        if (command.length() > 0) {
            if (command.equals(REMOVE_CMD)) {
                handleRemove();
            } else if (command.equals(HELP_CMD)) {
                printHelp();
            } else if (command.equals(TOTAL_CMD)) {
                handleTotal();
            } else if (command.equals(QUIT_CMD)) {
                System.out.println("Quitting...");
                running = false;
            } else {
                System.out.println("\n\t Invalid command, try again: ");
            }
        }
    }

    // todo: ensure 25 cap is not reached
    // MODIFIES: userProductsList
    // EFFECTS: looks for a given ID within the master list of IDs. If found, adds the Product associated
    //          with the ID to userProductsList, otherwise asks for a correct ID or command
    public void understandId(int userId) {
        int index;
        Product productToAdd;
        boolean foundItem = false;

        ArrayList<Integer> allProductsIdOnly = allProductsList.getIdList();

        for (int itemId : allProductsIdOnly) { // search the list of IDs for the ID the user gives
            if (userId == itemId) {
                foundItem = true;
                index = allProductsIdOnly.indexOf(itemId);
                productToAdd = allProductsList.getProductFromIdIndex(index);
                userProductList.addProduct(productToAdd);

                System.out.println("You entered: " + productToAdd.getName() + ", $"
                        + df.format(productToAdd.getPrice()));
                System.out.println("Your order so far: " + userProductList.getNameList()
                        + " | Total: $" + df.format(userProductList.getTotal()));
            }
        }
        if (!foundItem) { // could not find the ID
            System.out.println("\n\t Invalid ID, try again: ");
        }
        printAfterActionMessage();
    }

    // todo: consider abstracting the scanner and removal function. See understandId() for more
    // MODIFIES: userProductList
    // EFFECTS: if the user enters remove mode, will as for the id
    public void handleRemove() {
        boolean foundItem = false;
        int index;
        int userIdToRemove;
        Product productToRemove;

        // Scanner code
        Scanner input = new Scanner(System.in);
        System.out.println("\n[REMOVE MODE] Enter the ID of an item you wish to remove: ");
        String potentialValidId = input.nextLine();

        try { // try converting the user input to an int if it is an ID
            userIdToRemove = Integer.parseInt(potentialValidId);

            // Begin removal function if try was successful
            ArrayList<Integer> allUserIdOnly = userProductList.getIdList();

            for (int itemId : allUserIdOnly) { // search the list of IDs for the ID the user gives
                if (userIdToRemove == itemId) {
                    foundItem = true;
                    index = allUserIdOnly.indexOf(itemId);
                    productToRemove = userProductList.getProductFromIdIndex(index);
                    userProductList.removeProduct(productToRemove);

                    System.out.println("Removed: " + productToRemove.getName() + ", $"
                            + df.format(productToRemove.getPrice()));
                    System.out.println("Your order so far: " + userProductList.getNameList()
                            + " | Total: $" + df.format(userProductList.getTotal()));
                    break;
                }
            }
            if (!foundItem) { // could not find the ID
                System.out.println("\n\t Could not find: \"" + userIdToRemove + "\", leaving [REMOVE MODE]");
            }
        } catch (NumberFormatException e) { // otherwise, it is a command
            System.out.println("\n\t Invalid ID, try again: ");
        }
        printAfterActionMessage();
    }

    // EFFECTS: gives the total, asking the user their payment method. Sends off to the appropriate method as required.
    // todo: room for optimization
    public void handleTotal() {
        double userTotal;
        userTotal = userProductList.getTotal();
        Purchase thisPurchase;
        running = true;
        String userCashCardChoice;

        System.out.println("\n==== TOTAL ==== ");
        System.out.println("Your total cost is: $" + df.format(userTotal) + ".");
        System.out.println("Would you like to pay by CASH or CARD today?");
        System.out.println("Enter \"cash\" or \"card\" below:");

        Scanner input = new Scanner(System.in);
        userCashCardChoice = input.nextLine();

        while (running) {
            if (userCashCardChoice.equals("cash")) {
                thisPurchase = new Purchase(userTotal, false);
                handleCash(thisPurchase);
                running = false;
                break;
            } else if (userCashCardChoice.equals("card")) {
                thisPurchase = new Purchase(userTotal, true);
                handleCard(thisPurchase);
                running = false;
                break;
            } else {
                System.out.println("\n\t Invalid selection, try again: ");
                handleTotal();
            }
        }
    }

    // todo: remove redundant code
    public void handleCash(Purchase pch) {
        String potentialCustomerAmount;
        double customerAmount;
        double change;

        Scanner input = new Scanner(System.in);
        System.out.println("\nYour total: $" + df.format(pch.getAmount()) + ".");
        System.out.println("Please enter an amount: ");
        potentialCustomerAmount = input.nextLine();

        try { // try converting the user input to a double
            customerAmount = Integer.parseInt(potentialCustomerAmount);
            change = pch.calculateChange(customerAmount);

            if (change >= 0) {
                System.out.println("\n================================================");
                System.out.println("TOTAL: $" + df.format(pch.getAmount()));
                System.out.println("AMOUNT: $" + df.format(customerAmount));
                System.out.println("CHANGE: $" + df.format(change));
                System.out.println("================================================\n");
                System.out.println("THANK YOU FOR SHOPPING");
            } else if (change < 0) {
                double shortAmount = (pch.getAmount() - customerAmount);

                System.out.println("\nThe amount you provided is insufficient. Review the details below:");
                System.out.println("\tTOTAL: $" + df.format(pch.getAmount()));
                System.out.println("\tAMOUNT: $" + df.format(customerAmount));
                System.out.println("\tSHORT: $" + df.format(shortAmount));
                System.out.println("\nYou will be returned to your order.");
                System.out.println("Enter \"remove\" to remove unwanted items, "
                        + "or \"total\" to return to the total screen");
                handleUserCommand();
            } else {
                System.out.println("Unexpected Error");
            }

        } catch (NumberFormatException e) { // user did not provide a valid amount
            System.out.println("\n\tInvalid amount, try again: ");
            handleCash(pch);
        }
    }

    // todo: massively room to remove redundancy here
    public void handleCard(Purchase pch) {
        String customerCardNumber;

        Scanner inputCardNumber = new Scanner(System.in);
        System.out.println("\nPlease enter your card number (12 digits): ");
        customerCardNumber = inputCardNumber.nextLine();

        if (customerCardNumber.length() == 12) {
            String customerCardPin;

            Scanner inputCardPin = new Scanner(System.in);
            System.out.println("\nPlease enter your PIN (4 digits): ");
            customerCardPin = inputCardPin.nextLine();

            if (customerCardPin.length() == 4) {
                System.out.println("\n================================================");
                System.out.println("TOTAL: $" + pch.getAmount());
                System.out.println("AMOUNT CHARGED: $" + pch.getAmount());
                System.out.println("================================================\n");
                System.out.println("THANK YOU FOR SHOPPING");
            } else {
                System.out.println("\n\tThe PIN you provided is invalidly formatted.");
                System.out.println("\tYou will be returned to the previous step.");
                handleCard(pch);
            }
        } else {
            System.out.println("\n\tThe card number you provided is invalidly formatted.");
            System.out.println("\tYou will be returned to the previous step.");
            handleCard(pch);
        }
    }

    // EFFECTS: prints the help message if a user requests it
    public void printHelp() {
        System.out.println("\t Type \"" + HELP_CMD + "\" for a list of commands.");
        System.out.println("\t Type any valid 4-digit ID to add the item to your order.");
        System.out.println("\t Type \"" + REMOVE_CMD + "\" to remove items from your order.");
        System.out.println("\t Type \"" + TOTAL_CMD + "\" to finalize your order.");
        System.out.println("\t Type \"" + QUIT_CMD + "\" to quit.\n");
    }

    public void printAfterActionMessage() {
        System.out.println("\n --- Please enter a command, 4-digit ID, or type \"help\" to continue: ---");
    }

    public void loadAllProductsList() {
        Product apples = new Product("apples", 2000, 2.00);
        Product oranges = new Product("oranges", 2001, 3.00);
        Product pears = new Product("pears", 2002, 4.50);
        Product banana = new Product("banana", 2003, 5.75);
        Product watermelon = new Product("watermelon", 2004, 14.00);
        Product paperBag = new Product("paper bag", 1001, 0.10);
        Product canvasBag = new Product("canvas bag", 1002, 0.25);

        allProductsList = new ProductList();

        allProductsList.addProduct(apples);
        allProductsList.addProduct(oranges);
        allProductsList.addProduct(pears);
        allProductsList.addProduct(banana);
        allProductsList.addProduct(watermelon);
        allProductsList.addProduct(paperBag);
        allProductsList.addProduct(canvasBag);

        // This method likely has awful O(n) but works for a small ProductList
    }

    public static void main(String[] args) {
        new Main();
    }
}
