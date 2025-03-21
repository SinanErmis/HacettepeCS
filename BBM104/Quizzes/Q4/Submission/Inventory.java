import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory {
    private static Inventory instance;
    private List<Item> items = new ArrayList<>();

    private Inventory() {
    }

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }

    public void add(Item item) {
        items.add(item);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("INVENTORY:\n");
        List<Item> items = this.items.stream().sorted(Comparator.comparing(Item::getType)).collect(Collectors.toList());
        for (Item item : items) {
            sb.append(item.toString());
            sb.append("\n");
        }
        sb.append(Main.SEPERATOR).append('\n');
        return sb.toString();
    }

    public boolean hasItemWithName(String name) {
        return items.stream().anyMatch(item -> item.getName().equals(name));
    }

    public boolean hasItemWithBarcode(int barcode) {
        return items.stream().anyMatch(item -> item.getBarcode() == barcode);
    }

    public void removeItemWithBarcode(int barcode) {
        items.removeIf(item -> item.getBarcode() == barcode);
    }

    public Item getItemWithBarcode(int barcode) {
        return items.stream().filter(item -> item.getBarcode() == barcode).findFirst().orElse(null);
    }

    public Item getItemWithName(String name) {
        return items.stream().filter(item -> item.getName().equals(name)).findFirst().orElse(null);
    }

    public void clear() {
        items.clear();
    }
}
