package model;

public class Product {
    private String name;
    private int id;
    private double price;

    // REQUIRES: id is 4 digits, price > 0
    public Product(String name, int id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
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
