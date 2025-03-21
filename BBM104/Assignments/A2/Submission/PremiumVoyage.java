/**
 * Represents a Premium Voyage, a specific type of Voyage that includes premium and standard seats.
 */
public class PremiumVoyage extends Voyage {
    private static final int PREMIUM_SEATS_PER_ROW = 1;
    private static final int STANDARD_SEATS_PER_ROW = 2;
    private static final int SEATS_PER_ROW = PREMIUM_SEATS_PER_ROW + STANDARD_SEATS_PER_ROW;

    private final int refundCut;
    private final int premiumFee;

    /**
     * Constructs a PremiumVoyage with the specified parameters.
     * @param id Voyage ID.
     * @param from Starting location.
     * @param destination Destination location.
     * @param rows Number of rows of seats.
     * @param price Price per standard seat.
     * @param refundCut Refund cut percentage.
     * @param premiumFee Premium fee percentage.
     */
    public PremiumVoyage(int id, String from, String destination, int rows, double price, int refundCut, int premiumFee) {
        super(id, from, destination, rows, price);
        this.refundCut = refundCut;
        this.premiumFee = premiumFee;
        seats = new boolean[rows * SEATS_PER_ROW]; // Initialize seats array for PremiumVoyage
    }

    /**
     * Generates the seating plan for the Premium Voyage, distinguishing between premium and standard seats.
     * @param sb StringBuilder to build the seating plan.
     * @return StringBuilder with the formatted seating plan.
     */
    @Override
    protected StringBuilder getSeatingPlan(StringBuilder sb) {
        for (int row = 0; row < getRows(); row++) {
            for (int seat = 0; seat < PREMIUM_SEATS_PER_ROW; seat++) {
                sb.append(seats[row * SEATS_PER_ROW + seat] ? "X " : "* ");
            }
            sb.append("| ");
            for (int seat = PREMIUM_SEATS_PER_ROW; seat < SEATS_PER_ROW; seat++) {
                if (seat == SEATS_PER_ROW - 1) {
                    sb.append(seats[row * SEATS_PER_ROW + seat] ? 'X' : '*');
                } else {
                    sb.append(seats[row * SEATS_PER_ROW + seat] ? "X " : "* ");
                }
            }
            sb.append('\n');
        }
        return sb;
    }

    /**
     * Calculates the revenue generated from currently sold seats on the Premium Voyage.
     * @return Total revenue from currently sold seats.
     */
    @Override
    protected double calculateCurrentlySoldSeatsRevenue() {
        double revenue = 0.0;
        for (int row = 0; row < getRows(); row++) {
            for (int seat = 0; seat < PREMIUM_SEATS_PER_ROW; seat++) {
                if (seats[row * SEATS_PER_ROW + seat]) {
                    revenue += getPremiumPrice();
                }
            }
            for (int seat = PREMIUM_SEATS_PER_ROW; seat < SEATS_PER_ROW; seat++) {
                if (seats[row * SEATS_PER_ROW + seat]) {
                    revenue += getPrice();
                }
            }
        }
        return revenue;
    }

    /**
     * Retrieves the price of a specific seat on the Premium Voyage.
     * @param seat Seat number.
     * @return Price of the specified seat (premium or standard).
     */
    @Override
    protected double getPriceOfSeat(int seat) {
        return (seat % SEATS_PER_ROW < PREMIUM_SEATS_PER_ROW) ? getPremiumPrice() : getPrice();
    }

    /**
     * Computes the premium price based on the standard price and premium fee percentage.
     * @return Calculated premium price.
     */
    private double getPremiumPrice() {
        return getPrice() * (1.0 + (premiumFee / 100.0));
    }

    /**
     * Generates the initialization text describing the Premium Voyage.
     * @return Initialization text for the Premium Voyage.
     */
    @Override
    public String getInitText() {
        double price = getPrice();
        double premiumPrice = price * (1.0 + (premiumFee / 100.0));
        return String.format("Voyage %d was initialized as a premium (1+2) voyage from %s to %s with " +
                        "%.2f TL priced %d regular seats and %.2f TL priced %d " +
                        "premium seats. Note that refunds will be %d%s less than the paid amount.",
                getId(), getFrom(), getDestination(), price, getRows() * STANDARD_SEATS_PER_ROW,
                premiumPrice, getRows() * PREMIUM_SEATS_PER_ROW, refundCut, "%");
    }

    /**
     * Retrieves the refund cut percentage applicable to the Premium Voyage.
     * @return Refund cut percentage.
     */
    @Override
    public int getRefundCut() {
        return refundCut;
    }

}
