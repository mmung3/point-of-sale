package ui;

import model.Product;
import model.ProductList;
import model.Purchase;

import java.text.DecimalFormat;
import java.util.Scanner;

//todo: general documentation
public class PointOfSaleTool {

    // constants made public to allow implementation across both UI classes
    protected static final String REMOVE_CMD = "remove";
    protected static final String TOTAL_CMD = "total";
    protected static final String QUIT_CMD = "quit";
    protected static final String HELP_CMD = "help";

    protected static final DecimalFormat df = new DecimalFormat("0.00");

    protected ProductList allProductsList;
    protected boolean running;
    protected ProductList userProductList;

    protected double userTotal;


    public PointOfSaleTool() {
        running = true;
        userProductList = new ProductList();
        userTotal = userProductList.getTotal();
    }

    // EFFECTS: prints the welcome message
    protected void welcomeMessage() {
        System.out.println(" ==== WELCOME TO ____ STORE ==== ");
    }

    // EFFECTS: reads user input using the Scanner. Returns userInputString in String form.
    protected String handleInputScanner() {
        String userInputString;

        Scanner input = new Scanner(System.in);

        userInputString = input.nextLine();
        return userInputString;
    }

    protected void printAddSuccessMessage(Product productToAdd) {
        System.out.println("You entered: " + productToAdd.getName() + ", $"
                + df.format(productToAdd.getPrice()));
        System.out.println("Your order so far: " + userProductList.getNameList()
                + " | Total: $" + df.format(userProductList.getTotal()));
    }

    protected void printRemoveSuccessMessage(Product productToRemove) {
        System.out.println("Removed: " + productToRemove.getName() + ", $"
                + df.format(productToRemove.getPrice()));
        System.out.println("Your order so far: " + userProductList.getNameList()
                + " | Total: $" + df.format(userProductList.getTotal()));
    }

    protected void printTotalIntroMessage(double userTotal) {
        System.out.println("\n==== TOTAL ==== ");
        System.out.println("Your total cost is: $" + df.format(userTotal) + ".");
        System.out.println("Would you like to pay by CASH or CARD today?");
        System.out.println("Enter \"cash\" or \"card\" below:");
    }

    protected void printCashIntroMessage(Purchase pch) {
        System.out.println("\nYour total: $" + df.format(pch.getAmount()) + ".");
        System.out.println("Please enter an amount: ");
    }

    // REQUIRES: customerAmount, change >= 0
    // EFFECTS: prints out the designated message if a cash purchase involving change is successful
    protected void printChangeSuccessMessage(Purchase pch, double customerAmount, double change) {
        System.out.println("\n================================================");
        System.out.println("TOTAL: $" + df.format(pch.getAmount()));
        System.out.println("AMOUNT PAID: $" + df.format(customerAmount));
        System.out.println("CHANGE: $" + df.format(change));
        System.out.println("================================================\n");
        System.out.println("THANK YOU FOR SHOPPING!");
    }

    // REQUIRES: customerAmount >= 0
    // EFFECTS: prints out the designated message if a cash purchase involving change fails.
    protected void printChangeFailureMessage(Purchase pch, double customerAmount) {
        double shortAmount = (pch.getAmount() - customerAmount);

        System.out.println("\nThe amount you provided is insufficient. Review the details below:");
        System.out.println("\tTOTAL: $" + df.format(pch.getAmount()));
        System.out.println("\tAMOUNT: $" + df.format(customerAmount));
        System.out.println("\tSHORT: $" + df.format(shortAmount));
        System.out.println("\nYou will be returned to your order.");
        System.out.println("Enter \"remove\" to remove unwanted items, "
                + "or \"total\" to return to the total screen");
    }

    // EFFECTS: prints out the success message for a purchase involving a card
    protected void printPinSuccessMessage(Purchase pch) {
        System.out.println("\n================================================");
        System.out.println("TOTAL: $" + pch.getAmount());
        System.out.println("AMOUNT CHARGED: $" + pch.getAmount());
        System.out.println("================================================\n");
        System.out.println("THANK YOU FOR SHOPPING");
    }

    // EFFECTS: prints out the failure message for a purchase involving a card
    protected void printPinFailureMessage() {
        System.out.println("\n\tThe PIN you provided is invalidly formatted.");
        System.out.println("\tYou will be returned to the previous step.");
    }

    // EFFECTS: prints the help message if a user requests it
    protected void printHelp() {
        System.out.println("\t Type \"" + HELP_CMD + "\" for a list of commands.");
        System.out.println("\t Type any valid 4-digit ID to add the item to your order.");
        System.out.println("\t Type \"" + REMOVE_CMD + "\" to remove items from your order.");
        System.out.println("\t Type \"" + TOTAL_CMD + "\" to finalize your order.");
        System.out.println("\t Type \"" + QUIT_CMD + "\" to quit.\n");
    }

    public void printAfterActionMessage() {
        System.out.println("\n --- Please enter a command, 4-digit ID, or type \"help\" to continue: ---");
    }


}
