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


public class PointOfSaleGUI extends JFrame {

    private static final int WIDTH = 850;
    private static final int HEIGHT = 500;

    private Product apples = new Product("apples", 2000, 2.00);
    private Product oranges = new Product("oranges", 2001, 3.00);
    private Product pears = new Product("pears", 2002, 4.50);
    private Product banana = new Product("banana", 2003, 5.75);
    private Product watermelon = new Product("watermelon", 2004, 14.00);
    private Product paperBag = new Product("paper bag", 1001, 0.10);
    private Product canvasBag = new Product("canvas bag", 1002, 0.25);

    private ProductList pl;

    private static final String JSON_STORE = "./data/productList.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public PointOfSaleGUI() {
        super("Point of Sale");

        pl = new ProductList();

        JLabel topText = new JLabel("Welcome to the 210 Store");
        add(topText, BorderLayout.NORTH);
        // keep above

        addEventButtons();
        addSaveLoadButtons();
        addEndPurchaseButton();
        addListOfPurchaseDisplay();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // keep below
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

        this.add(eventPanel, BorderLayout.WEST);
    }

    private void addSaveLoadButtons() {
        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.setLayout(new GridLayout(3, 1));
        saveLoadPanel.add(new JButton(new SaveAction()));
        saveLoadPanel.add(new JButton(new LoadAction()));

        this.add(saveLoadPanel, BorderLayout.EAST);
    }

    private void addEndPurchaseButton() {
        JPanel saveLoadPanel = new JPanel();
//        saveLoadPanel.setLayout(new GridLayout(1, 1));
        saveLoadPanel.add(new JButton(new EndPurchaseAction()));

        this.add(saveLoadPanel, BorderLayout.SOUTH);
    }

    private void addListOfPurchaseDisplay() {
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new GridLayout(3, 1));
        // todo: add something here

        this.add(eventPanel, BorderLayout.CENTER);
    }

    // Action Controllers for Buttons =====

    private class AddBagAction extends AbstractAction {

        AddBagAction() {
            super("Add a Bag ($0.10)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            pl.addProduct(paperBag);
        }
    }

    private class RemoveItemAction extends AbstractAction {

        RemoveItemAction() {
            super("Remove Most Recent Item");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            pl.removeProduct(pl.getProductFromIndex(pl.getSize() - 1));
        }
    }

    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save Purchase to File");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            handleSave();
        }
    }

    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load Purchase From File");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            handleLoad();
        }
    }

    private class EndPurchaseAction extends AbstractAction {

        EndPurchaseAction() {
            super("End Purchase");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("display ty graphic"); // todo
        }
    }

    // JSON Content =====

    private void handleSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(pl);
            jsonWriter.close();
            System.out.println("\n\tSaved your list of products to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("\n\tUnable to write to file: " + JSON_STORE); //todo convert to notifs
        }
    }

    private void handleLoad() {
        try {
            pl = jsonReader.read();
            System.out.println("\n\tLoaded your list of products from " + JSON_STORE);
            // printOrderSoFarMessage();
        } catch (IOException e) {
            System.out.println("\n\tUnable to read from file: " + JSON_STORE);
        }
    }


    public static void main(String[] args) {
        new PointOfSaleGUI();
    }
}



















