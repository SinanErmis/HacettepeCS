/**
 * Utility class for validating and parsing voyage-related data.
 */
public class VoyageUtilities {

    /**
     * Validates that the provided voyage ID is positive.
     * @param id Voyage ID to validate.
     * @throws IllegalArgumentException if the ID is not a positive integer.
     */
    public static void validateId(int id) {
        if (id <= 0) {
            String message = String.format("%d is not a positive integer, ID of a voyage must be a positive integer!", id);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that the provided number of seat rows is positive.
     * @param rows Number of seat rows to validate.
     * @throws IllegalArgumentException if the number of rows is not positive.
     */
    public static void validateRows(int rows) {
        if (rows <= 0) {
            String message = String.format("%d is not a positive integer, number of seat rows of a voyage must be a positive integer!", rows);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that the provided price is positive.
     * @param price Price to validate.
     * @throws IllegalArgumentException if the price is not positive.
     */
    public static void validatePrice(double price) {
        if (price <= 0) {
            String message = String.format("%.0f is not a positive number, price must be a positive number!", price);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that the provided refund cut percentage is within the range [0, 100].
     * @param refundCut Refund cut percentage to validate.
     * @throws IllegalArgumentException if the refund cut is not within the valid range.
     */
    public static void validateRefundCut(int refundCut) {
        if (refundCut < 0 || refundCut > 100) {
            String message = String.format("%d is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!", refundCut);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that the provided seat number is positive.
     * @param seatNumber Seat number to validate.
     * @throws IllegalArgumentException if the seat number is not positive.
     */
    public static void validateSeatNumber(int seatNumber) {
        if (seatNumber <= 0) {
            String message = String.format("%d is not a positive integer, seat number must be a positive integer!", seatNumber);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates that the provided premium fee percentage is non-negative and within the range [0, 100].
     * @param premiumFee Premium fee percentage to validate.
     * @throws IllegalArgumentException if the premium fee is not within the valid range.
     */
    public static void validatePremiumFee(int premiumFee) {
        if (premiumFee < 0 || premiumFee > 100) {
            String message = String.format("%d is not a non-negative integer, premium fee must be a non-negative integer!", premiumFee);
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Parses a string representation of voyage type into its enum value.
     * @param typeString String representation of voyage type.
     * @return Voyage.Type corresponding to the string representation.
     * @throws IllegalArgumentException if the typeString does not match any known type.
     */
    public static Voyage.Type parseType(String typeString) {
        try {
            return Enum.valueOf(Voyage.Type.class, typeString);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(Voyage.ERRONEOUS_USAGE_MESSAGE);
        }
    }

    /**
     * Validates the number of tokens based on the voyage type.
     * @param type Voyage type.
     * @param tokenCount Number of tokens in the command.
     * @throws IllegalArgumentException if the token count does not match the expected count for the given voyage type.
     */
    public static void validateTokenCount(Voyage.Type type, int tokenCount) {
        if (type == Voyage.Type.Minibus && tokenCount == 7) {
            return;
        }
        if (type == Voyage.Type.Standard && tokenCount == 8) {
            return;
        }
        if (type == Voyage.Type.Premium && tokenCount == 9) {
            return;
        }
        throw new IllegalArgumentException(Voyage.ERRONEOUS_USAGE_MESSAGE);
    }
}
