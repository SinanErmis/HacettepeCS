public class SearchByBarcodeCommand implements Command {
    private int barcode;

    public SearchByBarcodeCommand(int barcode) {
        this.barcode = barcode;
    }

    @Override
    public void execute() {
        // Execute does nothing
    }

    @Override
    public String toString() {
        if (!Inventory.getInstance().hasItemWithBarcode(barcode)) {
            return "SEARCH RESULTS:\nItem is not found.\n" + Main.SEPERATOR + "\n";
        }
        Item item = Inventory.getInstance().getItemWithBarcode(barcode);
        return "SEARCH RESULTS:\n" + item.toString() + "\n" + Main.SEPERATOR + "\n";
    }
}
