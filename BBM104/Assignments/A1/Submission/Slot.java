/**
 * Represents a slot in the vending machine.
 * Each slot can hold a certain number of a specific product.
 */
public class Slot {
    private final Product currentProduct;
    private int currentProductCount;
    private static final int MAX_COUNT = 10;

    /**
     * Constructs a slot with the given product.
     *
     * @param product The product to be stored in the slot.
     */
    public Slot(Product product) {
        currentProduct = product;
    }

    /**
     * Tries to load the given product into the slot.
     * If the slot is not full and the product matches the current product, it increments the count.
     *
     * @param product The product to be loaded into the slot.
     * @return True if the product is successfully loaded, false otherwise.
     */
    public boolean TryLoad(Product product) {
        if (isFull()) return false;

        String productName = product.getName();
        if (!productName.equals(currentProduct.getName())) return false;

        currentProductCount += 1;
        return true;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public int getCount() {
        return currentProductCount;
    }

    /**
     * Reduces the count of the current product in the slot by one.
     */
    public void reduceCount() {
        currentProductCount--;
    }

    /**
     * Checks if the slot is full.
     *
     * @return True if the slot is full, false otherwise.
     */
    public boolean isFull() {
        return currentProductCount >= MAX_COUNT;
    }
}
