package persistence;

import model.Product;
import model.ProductList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ProductList productList = new ProductList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testEmptyProductList() {
        try {
            ProductList productList = new ProductList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyProductList.json");
            writer.open();
            writer.write(productList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyProductList.json");
            productList = reader.read();
            assertEquals(0, productList.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ProductList productListForThisTest = new ProductList();
            productListForThisTest.addProduct(new Product("apples", 2000, 2.00));
            productListForThisTest.addProduct(new Product("banana", 2003, 5.75));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralProductList.json");
            writer.open();
            writer.write(productListForThisTest);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralProductList.json");
            ProductList productListFromDataPackage = reader.read(); //fails here
            assertEquals(2, productListFromDataPackage.getSize());

            checkProduct(2, "apples", 2000, productListFromDataPackage.getProductFromIndex(0));
            checkProduct(5.75, "banana", 2003, productListFromDataPackage.getProductFromIndex(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
