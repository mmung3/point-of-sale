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

    private ProductList pl;

    private final JLabel topText;
    private JTextArea productListDisplay;
    private JTextArea totalDisplay;

    private static final String JSON_FILE_PATH = "./data/productList.json";
    private static final String THANK_YOU_FILE_PATH = "./data/thankYou.jpg";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    public PointOfSaleGUI() {
        super("Point of Sale");

        pl = new ProductList();

        topText = new JLabel("Welcome to the 210 Store");
        topText.setFont(new Font("Default", Font.PLAIN, 32));
        add(topText, BorderLayout.PAGE_START);

        addEventButtons();
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

    private void addEventButtons() {
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new GridLayout(3, 1));
        eventPanel.add(new JButton(new AddBagAction()));
        eventPanel.add(new JButton(new RemoveItemAction()));
        eventPanel.add(addTotalJTextArea());

        this.add(eventPanel, BorderLayout.EAST);
    }

    private void addSaveLoadButtons() {
        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.setLayout(new GridLayout(3, 1));
        saveLoadPanel.add(new Container());
        saveLoadPanel.add(new JButton(new SaveAction()));
        saveLoadPanel.add(new JButton(new LoadAction()));

        this.add(saveLoadPanel, BorderLayout.WEST);
    }

    private void addEndPurchaseButton() {
        JPanel endPurchasePanel = new JPanel();
        endPurchasePanel.add(new JButton(new EndPurchaseAction()));

        this.add(endPurchasePanel, BorderLayout.SOUTH);
    }

    // Methods for Display of the List of Products so far =====

    private void addListOfPurchaseDisplay() {
        productListDisplay = new JTextArea();
        productListDisplay.setFont(new Font("Default", Font.PLAIN, 20));
        productListDisplay.setEditable(false);

        updateProductList();

        JScrollPane scrollPane = new JScrollPane(productListDisplay);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private String productListToString() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < pl.getSize(); i++) {
            Product product = pl.getProductFromIndex(i);
            sb.append("ID").append(product.getId()).append(" : ").append(product.getName().toUpperCase()).append(
                    "  ..............................  $").append(df.format(product.getPrice()));
            sb.append("\n");
        }

        return sb.toString();
    }

    private void updateProductList() {
        productListDisplay.setText("\n" + productListToString());
        repaint();
    }

    // Displaying the Totaling Panel =====

    private JTextArea addTotalJTextArea() {
        totalDisplay = new JTextArea();
        totalDisplay.setFont(new Font("Default", Font.BOLD, 32));
        totalDisplay.setEditable(false);

        updateTotal();

        return totalDisplay;

    }

    private void updateTotal() {

        String totalToString;

        try {
            totalToString = df.format(pl.getTotal());
        } catch (NumberFormatException e) {
            totalToString = "Internal Error:\nNumberFormatException";
        }

        totalDisplay.setText("\n TOTAL:\n $" + totalToString);
        repaint();
    }

    private void updateAll() {
        updateProductList();
        updateTotal();
    }


    // Action Controllers for Buttons =====

    private class AddBagAction extends AbstractAction {

        AddBagAction() {
            super("Add a Bag ($0.10)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            pl.addProduct(paperBag);
            topText.setText("Added: " + paperBag.getName() + ".");
            updateAll();
        }
    }

    private class RemoveItemAction extends AbstractAction {

        RemoveItemAction() {
            super("Remove Most Recent Item");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Product productToRemove = pl.getProductFromIndex(pl.getSize() - 1);
                pl.removeProduct(productToRemove);
                topText.setText("Removed most recent item: " + productToRemove.getName());
                updateAll();
            } catch (IndexOutOfBoundsException exception) {
                topText.setText("No more items to remove.");
            }
        }
    }

    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save Purchase to File");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            handleSave();
            updateAll();
        }
    }

    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load Purchase From File");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            handleLoad();
            updateAll();
        }
    }

    private class EndPurchaseAction extends AbstractAction {

        EndPurchaseAction() {
            super("End Purchase");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            addThankYouImage();
            topText.setText("Thank you for shopping!");
        }
    }

    private void addThankYouImage() {
        ImageIcon thankYouImage = new ImageIcon(THANK_YOU_FILE_PATH);

        JPanel thankYouPanel = new JPanel();
        JLabel imageAsLabel = new JLabel(thankYouImage);

        thankYouPanel.add(imageAsLabel);
        this.add(thankYouPanel, BorderLayout.CENTER);
    }

    // JSON Content =====

    private void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(pl);
            jsonWriter.close();
            topText.setText("Saved your list of products to " + JSON_FILE_PATH);
        } catch (FileNotFoundException e) {
            topText.setText("Unable to write to file: " + JSON_FILE_PATH);
        }
    }

    private void handleLoad() {
        try {
            pl = jsonReader.read();
            topText.setText("Loaded your list of products from " + JSON_FILE_PATH);
        } catch (IOException e) {
            topText.setText("Unable to read from file: " + JSON_FILE_PATH);
        }
    }


    public static void main(String[] args) {
        new PointOfSaleGUI();
    }
}



















