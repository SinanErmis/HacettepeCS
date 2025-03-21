public class CommandParser {
    public Command parse(String commandLine){
        String[] tokens = commandLine.split("\t");
        String commandType = tokens[0];
        switch (commandType){
            case "ADD":
                Item item = parseItem(tokens);
                return new AddCommand(item);
            case "REMOVE":
                int barcodeToRemove = Integer.parseInt(tokens[1]);
                return new RemoveCommand(barcodeToRemove);
            case "SEARCHBYBARCODE":
                int barcodeToSearch = Integer.parseInt(tokens[1]);
                return new SearchByBarcodeCommand(barcodeToSearch);
            case "SEARCHBYNAME":
                String name = tokens[1];
                return new SearchByNameCommand(name);
            case "DISPLAY":
                return new DisplayCommand();
            default:
                return null;
        }
    }

    private Item parseItem(String[] tokens) throws IllegalArgumentException {
        Item.Type type = Item.Type.valueOf(tokens[1]);
        String name = tokens[2];
        int barcode = Integer.parseInt(tokens[4]);
        double price = Double.parseDouble(tokens[5]);

        // I know this looks useless, why bother using 3 different branches for same String type?
        // Because we don't know what the future holds, maybe in the future we need to change type of authors from
        // String to Author class, or maybe we need to change stationery kind from String to StationeryKind enum.
        switch (type){
            case Book:
                String author = tokens[3];
                return new Item<String>(type, name, author, barcode, price);
            case Toy:
                String color = tokens[3];
                return new Item<String>(type, name, color, barcode, price);
            case Stationery:
                String kind = tokens[3];
                return new Item<String>(type, name, kind, barcode, price);
        }
        throw new IllegalArgumentException("Invalid item type");
    }
}
