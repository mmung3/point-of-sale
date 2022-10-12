package model;

import java.util.ArrayList;

public class ProductList {

    private final ArrayList<Product> productList;

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

        for (Product pr : productList) {
            totalSoFar += pr.getPrice();
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

    // EFFECTS: returns a list of the IDs of items currently in queue
    public ArrayList<Integer> getIdList() {
        ArrayList<Integer> idListSoFar = new ArrayList<>();

        for (Product p : productList) {
            idListSoFar.add(p.getId());
        }
        return idListSoFar;
    }

    // EFFECTS: returns a Product associated with a given index
    public Product getProductFromIdIndex(int index) {
        return productList.get(index);
    }

    // GETTERS ====

    // EFFECTS: adds a product to the list of Products
    public void addProduct(Product product) {
        productList.add(product);
    }

    // EFFECTS: removes a product from the list of Products
    public void removeProduct(Product product) {
        productList.remove(product);
    }


}
