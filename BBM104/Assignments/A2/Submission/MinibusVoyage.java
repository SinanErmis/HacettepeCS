/**
 * Represents a Minibus voyage, a specific type of Voyage.
 */
public class MinibusVoyage extends Voyage {
    private static final int SEATS_PER_ROW = 2; // Number of seats per row in a Minibus

    /**
     * Constructs a MinibusVoyage with the specified parameters.
     * @param id Voyage ID.
     * @param from Starting location.
     * @param destination Destination location.
     * @param rows Number of rows of seats.
     * @param price Price per seat.
     */
    public MinibusVoyage(int id, String from, String destination, int rows, double price) {
        super(id, from, destination, rows, price);
        seats = new boolean[rows * SEATS_PER_ROW]; // Initialize seats array for the Minibus
    }

    /**
     * Generates the seating plan for the Minibus voyage.
     * @param sb StringBuilder to build the seating plan.
     * @return StringBuilder with the formatted seating plan.
     */
    @Override
    protected StringBuilder getSeatingPlan(StringBuilder sb) {
        for (int row = 0; row < getRows(); row++) {
            for (int seat = 0; seat < SEATS_PER_ROW; seat++) {
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
     * Calculates the revenue generated from currently sold seats on the Minibus.
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
     * Generates the initialization text describing the Minibus voyage.
     * @return Initialization text for the Minibus voyage.
     */
    @Override
    public String getInitText() {
        return String.format("Voyage %d was initialized as a minibus (2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that minibus tickets are not refundable.",
                getId(), getFrom(), getDestination(), getPrice(), getRows() * SEATS_PER_ROW);
    }

    /**
     * Refunds tickets on the Minibus voyage (unsupported operation).
     * @param seats Array of seat numbers to refund.
     * @return Error message stating that Minibus tickets are not refundable.
     */
    @Override
    public String refundTicket(int[] seats) {
        throw new UnsupportedOperationException("Minibus tickets are not refundable!");
    }

    /**
     * Gets the refund cut percentage for the Minibus voyage (unsupported operation).
     * @return Error message stating that Minibus tickets are not refundable.
     */
    @Override
    protected int getRefundCut() {
        throw new UnsupportedOperationException("Minibus tickets are not refundable!");
    }
}
