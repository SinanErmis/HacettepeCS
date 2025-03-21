public class RemoveCommand implements Command {
    private final int barcode;
    private boolean removed = false;

    public RemoveCommand(int barcode) {
        this.barcode = barcode;
    }

    @Override
    public void execute() {
        // Remove item from inventory
        if (Inventory.getInstance().hasItemWithBarcode(barcode)) {
            Inventory.getInstance().removeItemWithBarcode(barcode);
            removed = true;
        }
    }

    @Override
    public String toString() {
        if (removed) {
            return "REMOVE RESULTS:\nItem is removed.\n" + Main.SEPERATOR + "\n";
        } else {
            return "REMOVE RESULTS:\nItem is not found.\n" + Main.SEPERATOR + "\n";
        }
    }
}
