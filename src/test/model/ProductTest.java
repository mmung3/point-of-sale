package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product apples;
    private Product oranges;
    private Product paperBag;
    private Product canvasBag;

    @BeforeEach
    public void setup() {
        apples = new Product("apples", 2000, 2.00);
        oranges = new Product("oranges", 2001, 3.00);
        paperBag = new Product("paper bag", 1001, 0.10);
        canvasBag = new Product("canvas bag", 1002, 0.25);
    }

    @Test
    public void testGetName() {
        assertEquals("apples", apples.getName());
        assertEquals("oranges", oranges.getName());
        assertEquals("paper bag", paperBag.getName());
    }

    @Test
    public void testGetId() {
        assertEquals(2000, apples.getId());
        assertEquals(2001, oranges.getId());
    }

    @Test
    public void testGetPrice() {
        assertEquals(2.00, apples.getPrice());
        assertEquals(3.00, oranges.getPrice());
        assertEquals(0.25, canvasBag.getPrice());
    }
}
