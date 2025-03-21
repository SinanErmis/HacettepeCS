/**
 * Main class to run the gym meal vending machine simulation.
 */
public class Main {
    public static void main(String[] args) {
        // Usage: java8 Main Product.txt Purchase.txt GMMOutput.txt

        String[] productLines = FileUtilities.readFile(args[0], true, true);
        assert productLines != null;

        String[] purchaseLines = FileUtilities.readFile(args[1], true, true);
        assert purchaseLines != null;

        String outputFileName = args[2];
        StringBuilder outputBuilder = new StringBuilder();

        Machine machine = new Machine(outputBuilder);
        machine.loadUsingLines(productLines);
        machine.printCurrentSituation();
        machine.handlePurchases(purchaseLines);
        machine.printCurrentSituation();

        FileUtilities.writeToFile(outputFileName, outputBuilder.toString(), false, false);
    }
}