package persistence;

import model.Product;
import model.ProductList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads JSON representation of ProductList from a file
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ProductList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ProductList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseProductList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses productList from JSON object and returns it
    private ProductList parseProductList(JSONObject jsonObject) {
        ProductList productList = new ProductList();
        addProducts(productList, jsonObject);
        return productList;
    }

    // MODIFIES: productList
    // EFFECTS: parses product(s) from JSON object and adds them to productList
    private void addProducts(ProductList productList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Products");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            addProduct(productList, nextProduct);
        }
    }

    // MODIFIES: productList
    // EFFECTS: parses product from JSON object and adds it to productList
    private void addProduct(ProductList productList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int id = jsonObject.getInt("id");
        double price = jsonObject.getDouble("price");
        Product product = new Product(name, id, price);
        productList.addProduct(product);
    }
}
