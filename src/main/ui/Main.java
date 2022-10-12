package ui;

import model.ProductList;

import java.util.Scanner;

public class Main {

    private static final String REMOVE_CMD = "remove";
    private static final String TOTAL_CMD = "total";
    private static final String QUIT_CMD = "quit";

    private ProductList productList;
    private Scanner input;
    private boolean running;
    private String command;
    private int nextId;
    private int intInput;

    public Main() {
        System.out.println(" === WELCOME TO ____ STORE === ");

        handleUserCommand();
    }

    public void handleUserCommand() {
        input = new Scanner(System.in);
        running = true;

        while (running) {
            if (input.hasNext()) {
                command = input.nextLine();

                try { // is the user input actually an ID?
                    intInput = Integer.parseInt(command);
                    understandId(intInput);

                } catch (NumberFormatException e) { // otherwise, it is a command
                    understandCommand(command);
                }
            }
        }
    }

    // todo: implementation
    public void understandCommand(String command) {
        if (command.length() > 0) {
            if (command.equals(REMOVE_CMD)) {
                handleRemove();
            } else if (command.equals(TOTAL_CMD)) {
                handleTotal();
            } else if (command.equals(QUIT_CMD)) {
                running = false;
            } else {
                System.out.println("Invalid command, try again: ");
            }
        }
    }

    // todo: implement
    public void handleRemove() {
        //...
    }

    // todo: implement
    public void handleTotal() {
        // ...
    }

    // ToDo: implementation
    public void understandId(int id) {
        // ...
    }

    public static void main(String[] args) {
        new Main();
    }
}
