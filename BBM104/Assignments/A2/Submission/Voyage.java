import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents an abstract Voyage with basic functionality and abstract methods for specific voyage types.
 */
public abstract class Voyage {
    public static final String ERRONEOUS_USAGE_MESSAGE = "Erroneous usage of \"INIT_VOYAGE\" command!";
    private double revenueForRefunds = 0;

    public enum Type {
        Minibus, Standard, Premium
    }

    private final int id;
    private final String from, destination;
    private final int rows;
    private final double price;
    private boolean isCanceled;
    protected boolean[] seats;

    /**
     * Constructs a Voyage with the specified parameters.
     * @param id Voyage ID.
     * @param from Starting location.
     * @param destination Destination location.
     * @param rows Number of rows of seats.
     * @param price Price per seat.
     */
    public Voyage(int id, String from, String destination, int rows, double price) {
        this.id = id;
        this.from = from;
        this.destination = destination;
        this.rows = rows;
        this.price = price;
    }

    /**
     * Sells tickets for the specified seats.
     * @param seatsToSell Array of seat numbers to sell.
     * @return Confirmation message for the sold seats.
     */
    public String sellTicket(int[] seatsToSell) {
        for(int seat : seatsToSell) {
            if(seat <= 0 || seat > seats.length) {
                throw new IllegalArgumentException("There is no such a seat!");
            }
            if(seats[seat-1]) {
                throw new IllegalArgumentException("One or more seats already sold!");
            }
        }
        double price = 0.0;
        for(int seat : seatsToSell) {
            seats[seat-1] = true;
            price += getPriceOfSeat(seat-1);
        }

        String s = Arrays.stream(seatsToSell)
                .mapToObj(String::valueOf)  // Convert each integer to String
                .collect(Collectors.joining("-"));  // Join with "-"
        return String.format("Seat %s of the Voyage %d from %s to %s was successfully sold for %.2f TL.",
                s, getId(), getFrom(), getDestination(), price);
    }

    /**
     * Refunds tickets for the specified seats.
     * @param seatsToRefund Array of seat numbers to refund.
     * @return Confirmation message for the refunded seats.
     */
    public String refundTicket(int[] seatsToRefund) {
        for(int seat : seatsToRefund) {
            if(seat <= 0 || seat > seats.length) {
                throw new IllegalArgumentException("There is no such a seat!");
            }
            if(!seats[seat-1]) {
                throw new IllegalArgumentException("One or more seats are already empty!");
            }
        }
        double price = 0.0;
        for(int seat : seatsToRefund) {
            seats[seat-1] = false;
            double normalPrice = getPriceOfSeat(seat-1);
            double cut = normalPrice * (getRefundCut() / 100.0);
            price += (getPriceOfSeat(seat-1) - cut);
            revenueForRefunds += cut;
        }
        String s = Arrays.stream(seatsToRefund)
                .mapToObj(String::valueOf)  // Convert each integer to String
                .collect(Collectors.joining("-"));  // Join with "-"

        return String.format("Seat %s of the Voyage %d from %s to %s was successfully refunded for %.2f TL.",
                s, getId(), getFrom(), getDestination(), price);
    }

    /**
     * Cancels the voyage.
     */
    public void cancel() {
        isCanceled = true;
    }

    /**
     * Gets the ID of the voyage.
     * @return Voyage ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the starting location of the voyage.
     * @return Starting location.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Gets the destination of the voyage.
     * @return Destination.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Gets the number of rows of seats in the voyage.
     * @return Number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the price per seat of the voyage.
     * @return Price per seat.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Generates the Z Report for the voyage.
     * @return Z Report.
     */
    public String getZReport() {
        StringBuilder sb = new StringBuilder()
                .append("Voyage ").append(getId()).append("\n")
                .append(getFrom()).append('-').append(getDestination()).append('\n');

        double totalRevenue = (isCanceled ? 0.0 : calculateCurrentlySoldSeatsRevenue()) + revenueForRefunds;

        return getSeatingPlan(sb)
                .append("Revenue: ").append(String.format("%.2f", totalRevenue))
                .toString();
    }

    /**
     * Generates the seating plan of the voyage.
     * @param sb StringBuilder to build the seating plan.
     * @return StringBuilder with the formatted seating plan.
     */
    protected abstract StringBuilder getSeatingPlan(StringBuilder sb);

    /**
     * Calculates the revenue generated from currently sold seats.
     * @return Total revenue from currently sold seats.
     */
    protected abstract double calculateCurrentlySoldSeatsRevenue();

    /**
     * Gets the price of a specific seat.
     * @param seat Seat index.
     * @return Price of the seat.
     */
    protected double getPriceOfSeat(int seat) {
        return price;
    }

    /**
     * Gets the refund price of a specific seat.
     * @param seat Seat index.
     * @return Refund price of the seat.
     */
    protected double getRefundPriceOfSeat(int seat) {
        double normalPrice = getPriceOfSeat(seat);
        double cut = normalPrice * (getRefundCut() / 100.0);
        revenueForRefunds += cut;
        return normalPrice - cut;
    }

    /**
     * Gets the refund cut percentage.
     * @return Refund cut percentage.
     */
    protected abstract int getRefundCut();

    /**
     * Gets the initialization text for the voyage.
     * @return Initialization text.
     */
    public abstract String getInitText();

    /**
     * Builder class to construct specific types of Voyage based on Type.
     */
    public static class Builder {
        private Type type;
        private int id;
        private String from, destination;
        private int rows;
        private double price;
        private int refundCut;
        private int premiumFee;

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder setRows(int rows) {
            this.rows = rows;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setRefundCut(int refundCut) {
            this.refundCut = refundCut;
            return this;
        }

        public Builder setPremiumFee(int premiumFee) {
            this.premiumFee = premiumFee;
            return this;
        }

        /**
         * Builds a specific type of Voyage based on the configured parameters.
         * @return Constructed Voyage.
         * @throws IllegalArgumentException if an invalid type is specified.
         */
        public Voyage build() {
            if (type == Type.Minibus) {
                return new MinibusVoyage(id, from, destination, rows, price);
            } else if (type == Type.Standard) {
                return new StandardVoyage(id, from, destination, rows, price, refundCut);
            } else if (type == Type.Premium) {
                return new PremiumVoyage(id, from, destination, rows, price, refundCut, premiumFee);
            } else {
                throw new IllegalArgumentException("Invalid voyage type specified!");
            }
        }
    }
}
