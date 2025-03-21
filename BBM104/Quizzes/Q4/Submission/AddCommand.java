public class AddCommand implements Command {
    private final Item item;
    public AddCommand(Item item){
        this.item = item;
    }
    @Override
    public void execute() {
        Inventory.getInstance().add(item);
    }

    @Override
    public String toString() {
        // Add command should not be printed
        return "";
    }
}
