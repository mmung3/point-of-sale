package ui;

import model.Product;
import model.ProductList;
import model.Purchase;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// The main class for this project, handling the majority of actions that the user will see on the UI.
public class PointOfSale extends PointOfSaleTool {

    private static final String JSON_STORE = "./data/productList.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public PointOfSale() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        loadAllProductsList();
        welcomeMessage();
        handleUserCommand();
    }

    // EFFECTS: understands the users' input. Sends to understandId() or understandCommand() if
    //          it can be successfully converted to int (i.e. it is an ID). Currently, exact strings are required:
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
            if (command.equals(REMOVE_CMD)) {
                handleRemove();
            } else if (command.equals(HELP_CMD)) {
                printHelp();
            } else if (command.equals(TOTAL_CMD)) {
                handleTotal();
            } else if (command.equals(CATALOGUE_CMD)) {
                printCatalogue();
                printAfterActionMessage();
            } else if (command.equals(SAVE_CMD)) {
                handleSave();
            } else if (command.equals(LOAD_CMD)) {
                handleLoad();
            } else if (command.equals(QUIT_CMD)) {
                System.out.println("\n Quitting...");
                running = false;
            } else {
                System.out.println("\n\t Invalid command, try again: ");
            }
        }
    }

    // MODIFIES: userProductsList
    // EFFECTS: looks for a given ID within the master list of IDs. If found, adds the Product associated
    //          with the ID to userProductsList, otherwise asks for a correct ID or command
    public void understandId(int userId) {
        Product productToAdd;
        boolean foundItem = false;

        ArrayList<Integer> allProductsIdOnly = allProductsList.getIdList();

        for (int itemId : allProductsIdOnly) { // search the list of IDs for the ID the user gives
            if (userId == itemId) { // i.e. does the ID the user gave match one in the full catalogue
                foundItem = true;
                int index = allProductsIdOnly.indexOf(itemId);
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

    // MODIFIES: userProductList
    // EFFECTS: if the user enters remove mode, will remove the product from the product list based off the id
    public void handleRemove() {
        boolean foundItem = false;

        System.out.println("\n[REMOVE MODE] Enter the ID of an item you wish to remove: ");
        String potentialValidId = handleInputScanner();

        try { // try converting the user input to an int if it is an ID
            int userIdToRemove = Integer.parseInt(potentialValidId);

            // Begin removal function if try was successful
            ArrayList<Integer> allUserIdOnly = userProductList.getIdList();

            for (int itemId : allUserIdOnly) { // search the list of IDs for the ID the user gives
                if (userIdToRemove == itemId) {
                    foundItem = true;
                    int index = allUserIdOnly.indexOf(itemId);
                    Product productToRemove = userProductList.getProductFromIdIndex(index);
                    userProductList.removeProduct(productToRemove);

                    printRemoveSuccessMessage(productToRemove);
                    break;
                }
            }
            if (!foundItem) { // could not find the ID
                printRemoveFailureMessage(userIdToRemove);
            }
        } catch (NumberFormatException e) { // exit remove mode upon failure
            System.out.println("\n\tCould not find any item with that ID, leaving [REMOVE MODE]");
        }
        printAfterActionMessage();
    }

    // EFFECTS: gives the total, asking the user their payment method. Sends off to the appropriate method as required.
    public void handleTotal() {
        Purchase thisPurchase;

        printTotalIntroMessage(getUserTotal());
        String userCashCardChoice = handleInputScanner();

        while (running) {
            if (userCashCardChoice.equals("cash")) {
                thisPurchase = new Purchase(getUserTotal(), false);
                handleCash(thisPurchase);
                running = false;
                break;
            } else if (userCashCardChoice.equals("card")) {
                thisPurchase = new Purchase(getUserTotal(), true);
                handleCard(thisPurchase);
                running = false;
                break;
            } else if (userCashCardChoice.equals("back")) {
                handleUserCommand();
            } else {
                System.out.println("\n\t Invalid selection, try again: ");
                handleTotal();
            }
        }
    }

    // EFFECTS: handles payment if the user has decided to pay with cash. Will accept payment and print the change
    //          for the customer as required, ending the program. If the amount provided is insufficient, the
    //          user is returned to the main menu and prompted to remove items and re-enter the total screen.
    public void handleCash(Purchase pch) {
        double totalCash = pch.getAmount();

        printCashIntroMessage(totalCash);
        String potentialCustomerAmount = handleInputScanner();

        if (potentialCustomerAmount.equals("back")) {
            handleTotal();
        }

        try { // try converting the user input to a double
            double customerAmount = Double.parseDouble(potentialCustomerAmount);
            double change = pch.calculateChange(customerAmount);

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


    // EFFECTS: handles payment if the user decided to pay with card. If the card number they give is validly formatted,
    //          will then as for a validly formatted PIN, sending to handlePin().
    public void handleCard(Purchase pch) {
        System.out.println("\nPlease enter your card number (12 digits) or enter "
                + "\"back\" to return to the previous menu:");
        String customerCardNumber = handleInputScanner();

        if (customerCardNumber.length() == 12) {
            handlePin(pch);
        } else if (customerCardNumber.equals("back")) {
            handleTotal();
        } else {
            System.out.println("\n\tThe card number you provided is invalidly formatted.");
            System.out.println("\t(" + customerCardNumber.length() + " provided, 12 needed)");
            System.out.println("\tYou will be returned to the previous step.");
            handleCard(pch);
        }
    }

    // EFFECTS: asks the user for a PIN, ending the program and printing a success message if the PIN provided is
    //          validly formatted.
    public void handlePin(Purchase pch) {
        System.out.println("\nPlease enter your PIN (4 digits) or \"back\":");
        String customerCardPin = handleInputScanner();

        if (customerCardPin.equals("back")) {
            handleCard(pch);
        } else if (customerCardPin.length() == 4) {
            printPinSuccessMessage(pch);
        } else {
            printPinFailureMessage();
            handlePin(pch);
        }
    }

    // MODIFIES: allProductsList
    // EFFECTS: Loads all products to the master list. Can be printed to terminal using printCatalogue().
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
        // This method likely struggles for a large list but works for a small ProductList
    }


    // JSON CONTENT ===

    // EFFECTS: saves the workroom to file
    private void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(userProductList);
            jsonWriter.close();
            System.out.println("\n\tSaved your list of products to " + JSON_STORE);
            printAfterActionMessage();
        } catch (FileNotFoundException e) {
            System.out.println("\n\tUnable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void handleLoad() {
        try {
            userProductList = jsonReader.read();
            System.out.println("\n\tLoaded your list of products from " + JSON_STORE);
            printOrderSoFarMessage();
        } catch (IOException e) {
            System.out.println("\n\tUnable to read from file: " + JSON_STORE);
        }
    }

    public static void main(String[] args) {
        new PointOfSale();
    }
}
