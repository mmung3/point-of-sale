package ui;

import model.ProductList;
import model.Product;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

// Class which contains methods, fields, etc. to run the Graphical User Interface version of PointOfSale
public class PointOfSaleGUI extends JFrame {

    private static final int WIDTH = 850;
    private static final int HEIGHT = 600;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private final Product apples = new Product("apples", 2000, 2.00);
    private final Product oranges = new Product("oranges", 2001, 3.00);
    private final Product pears = new Product("pears", 2002, 4.50);
    private final Product banana = new Product("banana", 2003, 5.75);
    private final Product watermelon = new Product("watermelon", 2004, 14.00);
    private final Product paperBag = new Product("paper bag", 1001, 0.10);
    private final Product canvasBag = new Product("canvas bag", 1002, 0.25);

    private ProductList productList;

    private final JLabel topText;
    private JTextArea productListDisplay;
    private JTextArea totalDisplay;

    private static final String JSON_FILE_PATH = "./data/productList.json";
    private static final String THANK_YOU_FILE_PATH = "./data/thankYou.jpg";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // Constructor
    // MODIFIES: this
    // EFFECTS: Constructs the JFrame window, loads all buttons, and initializes productList and JSON related files
    public PointOfSaleGUI() {
        super("Point of Sale");

        productList = new ProductList();

        topText = new JLabel("Welcome to the 210 Store");
        topText.setFont(new Font("Default", Font.PLAIN, 32));
        add(topText, BorderLayout.PAGE_START);

        addEventButtonsAndTotal();
        addSaveLoadButtons();
        addEndPurchaseButton();
        addListOfPurchaseDisplay();

        jsonWriter = new JsonWriter(JSON_FILE_PATH);
        jsonReader = new JsonReader(JSON_FILE_PATH);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Adding Various Button Grids =====

    // MODIFIES: this
    // EFFECTS: Adds 3 elements along the rightmost border: a button to add a bag, a button to remove items
    //          and the area where the users' total will be displayed.
    private void addEventButtonsAndTotal() {
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new GridLayout(3, 1));
        eventPanel.add(new JButton(new AddBagAction()));
        eventPanel.add(new JButton(new RemoveItemAction()));
        eventPanel.add(initTotalDisplay());

        this.add(eventPanel, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: Adds the buttons which save/load data to JSON files along the leftmost border
    private void addSaveLoadButtons() {
        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.setLayout(new GridLayout(3, 1));
        saveLoadPanel.add(new Container());
        saveLoadPanel.add(new JButton(new SaveAction()));
        saveLoadPanel.add(new JButton(new LoadAction()));

        this.add(saveLoadPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: places a small button to end the purchase at the bottom border
    private void addEndPurchaseButton() {
        JPanel endPurchasePanel = new JPanel();
        endPurchasePanel.add(new JButton(new EndPurchaseAction()));

        this.add(endPurchasePanel, BorderLayout.SOUTH);
    }


    /*
    ===== Methods to Display the List of Products so far =====
     */

    // MODIFIES: this, productListDisplay
    // EFFECTS: Initializes the area where the ProductList will be displayed. See updateProductList() as
    //          the function draws info from there next.
    private void addListOfPurchaseDisplay() {
        productListDisplay = new JTextArea();
        productListDisplay.setFont(new Font("Default", Font.PLAIN, 20));
        productListDisplay.setEditable(false);

        updateProductList();

        JScrollPane scrollPane = new JScrollPane(productListDisplay);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: productListDisplay
    // EFFECTS: Sets the text for productListDisplay and forces a refresh. See productListToString()
    private void updateProductList() {
        productListDisplay.setText("\n" + productListToString());
        repaint();
    }

    // EFFECTS: Builds a String so that all items on the productList can be displayed in form (for example):
    //
    //          ID5678 : product name  ..............................  $2.50
    //
    // (with a new line inserted between entries).
    private String productListToString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < productList.getSize(); i++) {
            Product product = productList.getProductFromIndex(i);
            sb.append("ID").append(product.getId()).append(" : ").append(product.getName().toUpperCase()).append(
                    "  ..............................  $").append(df.format(product.getPrice()));
            sb.append("\n");
        }

        return sb.toString();
    }


    /*
    ===== Displaying the Totaling Panel =====
     */

    // MODIFIES: totalDisplay
    // EFFECTS: initializes totalDisplay field and updates value through updateTotal()
    private JTextArea initTotalDisplay() {
        totalDisplay = new JTextArea();
        totalDisplay.setFont(new Font("Default", Font.BOLD, 32));
        totalDisplay.setEditable(false);

        updateTotal();

        return totalDisplay;

    }

    // MODIFIES: this, totalDisplay
    // EFFECTS: Attempts to change totalDisplay to form (for example):
    //
    //          TOTAL:
    //          $4.50
    //
    // Changes the string to that of an error in the case of an exception and attempts to format
    // the number so that the total can be displayed in form $0.00
    private void updateTotal() {

        String totalToString;

        try {
            totalToString = df.format(productList.getTotal());
        } catch (NumberFormatException e) {
            totalToString = "Internal Error:\nNumberFormatException";
        }

        totalDisplay.setText("\n TOTAL:\n $" + totalToString);
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: forces the productList window and total windows to repaint()
    private void updateAll() {
        updateProductList();
        updateTotal();
    }


    /*
    ===== Action Controllers for Buttons =====
     */

    // Class which handles processes when the bag button is pressed
    private class AddBagAction extends AbstractAction {

        // EFFECTS: constructs a button with the description as given below
        AddBagAction() {
            super("Add a Bag ($0.10)");
        }

        // MODIFIES: productList, topText
        // EFFECTS: adds a paper bag to the user's productList, notifying the user through topText and
        //          updating the total and productListDisplay
        @Override
        public void actionPerformed(ActionEvent e) {
            productList.addProduct(paperBag);
            topText.setText("Added: " + paperBag.getName() + ".");
            updateAll();
        }
    }

    // Class which handles processes when the remove button is pressed
    private class RemoveItemAction extends AbstractAction {

        // EFFECTS: constructs a button with the description as given below
        RemoveItemAction() {
            super("Remove Most Recent Item");
        }

        // MODIFIES: productList, topText
        // EFFECTS: adds removes the most recent item from the user's productList,
        //          notifying the user through topText and updating productListDisplay and totalDisplay
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Product productToRemove = productList.getProductFromIndex(productList.getSize() - 1);
                // note that as productList is an ArrayList it may struggle with large lists
                productList.removeProduct(productToRemove);
                topText.setText("Removed most recent item: " + productToRemove.getName());
                updateAll();
            } catch (IndexOutOfBoundsException exception) {
                topText.setText("No more items to remove.");
            }
        }
    }

    // Class which handles processes when the save button is pressed
    private class SaveAction extends AbstractAction {

        // EFFECTS: constructs a button with the description as given below
        SaveAction() {
            super("Save Purchase to File");
        }

        // EFFECTS: sends info to handleSave() and updates productListDisplay and totalDisplay
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSave();
            updateAll();
        }
    }

    // Class which handles processes when the load button is pressed
    private class LoadAction extends AbstractAction {

        // EFFECTS: constructs a button with the description as given below
        LoadAction() {
            super("Load Purchase From File");
        }

        // EFFECTS: sends info to handleLoad() and updates productListDisplay and totalDisplay
        @Override
        public void actionPerformed(ActionEvent e) {
            handleLoad();
            updateAll();
        }
    }

    // Class which handles processes when the end purchase button is pressed
    private class EndPurchaseAction extends AbstractAction {

        // EFFECTS: constructs a button with the description as given below
        EndPurchaseAction() {
            super("End Purchase");
        }

        // EFFECTS: calls addThankYouImage and displays a thank-you message on topText
        @Override
        public void actionPerformed(ActionEvent e) {
            addThankYouImage();
            topText.setText("Thank you for shopping!");
        }
    }

    // MODIFIES: this
    // EFFECTS: places the given thankYouImage onto the centre of the screen
    private void addThankYouImage() {
        ImageIcon thankYouImage = new ImageIcon(THANK_YOU_FILE_PATH);

        JPanel thankYouPanel = new JPanel();
        JLabel imageAsLabel = new JLabel(thankYouImage);

        thankYouPanel.add(imageAsLabel);
        this.add(thankYouPanel, BorderLayout.CENTER);
    }


    /*
    ===== JSON Content =====
     */

    // MODIFIES: jsonWriter, this (through topText)
    // EFFECTS: saves the workroom to file
    private void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(productList);
            jsonWriter.close();
            topText.setText("Saved your list of products to " + JSON_FILE_PATH);
        } catch (FileNotFoundException e) {
            topText.setText("Unable to write to file: " + JSON_FILE_PATH);
        }
    }

    // MODIFIES: this (through topText)
    // EFFECTS: loads workroom from file
    private void handleLoad() {
        try {
            productList = jsonReader.read();
            topText.setText("Loaded your list of products from " + JSON_FILE_PATH);
        } catch (IOException e) {
            topText.setText("Unable to read from file: " + JSON_FILE_PATH);
        }
    }

    /*
    ===== Main =====
     */

    // starts the program
    public static void main(String[] args) {
        new PointOfSaleGUI();
    }
}



















