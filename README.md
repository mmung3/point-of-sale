# Point of Sale

### What is this project?

This project will run a check-out system commonly seen in
a grocery or convenience store. It will be modelled after
a **self-checkout** system (for a *customer* to use), 
although it could also be run by a *human* cashier.

### Inspiration

Having worked in retail previously, the poor design of some
systems infuriated my co-workers and I. With this project
I will explore the design of a more functional and
effective point of sale.

### User Stories
Some examples include:
- as a user, I want to add products (including bags) that are on the
catalogue to my current list of items
- as a user, I want to remove products that I no longer
wish to purchase
- as a user, I want to see my amount of change calculated
correctly based on a cash amount I will give
- as a user, I want to enter a validly formatted card
number and PIN to complete the purchase
- as a user, I want to be able to save my entire list of items
so that, if I wanted, I could come back with more items, or
come back with a different or new payment method 
(*for example, I left my wallet in the car, 
or I need to go grab an extra can of beans
all the day down in aisle 15*). 
- as a user, I want to see an in-progress purchase
be made active again.

# Instructions for Grader

- You can generate the first required event related to adding Xs to a Y by...

**Clicking the "Add a bag ($0.10)" button in the top right.**
First event adds a paper bag of type Product to a ProductList.

- You can generate the second required event related to adding Xs to a Y by...

**Clicking the "Remove Most Recent Item" button near the middle right.**
Note that this requires an item to be in the ProductList already. Try adding
a few bags first to test this second event.

- You can locate my visual component by...

**Clicking the "End Purchase" button at the bottom.**
This will display a thank-you message and display a thank-you graphic on screen.

- You can save the state of my application by...

**Clicking the "Save Purchase to File" button near the middle left.**

- You can reload the state of my application by...

**Clicking the "Load Purchase from File" button near the bottom left.**

#

*image credit goes to https://www.istockphoto.com/*