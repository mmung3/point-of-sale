package model;

import org.json.JSONObject;
import persistence.Writable;

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
