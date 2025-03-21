import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a booking system for managing voyages and ticket operations.
 */
public class BookingSystem {
    private final List<Voyage> voyages = new ArrayList<>();
    private String lastCommand = "";

    /**
     * Entry point of the program.
     * @param args Command line arguments (input and output file names).
     */
    public static void main(String[] args) {
        BookingSystem bookingSystem = new BookingSystem();
        bookingSystem.run(args[0], args[1]);
    }

    /**
     * Reads commands from input file and executes corresponding operations.
     * @param inputFileName Input file containing commands.
     * @param outputFileName Output file to write results and errors.
     */
    private void run(String inputFileName, String outputFileName) {
        String[] inputLines = FileUtilities.readFile(inputFileName, true, true);

        for (int i = 0; i < inputLines.length; i++) {
            FileUtilities.writeToFile(outputFileName, "COMMAND: " + inputLines[i], true, true);
            String[] tokens = inputLines[i].split("\t");

            String command = tokens[0];
            lastCommand = command;

            try {
                switch (command) {
                    case "INIT_VOYAGE":
                        handleInitVoyage(tokens, outputFileName);
                        break;
                    case "SELL_TICKET":
                        handleSellTicket(tokens, outputFileName);
                        break;
                    case "REFUND_TICKET":
                        handleRefundTicket(tokens, outputFileName);
                        break;
                    case "PRINT_VOYAGE":
                        handlePrintVoyage(tokens, outputFileName);
                        break;
                    case "CANCEL_VOYAGE":
                        handleCancelVoyage(tokens, outputFileName);
                        break;
                    case "Z_REPORT":
                        if (tokens.length != 1) {
                            throw new IllegalArgumentException(getErroneousUsageMessage(command));
                        }
                        printZReport(outputFileName, i == inputLines.length - 1);
                        break;
                    default:
                        String errorMessage = String.format("ERROR: There is no command namely %s!", command);
                        FileUtilities.writeToFile(outputFileName, errorMessage, true, true);
                        break;
                }
            } catch (NumberFormatException e) {
                FileUtilities.writeToFile(outputFileName, "ERROR: " + getErroneousUsageMessage(command), true, true);
            } catch (Exception e) {
                FileUtilities.writeToFile(outputFileName, "ERROR: " + e.getMessage(), true, true);
            }
        }

        if (!lastCommand.equals("Z_REPORT")) {
            printZReport(outputFileName, true);
        }
    }

    /**
     * Handles the initialization of a new voyage based on command parameters.
     * @param tokens Command tokens specifying voyage details.
     * @param outputFileName Output file to write results.
     */
    private void handleInitVoyage(String[] tokens, String outputFileName) {
        Voyage.Builder builder = new Voyage.Builder();
        Voyage.Type type = VoyageUtilities.parseType(tokens[1]);
        VoyageUtilities.validateTokenCount(type, tokens.length);
        builder.setType(type);

        int id = Integer.parseInt(tokens[2]);
        validateVoyageIdIsNotInUse(id);
        VoyageUtilities.validateId(id);
        builder.setId(id);

        builder.setFrom(tokens[3]);
        builder.setDestination(tokens[4]);

        int rows = Integer.parseInt(tokens[5]);
        VoyageUtilities.validateRows(rows);
        builder.setRows(rows);

        double price = Double.parseDouble(tokens[6]);
        VoyageUtilities.validatePrice(price);
        builder.setPrice(price);

        switch (type) {
            case Premium:
                int premiumFee = Integer.parseInt(tokens[8]);
                VoyageUtilities.validatePremiumFee(premiumFee);
                builder.setPremiumFee(premiumFee);
            case Standard:
                int refundCut = Integer.parseInt(tokens[7]);
                VoyageUtilities.validateRefundCut(refundCut);
                builder.setRefundCut(refundCut);
        }

        Voyage voyage = builder.build();
        FileUtilities.writeToFile(outputFileName, voyage.getInitText(), true, true);
        voyages.add(voyage);
    }

    /**
     * Handles the ticket selling operation for a specific voyage.
     * @param tokens Command tokens specifying the voyage ID and seats.
     * @param outputFileName Output file to write results.
     */
    private void handleSellTicket(String[] tokens, String outputFileName) {
        if (tokens.length != 3) {
            throw new IllegalArgumentException(getErroneousUsageMessage(tokens[0]));
        }

        int voyageId = Integer.parseInt(tokens[1]);
        VoyageUtilities.validateId(voyageId);
        int[] seats = Arrays.stream(tokens[2].split("_"))
                .mapToInt(Integer::parseInt)
                .peek(VoyageUtilities::validateSeatNumber)
                .toArray();

        FileUtilities.writeToFile(outputFileName, findVoyageById(voyageId).sellTicket(seats), true, true);
    }

    /**
     * Handles the ticket refund operation for a specific voyage.
     * @param tokens Command tokens specifying the voyage ID and seats.
     * @param outputFileName Output file to write results.
     */
    private void handleRefundTicket(String[] tokens, String outputFileName) {
        if (tokens.length != 3) {
            throw new IllegalArgumentException(getErroneousUsageMessage(tokens[0]));
        }

        int voyageId = Integer.parseInt(tokens[1]);
        VoyageUtilities.validateId(voyageId);
        int[] seats = Arrays.stream(tokens[2].split("_"))
                .mapToInt(Integer::parseInt)
                .peek(VoyageUtilities::validateSeatNumber)
                .toArray();

        FileUtilities.writeToFile(outputFileName, findVoyageById(voyageId).refundTicket(seats), true, true);
    }

    /**
     * Handles the printing of a voyage's report.
     * @param tokens Command tokens specifying the voyage ID.
     * @param outputFileName Output file to write results.
     */
    private void handlePrintVoyage(String[] tokens, String outputFileName) {
        if (tokens.length != 2) {
            throw new IllegalArgumentException(getErroneousUsageMessage(tokens[0]));
        }

        int voyageId = Integer.parseInt(tokens[1]);
        VoyageUtilities.validateId(voyageId);

        FileUtilities.writeToFile(outputFileName, findVoyageById(voyageId).getZReport(), true, true);
    }

    /**
     * Handles the cancellation of a specific voyage.
     * @param tokens Command tokens specifying the voyage ID.
     * @param outputFileName Output file to write results.
     */
    private void handleCancelVoyage(String[] tokens, String outputFileName) {
        if (tokens.length != 2) {
            throw new IllegalArgumentException(getErroneousUsageMessage(tokens[0]));
        }

        int voyageId = Integer.parseInt(tokens[1]);
        VoyageUtilities.validateId(voyageId);
        Voyage voyage = findVoyageById(voyageId);
        voyage.cancel();
        String s = String.format("Voyage %d was successfully cancelled!\nVoyage details can be found below:\n", voyageId);
        s += voyage.getZReport();
        voyages.remove(voyage);
        FileUtilities.writeToFile(outputFileName, s, true, true);
    }

    /**
     * Prints the Z Report for all voyages.
     * @param outputFileName Output file to write results.
     * @param isLast True if this is the last command in the sequence.
     */
    private void printZReport(String outputFileName, boolean isLast) {
        FileUtilities.writeToFile(outputFileName, "Z Report:\n----------------", true, true);
        if (voyages.isEmpty()) {
            FileUtilities.writeToFile(outputFileName, "No Voyages Available!\n" +
                    "----------------", true, !isLast);
        } else {
            Voyage[] sortedVoyages = voyages.stream().sorted(Comparator.comparingInt(Voyage::getId)).toArray(Voyage[]::new);
            for (int i = 0; i < sortedVoyages.length; i++) {
                FileUtilities.writeToFile(outputFileName, sortedVoyages[i].getZReport() + "\n----------------", true, !isLast || i != sortedVoyages.length - 1);
            }
        }
    }

    /**
     * Validates that a voyage ID is not already in use.
     * @param id Voyage ID to validate.
     */
    private void validateVoyageIdIsNotInUse(int id) {
        for (Voyage voyage : voyages) {
            if (voyage.getId() == id) {
                String message = String.format("There is already a voyage with ID of %d!", id);
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * Finds a voyage by its ID.
     * @param id Voyage ID to search for.
     * @return The voyage with the specified ID.
     * @throws IllegalArgumentException if no voyage with the specified ID is found.
     */
    private Voyage findVoyageById(int id) {
        for (Voyage voyage : voyages) {
            if (voyage.getId() == id) {
                return voyage;
            }
        }
        String message = String.format("There is no voyage with ID of %d!", id);
        throw new IllegalArgumentException(message);
    }

    /**
     * Generates an error message for an invalid command usage.
     * @param command The invalid command.
     * @return Error message for the invalid command.
     */
    private String getErroneousUsageMessage(String command) {
        return String.format("Erroneous usage of \"%s\" command!", command);
    }
}
