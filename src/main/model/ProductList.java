package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class ProductList implements Writable {

    private final ArrayList<Product> productList;

    public ProductList() {
        productList = new ArrayList<>();
    }

    // EFFECTS: returns a list of Products
    public ArrayList<Product> getProductList() {
        return productList;
    }

    // EFFECTS: returns the size of the array
    public int getSize() {
        return productList.size();
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
    public Product getProductFromIndex(int index) {
        return productList.get(index);
    }


    // JSON CONTENT ===

    @Override
    // EFFECTS: returns this as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Products", productsToJson());
        return json;
    }

    // EFFECTS: returns products in this productList as a JSONArray
    private JSONArray productsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product product : productList) {
            jsonArray.put(product.toJson());
        }

        return jsonArray;
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
