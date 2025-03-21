/**
 * Represents a product available in the vending machine.
 */
public class Product {
    private final String name;
    private final int price;
    private final float fat;
    private final float carbohydrate;
    private final float protein;
    private final float calorie;

    /**
     * Constructs a product with the given details.
     *
     * @param name         The name of the product.
     * @param price        The price of the product.
     * @param fat          The fat content of the product.
     * @param carbohydrate The carbohydrate content of the product.
     * @param protein      The protein content of the product.
     */
    public Product(String name, int price, float fat, float carbohydrate, float protein) {
        this.name = name;
        this.price = price;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.calorie = 4 * protein + 4 * carbohydrate + 9 * fat;
    }

    //region Getters

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public float getFat() {
        return fat;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public float getProtein() {
        return protein;
    }

    public float getCalorie() {
        return calorie;
    }

    /**
     * Gets the value of a specific requirement of the product.
     *
     * @param requirement The requirement type.
     * @return The value of the requirement.
     * @throws IllegalArgumentException if the requirement type is invalid.
     */
    public float getValueOfRequirement(PurchaseRequirement requirement) {
        switch (requirement) {
            case Protein:
                return getProtein();
            case Carb:
                return getCarbohydrate();
            case Fat:
                return getFat();
            case Calorie:
                return getCalorie();
            case Number:
            default:
                throw new IllegalArgumentException();
        }
    }
    //endregion
}
