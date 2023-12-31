package model;

// contains info regarding a purchase that the user may perform, with info regarding cash (with change) or card.
public class Purchase {

    private final boolean card; // determines what method the user is paying by
    private final double amount;

    public Purchase(double amount, boolean card) {
        this.amount = amount;
        this.card = card; // true = card, false = cash
    }

    // REQUIRES: amount, cashGiven > 0, card = false
    // EFFECTS: calculates the change that a customer would receive.
    //          Returns -1 if the cashGiven is insufficient
    //          Returns 0 if the cashGiven is exactly the amount
    //          Returns the change otherwise
    public double calculateChange(double cashGiven) {
        double preRoundedAmount;

        if (amount > cashGiven) {
            return -1; // handle the case of insufficient amount in UI
        } else if (amount == cashGiven) {
            return 0;
        } else {
            preRoundedAmount = (cashGiven - amount);
            return Math.round(preRoundedAmount * 100.00) / 100.00; // forces 2 decimal places
        }
    }


    // GETTERS ====
    public double getAmount() {
        return amount;
    }

    public boolean getCardState() {
        return card;
    }
}
