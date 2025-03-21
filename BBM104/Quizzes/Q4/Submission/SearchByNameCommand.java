public class SearchByNameCommand implements Command {
    private final String name;

    public SearchByNameCommand(String name) {
        this.name = name;
    }

    @Override
    public void execute() {
        // Execute does nothing
    }

    @Override
    public String toString() {
        if (!Inventory.getInstance().hasItemWithName(name)) {
            return "SEARCH RESULTS:\nItem is not found.\n" + Main.SEPERATOR + "\n";
        }
        Item item = Inventory.getInstance().getItemWithName(name);
        return "SEARCH RESULTS:\n" + item.toString() + "\n" + Main.SEPERATOR + "\n";
    }
}
