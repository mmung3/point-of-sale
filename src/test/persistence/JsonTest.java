package persistence;

import model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Class extended by JsonWriterTest and JsonReaderTest, with one method as given:
public class JsonTest {

    // EFFECTS: checks if a given Product is equivalent to the given parameters
    protected void checkProduct(double price, String name, int id, Product product) {
        assertEquals(price, product.getPrice());
        assertEquals(name, product.getName());
        assertEquals(id, product.getId());
    }
}
