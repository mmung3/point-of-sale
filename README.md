# Point of Sale

### What is this project?

This project will run a check-out system commonly seen in
a grocery or convenience store. It will be modelled after
a **self-checkout** system (for a *customer* to use), 
although it could also be run by a *human* cashier.

> How do I navigate to the code in this repository?
> - most of the code can be found in the `src` folder, and navigating from there.
> - `main` includes code for the internal code and GUI
> - `test` includes `JUnit`-focused collection of tests


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

# How to use the GUI

- You can generate the first required event related to adding Xs to a Y by...

**Clicking the "Add a bag ($0.10)" OR "Add One Apple ($2.00)" button in the top left.**
First event adds an item (either an apple or a bag) of type Product to a ProductList.

- You can generate the second required event related to adding Xs to a Y by...

**Clicking the "Remove Most Recent Item" button in the bottom left.**
Note that this requires an item to be in the ProductList already. Try adding
a few bags or an apple first to test this second event.

- You can locate my visual component by...

**Clicking the "End Purchase" button at the bottom.**
This will display a thank-you message and display a thank-you graphic on screen.

- You can save the state of my application by...

**Clicking the "Save Purchase to File" button near the top right.**

- You can reload the state of my application by...

**Clicking the "Load Purchase from File" button near the middle right.**

# Example Logs

***Below is an example output from the log:***
###
Fri Nov 25 14:23:04 PST 2022

=== apple added for $2.00 ===
###
Fri Nov 25 14:23:04 PST 2022

=== paper bag added for $0.10 ===
###
Fri Nov 25 14:23:06 PST 2022

=== apple added for $2.00 ===
###
Fri Nov 25 14:23:07 PST 2022

=== apple removed from ProductList ===
###

# Project Discussion
*See UML_Design_Diagram.png attached in the 
root folder of this project.*

Regarding refactoring, I found most classes in the *model*
package to be fairly cohesive. Some small refactors maybe could
have been done regarding some strings but this would likely be
unnecessary overall.

There was, however, a lot of refactoring that could be done
within the *UI* packages; none of them conform very well to the
Single-Responsibility Principle, with two classes overall to handle
the console-based UI and one class to handle all the GUI.

For example, the class *PointOfSaleConsoleTool* could be edited to
consist of only print statements and have its name changed as such.
It currently houses all the setup methods needed for the console
version to function correctly (ex. the lists, the boolean 
*running*, etc.).

The *PointOfSaleConsole* (not the tool version) could also be refactored in
a couple of ways, one example being to split it into two main classes:
- One whose purpose is to 'understand' the user's input
(ex. convert given ID from string to int)
- One 'handles' the associated action (ex. removes an 
item after first searching the list for it)

Or another way could be to split it by the different actions performed, for example:
- one class for sending the product list to be totalled
  - with subclasses for a cash or card purchase
- one class for adding items only
- one class for removing items
- *etc.*

The GUI version in *PointOfSaleGUI* could also use some serious refactoring;
all the code that powers the GUI version is in one class. A good way to split it
would be based off the panel sections (east, west, centre, etc.)
- a class for the add/remove methods
- a class for updating the product list display
- a class for updating and displaying the total panel
- a class for any JSON related content
- *etc.*

#
*image credit goes to https://www.istockphoto.com/*
