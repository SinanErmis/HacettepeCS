public class DisplayCommand implements Command {
    @Override
    public void execute() {
        // This command does nothing but override it's toString method
    }

    @Override
    public String toString() {
        return Inventory.getInstance().toString();
    }
}
