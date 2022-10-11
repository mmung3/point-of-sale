package model;

import java.util.ArrayList;

public class ProductList {

    private ArrayList<Product> productList;

    public ProductList() {
        productList = new ArrayList<>();
    }

    // EFFECTS: returns a list of Products
    public ArrayList<Product> getProductList() {
        return productList;
    }

    // EFFECTS: calculates the sum of a given list of items
    public double getTotal() {
        double totalSoFar = 0;

        for (Product p : productList) {
            totalSoFar += p.getPrice();
        }
        return totalSoFar;
    }

    // EFFECTS: returns a list of the items currently in queue
    public ArrayList<String> getNameList() {
        ArrayList<String> nameListSoFar = new ArrayList<>();

        for (Product p : productList) {
            nameListSoFar.add(p.getName());
        }
        return nameListSoFar;
    }

    // GETTERS ====

    // EFFECTS: returns size of a ProductList, mainly used for testing
    public int getLength() {
        return productList.size();
    }

    // EFFECTS: adds a product to the list of Products
    public void addProduct(Product product) {
        productList.add(product);
    }

    // EFFECTS: removes a product from the list of Products
    public void removeProduct(Product product) {
        productList.remove(product);
    }

}
