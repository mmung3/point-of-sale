package ui;

import model.Product;
import model.ProductList;
import model.Purchase;

import java.util.ArrayList;

public class PointOfSale extends PointOfSaleTool {

    public PointOfSale() {
        loadAllProductsList();

        welcomeMessage();

        handleUserCommand();
    }

    // todo: convert badly formatted but valid strings
    // EFFECTS: understands the users' input. Sends to understandId() or understandCommand() if
    //          it can be successfully converted to int (i.e. it is an ID). Currently is caps sensitive
    //          (ex. 'Remove', 'toTAL', '1 001' are invalid [should be 'remove', 'total', or '1001'])
    public void handleUserCommand() {
        printAfterActionMessage();

        while (running) {
            String command = handleInputScanner();

            try { // try converting the user input to an int if it is an ID
                int intInput = Integer.parseInt(command);
                understandId(intInput);

            } catch (NumberFormatException e) { // otherwise, it is a command
                understandCommand(command);
            }
        }
    }

    // EFFECTS: handles the command given, distributing to helper functions as necessary
    public void understandCommand(String command) {
        if (command.length() > 0) {
            switch (command) {
                case REMOVE_CMD:
                    handleRemove();
                    break;
                case HELP_CMD:
                    printHelp();
                    break;
                case TOTAL_CMD:
                    handleTotal();
                    break;
                case QUIT_CMD:
                    System.out.println("\nQuitting...");
                    running = false;
                    break;
                default:
                    System.out.println("\n\t Invalid command, try again: ");
                    break;
            }
        }
    }

    // MODIFIES: userProductsList
    // EFFECTS: looks for a given ID within the master list of IDs. If found, adds the Product associated
    //          with the ID to userProductsList, otherwise asks for a correct ID or command
    public void understandId(int userId) {
        int index;
        Product productToAdd;
        boolean foundItem = false;

        ArrayList<Integer> allProductsIdOnly = allProductsList.getIdList();

        for (int itemId : allProductsIdOnly) { // search the list of IDs for the ID the user gives
            if (userId == itemId) { // i.e. does the ID the user gave match one in the full catalogue
                foundItem = true;
                index = allProductsIdOnly.indexOf(itemId);
                productToAdd = allProductsList.getProductFromIdIndex(index);
                userProductList.addProduct(productToAdd);

                printAddSuccessMessage(productToAdd);
            }
        }
        if (!foundItem) { // could not find the ID
            System.out.println("\n\t Invalid ID, try again: ");
        }
        printAfterActionMessage();
    }

    // todo: consider abstracting the scanner and removal function. See understandId() for more
    // MODIFIES: userProductList
    // EFFECTS: if the user enters remove mode, will remove the product from the product list based off the id
    public void handleRemove() {
        boolean foundItem = false;
        int index;
        int userIdToRemove;
        Product productToRemove;

        System.out.println("\n[REMOVE MODE] Enter the ID of an item you wish to remove: ");
        String potentialValidId = handleInputScanner();

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

                    printRemoveSuccessMessage(productToRemove);
                }
            }
            if (!foundItem) { // could not find the ID
                System.out.println("\n\t Could not find any item with ID \""
                        + userIdToRemove + "\", leaving [REMOVE MODE]");
            }
        } catch (NumberFormatException e) { // otherwise, it is a command
            System.out.println("\n\t Invalid ID, try again: ");
        }
        printAfterActionMessage();
    }

    // EFFECTS: gives the total, asking the user their payment method. Sends off to the appropriate method as required.
    public void handleTotal() {
        Purchase thisPurchase;

        printTotalIntroMessage(userTotal);
        String userCashCardChoice = handleInputScanner();

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

    // todo: remove redundant code, documentation
    public void handleCash(Purchase pch) {
        double customerAmount;
        double change;

        printCashIntroMessage(pch);
        String potentialCustomerAmount = handleInputScanner();

        try { // try converting the user input to a double
            customerAmount = Integer.parseInt(potentialCustomerAmount);
            change = pch.calculateChange(customerAmount);

            if (change >= 0) {
                printChangeSuccessMessage(pch, customerAmount, change);
            } else if (change < 0) {
                printChangeFailureMessage(pch, customerAmount);
                handleUserCommand();
            } else {
                System.out.println("Unexpected Error");
            }

        } catch (NumberFormatException e) { // user did not provide a valid amount
            System.out.println("\n\tInvalid amount, try again: ");
            handleCash(pch);
        }
    }


    // todo: massively room to remove redundancy here, documentation
    public void handleCard(Purchase pch) {
        String customerCardNumber = handleInputScanner();

        System.out.println("\nPlease enter your card number (12 digits): ");

        if (customerCardNumber.length() == 12) {
            System.out.println("\nPlease enter your PIN (4 digits): ");
            String customerCardPin = handleInputScanner();

            if (customerCardPin.length() == 4) {
                printPinSuccessMessage(pch);
            } else {
                printPinFailureMessage();
                handleCard(pch);
            }
        } else {
            System.out.println("\n\tThe card number you provided is invalidly formatted.");
            System.out.println("\tYou will be returned to the previous step.");
            handleCard(pch);
        }
    }

    // todo: docs
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
        new PointOfSale();
    }
}
