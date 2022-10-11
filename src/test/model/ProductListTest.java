package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductListTest {
    private Product apples;
    private Product oranges;
    private Product pears;
    private Product banana;
    private Product watermelon;
    private Product paperBag;
    private Product canvasBag;

    // the numbers 'one', 'four' feature products added in the order they
    // are listed in setup()
    private ProductList noItems;
    private ProductList oneItem;
    private ProductList fourItems;

    @BeforeEach
    public void setup() {
        apples = new Product("apples", 2000, 2.00);
        oranges = new Product("oranges", 2001, 3.00);
        pears = new Product("pears", 2002, 4.50);
        banana = new Product("banana", 2003, 5.75);
        watermelon = new Product("watermelon", 2004, 14.00);
        paperBag = new Product("paper bag", 1001, 0.10);
        canvasBag = new Product("canvas bag", 1002, 0.25);

        noItems = new ProductList();

        oneItem = new ProductList();
        oneItem.addProduct(apples);
        // using these given addProduct() is functional given by the first test

        fourItems = new ProductList();
        fourItems.addProduct(apples);
        fourItems.addProduct(oranges);
        fourItems.addProduct(pears);
        fourItems.addProduct(banana);
    }

    @Test
    public void testGetters() {
        ArrayList<String> productNameList = new ArrayList<>();
        productNameList.add("apples");

        assertEquals(productNameList, oneItem.getNameList());
    }

    @Test
    public void testAddOneApple() {
        ArrayList<String> applesList = new ArrayList<>();
        applesList.add("apples");

        noItems.addProduct(apples);

        assertEquals(applesList, noItems.getNameList());
        assertEquals(2.00, noItems.getTotal());
    }

    @Test
    public void testAddTwoApples() {
        ArrayList<String> twoApplesList = new ArrayList<>();
        twoApplesList.add("apples");
        twoApplesList.add("apples");

        noItems.addProduct(apples);
        noItems.addProduct(apples);

        assertEquals(twoApplesList, noItems.getNameList());
        assertEquals(4.00, noItems.getTotal());

    }

    @Test
    public void testAddBothBags() {
        ArrayList<String> bagList = new ArrayList<>();
        bagList.add("paper bag");
        bagList.add("canvas bag");

        noItems.addProduct(paperBag);
        noItems.addProduct(canvasBag);

        assertEquals(bagList, noItems.getNameList());
        assertEquals(0.35, noItems.getTotal());
    }

    @Test
    public void testAddThree() {
        ArrayList<String> applesOrangesPearsList = new ArrayList<>();
        applesOrangesPearsList.add("apples");
        applesOrangesPearsList.add("oranges");
        applesOrangesPearsList.add("pears");

        noItems.addProduct(apples);
        noItems.addProduct(oranges);
        noItems.addProduct(pears);

        assertEquals(applesOrangesPearsList, noItems.getNameList());
        assertEquals(9.50, noItems.getTotal());
    }

    @Test
    public void testRemoveOne() {
        ArrayList<String> applesRemovedList = new ArrayList<>();
        applesRemovedList.add("oranges");
        applesRemovedList.add("pears");
        applesRemovedList.add("banana");

        fourItems.removeProduct(apples);

        assertEquals(applesRemovedList, fourItems.getNameList());
        assertEquals(13.25, fourItems.getTotal());
    }

    @Test
    public void testAddThreeRemoveOneFromEmpty() {
        ArrayList<String> pearsBananaWatermelonList = new ArrayList<>();
        pearsBananaWatermelonList.add("pears");
        pearsBananaWatermelonList.add("banana");
        pearsBananaWatermelonList.add("watermelon");

        noItems.addProduct(pears);
        noItems.addProduct(banana);
        noItems.addProduct(watermelon);

        assertEquals(pearsBananaWatermelonList, noItems.getNameList());
        assertEquals(24.25, noItems.getTotal());

        // remove banana setup and execution
        ArrayList<String> pearsWatermelonList = new ArrayList<>();
        pearsWatermelonList.add("pears");
        pearsWatermelonList.add("watermelon");

        noItems.removeProduct(banana);

        assertEquals(pearsWatermelonList, noItems.getNameList());
        assertEquals(18.50, noItems.getTotal());
    }
}
