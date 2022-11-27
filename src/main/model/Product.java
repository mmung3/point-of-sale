package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a product that a user may wish to purchase with a name, ID and price
public class Product implements Writable {
    private final String name;
    private final int id;
    private final double price;

    // REQUIRES: id is 4 digits, price > 0
    public Product(String name, int id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    @Override
    // EFFECTS: returns this as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("id", id);
        json.put("price", price);
        return json;
    }

    // GETTERS ====
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
}
