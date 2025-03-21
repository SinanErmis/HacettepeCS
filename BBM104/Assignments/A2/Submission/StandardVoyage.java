/**
 * Represents a Standard Voyage, a specific type of Voyage with standard seating arrangement.
 */
public class StandardVoyage extends Voyage {
    private static final int LEFT_SEATS_PER_ROW = 2;
    private static final int RIGHT_SEATS_PER_ROW = 2;
    private static final int SEATS_PER_ROW = LEFT_SEATS_PER_ROW + RIGHT_SEATS_PER_ROW;

    private final int refundCut;

    /**
     * Constructs a StandardVoyage with the specified parameters.
     * @param id Voyage ID.
     * @param from Starting location.
     * @param destination Destination location.
     * @param rows Number of rows of seats.
     * @param price Price per seat.
     * @param refundCut Refund cut percentage.
     */
    public StandardVoyage(int id, String from, String destination, int rows, double price, int refundCut) {
        super(id, from, destination, rows, price);
        this.refundCut = refundCut;
        seats = new boolean[rows * SEATS_PER_ROW]; // Initialize seats array for StandardVoyage
    }

    /**
     * Generates the seating plan for the Standard Voyage.
     * @param sb StringBuilder to build the seating plan.
     * @return StringBuilder with the formatted seating plan.
     */
    @Override
    protected StringBuilder getSeatingPlan(StringBuilder sb) {
        for (int row = 0; row < getRows(); row++) {
            // Append left seats (2 seats per row)
            for (int seat = 0; seat < LEFT_SEATS_PER_ROW; seat++) {
                boolean isSold = seats[row * SEATS_PER_ROW + seat];
                sb.append(isSold ? "X " : "* ");
            }
            sb.append("| "); // Separator between left and right seats
            // Append right seats (2 seats per row)
            for (int seat = LEFT_SEATS_PER_ROW; seat < SEATS_PER_ROW; seat++) {
                boolean isSold = seats[row * SEATS_PER_ROW + seat];
                if (seat == SEATS_PER_ROW - 1) {
                    sb.append(isSold ? 'X' : '*');
                } else {
                    sb.append(isSold ? "X " : "* ");
                }
            }
            sb.append('\n');
        }
        return sb;
    }

    /**
     * Calculates the revenue generated from currently sold seats on the Standard Voyage.
     * @return Total revenue from currently sold seats.
     */
    @Override
    protected double calculateCurrentlySoldSeatsRevenue() {
        double revenue = 0.0;
        for (boolean seat : seats) {
            if (seat) {
                revenue += getPrice();
            }
        }
        return revenue;
    }

    /**
     * Generates the initialization text describing the Standard Voyage.
     * @return Initialization text for the Standard Voyage.
     */
    @Override
    public String getInitText() {
        return String.format("Voyage %d was initialized as a standard (2+2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that refunds will be %d%s less than the paid amount.",
                getId(), getFrom(), getDestination(), getPrice(), getRows() * SEATS_PER_ROW, refundCut, "%");
    }

    /**
     * Retrieves the refund cut percentage applicable to the Standard Voyage.
     * @return Refund cut percentage.
     */
    @Override
    protected int getRefundCut() {
        return refundCut;
    }
}
