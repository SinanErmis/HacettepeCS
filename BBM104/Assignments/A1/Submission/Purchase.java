import java.util.Arrays;

/**
 * Represents a purchase made by a user.
 */
public class Purchase {
    private final String paymentType;
    private final int[] bills;
    private final PurchaseRequirement requirement;
    private final int requirementAmount;

    /**
     * Constructs a purchase with the given details.
     *
     * @param paymentType       The payment type used for the purchase.
     * @param bills             The bills used for payment.
     * @param requirement       The requirement type for the purchase.
     * @param requirementAmount The amount required to fulfill the purchase requirement.
     */
    public Purchase(String paymentType, int[] bills, PurchaseRequirement requirement, int requirementAmount) {
        this.paymentType = paymentType;
        this.bills = bills;
        this.requirement = requirement;
        this.requirementAmount = requirementAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public int[] getBills() {
        return bills;
    }

    /**
     * Gets the total payment amount made with the bills.
     *
     * @return The total payment amount.
     */
    public int getPaymentAmount() {
        return Arrays.stream(bills).sum();
    }

    public PurchaseRequirement getRequirement() {
        return requirement;
    }

    public int getRequirementAmount() {
        return requirementAmount;
    }
}
