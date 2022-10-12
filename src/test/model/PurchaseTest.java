package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {

    private Purchase purchase200Card;
    private Purchase purchase25Cash;
    private Purchase purchase2Dollars99CentsCash;

    @BeforeEach
    public void setup() {
        purchase200Card = new Purchase(200, true);
        purchase25Cash = new Purchase(25, false);
        purchase2Dollars99CentsCash = new Purchase(2.99, false);

    }

    @Test
    public void testGetters() {
        assertTrue(purchase200Card.getCardState());
        assertFalse(purchase2Dollars99CentsCash.getCardState());

        assertEquals(25.00, purchase25Cash.getAmount());
        assertEquals(2.99, purchase2Dollars99CentsCash.getAmount());
    }

    @Test
    public void testCalculateChange() {
        assertEquals(25, purchase25Cash.calculateChange(50));
        assertEquals(1.01, purchase2Dollars99CentsCash.calculateChange(4.00));
    }

}
