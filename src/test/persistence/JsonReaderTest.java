package persistence;

import model.Product;
import model.ProductList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

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


}
