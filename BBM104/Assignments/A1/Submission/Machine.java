import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Machine {
    private static final int ROW_COUNT = 6;
    private static final int COLUMN_COUNT = 4;

    private final Slot[][] slots = new Slot[ROW_COUNT][COLUMN_COUNT];
    private List<Purchase> purchases = new ArrayList<>();
    private final StringBuilder stringBuilder;

    public Machine(StringBuilder sb) {
        this.stringBuilder = sb;
    }

    /**
     * Tries to place the given product in an appropriate slot within the vending machine.
     *
     * @param product The product to be placed.
     * @return True if the machine still has some places to try next.
     */
    private boolean putAppropriatePlace(Product product) {
        // Loop through columns
        for (int i = 0; i < ROW_COUNT; i++) {
            // Loop through rows
            for (int j = 0; j < COLUMN_COUNT; j++) {
                if (slots[i][j] == null) {
                    slots[i][j] = new Slot(product);
                }
                if (slots[i][j].TryLoad(product)) {
                    return true;
                    // Product successfully loaded into a slot. This can be last slot, but we stop loading when
                    // we try to put a product and realize there is no space left.
                }
            }
        }

        // If there's no available slot to place the product
        stringBuilder.append("INFO: There is no available place to put ")
                .append(product.getName())
                .append("\n");

        // Check if there's any empty slot left in the machine
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                if (slots[i][j] == null || !slots[i][j].isFull()) {
                    return true; // There's still some place left in the machine
                }
            }
        }

        // If the machine is full
        stringBuilder.append("INFO: The machine is full!\n");
        return false;
    }


    /**
     * Loads products into the vending machine slots based on the provided lines.
     *
     * @param lines An array of strings representing product details.
     */
    public void loadUsingLines(String[] lines) {
        for (String line : lines) {
            String[] elements = line.split("\t");

            // Extracting product details from each line
            Product product = getProduct(elements);

            // Attempting to place the product in an appropriate slot
            boolean isThereSpaceAvailable = putAppropriatePlace(product);
            if (!isThereSpaceAvailable) {
                break; // Stop loading products if there's no available space in the machine
            }
        }
    }

    /**
     * Creates a Product object from the given array of elements.
     *
     * @param elements An array of strings representing product details.
     *                 The elements should be in the format: [name, price, nutritionValues[3]]
     * @return A Product object initialized with the provided details.
     */
    private static Product getProduct(String[] elements) {
        String name = elements[0];
        int price = Integer.parseInt(elements[1]);
        String[] nutritionValues = elements[2].split(" ");
        float protein = Float.parseFloat(nutritionValues[0]);
        float carb = Float.parseFloat(nutritionValues[1]);
        float fat = Float.parseFloat(nutritionValues[2]);

        // Creating a Product object from the extracted details
        return new Product(name, price, fat, carb, protein);
    }


    /**
     * Prints the current situation of the gym meal vending machine.
     * It displays the products stored in each slot, along with their names, calories, and counts.
     */
    public void printCurrentSituation() {
        stringBuilder.append("-----Gym Meal Machine-----\n");
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                if (slots[i][j] == null) {
                    stringBuilder.append("___(0, 0)___");
                    continue;
                }
                String name = slots[i][j].getCurrentProduct().getName();
                int calorie = Math.round(slots[i][j].getCurrentProduct().getCalorie());
                int count = slots[i][j].getCount();
                stringBuilder.append(name)
                        .append("(")
                        .append(calorie)
                        .append(", ").append(count).append(")___");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("----------\n");
    }

    /**
     * Handles purchases based on the provided lines.
     * Parses each line to extract purchase details and then processes the purchase.
     *
     * @param lines An array of strings representing purchase details.
     */
    public void handlePurchases(String[] lines) {
        for (String line : lines) {
            String[] elements = line.split("\t");

            // Extracting payment type
            String paymentType = elements[0];

            // Extracting payment bills
            int[] bills = Arrays.stream(elements[1].split(" ")).mapToInt(Integer::parseInt).toArray();

            // Extracting purchase requirement
            String requirementString = elements[2];
            PurchaseRequirement requirement = getPurchaseRequirement(requirementString);

            // Extracting purchase requirement amount
            int requirementAmount = Integer.parseInt(elements[3].trim());

            // Creating a Purchase object from the extracted details
            Purchase purchase = new Purchase(paymentType, bills, requirement, requirementAmount);
            purchases.add(purchase);

            // Handling the purchase
            handlePurchase(purchase);
        }
    }


    /**
     * Handles the purchase operation based on the provided purchase details.
     *
     * @param purchase The purchase details to be handled.
     */
    private void handlePurchase(Purchase purchase) {
        stringBuilder.append("INPUT: CASH\t");

        int[] bills = purchase.getBills();
        for (int i = 0; i < bills.length; i++) {
            stringBuilder.append(bills[i]);
            if (i != bills.length - 1) stringBuilder.append(" ");
        }
        stringBuilder.append("\t");

        PurchaseRequirement requirement = purchase.getRequirement();

        stringBuilder.append(requirement.toString().toUpperCase(Locale.ENGLISH));
        stringBuilder.append("\t");
        stringBuilder.append(purchase.getRequirementAmount());
        stringBuilder.append("\n");

        // Handle number separately since it's about the slot not product.
        if (requirement == PurchaseRequirement.Number) {
            int row = purchase.getRequirementAmount() / COLUMN_COUNT;
            int column = purchase.getRequirementAmount() % COLUMN_COUNT;

            // Try catch to handle any unacceptable number input.
            try {
                if (slots[row][column] == null) {
                    stringBuilder.append("INFO: This slot is empty, your money will be returned.\n");
                } else {
                    int price = slots[row][column].getCurrentProduct().getPrice();
                    boolean isTransactionHappened = realizeTransactionIfPossible(purchase.getPaymentAmount(), row, column);
                    if (isTransactionHappened) {
                        printChange(purchase.getPaymentAmount() - price);
                        return;
                    }
                    stringBuilder.append("INFO: Insufficient money, try again with more money.\n");
                }
            } catch (Exception e) {
                stringBuilder.append("INFO: Number cannot be accepted. Please try again with another number.\n");
            }

            printChange(purchase.getPaymentAmount());

            return;
        }

        // It is possible to find an appropriate product within the budget later.
        // So if a product that satisfies the requirement but is too expensive exists,
        // determine if the failed transaction is due to insufficient funds or the absence of the product.
        boolean productFound = false;

        // Loop through slots to find the product that satisfies the requirement.
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                if (slots[i][j] == null) continue;
                float value = slots[i][j].getCurrentProduct().getValueOfRequirement(requirement);
                float diff = purchase.getRequirementAmount() - value;
                // If the value is in the acceptable range
                if (diff <= 5.0f && diff >= -5.0f) {
                    productFound = true;
                    int price = slots[i][j].getCurrentProduct().getPrice();
                    if (realizeTransactionIfPossible(purchase.getPaymentAmount(), i, j)) {
                        printChange(purchase.getPaymentAmount() - price);
                        return;
                    }
                }
            }
        }

        // Product found, yet no transaction happened. So, there is insufficient balance.
        if (productFound) {
            stringBuilder.append("INFO: Insufficient money, try again with more money.\n");
        }
        // The product that the user wants doesn't even exist.
        else {
            stringBuilder.append("INFO: Product not found, your money will be returned.\n");
        }
        printChange(purchase.getPaymentAmount());
    }

    /**
     * Attempts to realize a transaction if possible.
     * If there is enough money, it completes the transaction, updates the slot count, and logs the purchase.
     *
     * @param payment The amount paid by the user.
     * @param row     The row of the slot.
     * @param column  The column of the slot.
     * @return True if the transaction is successful, false otherwise.
     */
    private boolean realizeTransactionIfPossible(int payment, int row, int column) {
        // If there is enough money, complete the transaction
        if (payment >= slots[row][column].getCurrentProduct().getPrice()) {
            stringBuilder.append("PURCHASE: You have bought one ")
                    .append(slots[row][column].getCurrentProduct().getName())
                    .append("\n");

            // Reduce the count if there is more than one item in the slot
            if (slots[row][column].getCount() > 1) {
                slots[row][column].reduceCount();
                return true;
            }

            // If there's only one item, remove the slot
            slots[row][column] = null;
            return true;
        }
        return false;
    }

    /**
     * Converts a string to a PurchaseRequirement enum value.
     *
     * @param s The string representation of the PurchaseRequirement.
     * @return The corresponding PurchaseRequirement enum value.
     * @throws IllegalArgumentException if the string does not match any PurchaseRequirement.
     */
    private PurchaseRequirement getPurchaseRequirement(String s) {
        switch (s) {
            case "PROTEIN":
                return PurchaseRequirement.Protein;
            case "CARB":
                return PurchaseRequirement.Carb;
            case "FAT":
                return PurchaseRequirement.Fat;
            case "CALORIE":
                return PurchaseRequirement.Calorie;
            case "NUMBER":
                return PurchaseRequirement.Number;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Prints the change to be returned to the user.
     *
     * @param change The amount of change to be returned.
     */
    private void printChange(int change) {
        stringBuilder.append("RETURN: Returning your change: ")
                .append(change)
                .append(" TL")
                .append("\n");
    }

}