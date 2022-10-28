package persistence;

import model.Product;
import model.ProductList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ProductList productList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyProductList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyProductList.json");
        try {
            ProductList productList = reader.read();
            assertEquals(0, productList.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralProductList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralProductList.json");
        try {
            ProductList productListFromDataPackage = reader.read();

            ArrayList<Product> productListForThisTest = productListFromDataPackage.getProductList();
            assertEquals(5, productListForThisTest.size());

            checkProduct(3, "oranges", 2001, productListForThisTest.get(0));
            checkProduct(14, "watermelon", 2004, productListForThisTest.get(2));
            checkProduct(0.1, "paper bag", 1001, productListForThisTest.get(3));
            checkProduct(0.1, "paper bag", 1001, productListForThisTest.get(4));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
