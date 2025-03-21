public class Item<T> {
    public enum Type {
        Book,
        Toy,
        Stationery
    }

    private final Type type;
    private final String name;
    private final T specialProperty;
    private final int barcode;
    private final double price;

    public Item(Type type, String name, T specialProperty, int barcode, double price) {
        this.type = type;
        this.name = name;
        this.specialProperty = specialProperty;
        this.barcode = barcode;
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public T getSpecialProperty() {
        return specialProperty;
    }

    public int getBarcode() {
        return barcode;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        // There are some odd representations of the price in the output. To match it, i need to check if the floating part price is
        // divisible by 0.1, if it is, then the output should be formatted with only one decimal point, otherwise, it should be formatted with two decimal points.
        // To avoid rounding errors, I will multiply the price by 100 and check if the result is divisible by 10.
        // This code is so ugly, there are probably better ways to do this but I couldn't think of any.

        if(((price * 100) % 10) == 0){
            switch (type){
                case Book:
                    return String.format("Author of the %s is %s. Its barcode is %d and its price is %.1f", name, specialProperty, barcode, price);
                case Toy:
                    return String.format("Color of the %s is %s. Its barcode is %d and its price is %.1f", name, specialProperty, barcode, price);
                case Stationery:
                    return String.format("Kind of the %s is %s. Its barcode is %d and its price is %.1f", name, specialProperty, barcode, price);
            }
        }
        switch (type){
            case Book:
                return String.format("Author of the %s is %s. Its barcode is %d and its price is %.2f", name, specialProperty, barcode, price);
            case Toy:
                return String.format("Color of the %s is %s. Its barcode is %d and its price is %.2f", name, specialProperty, barcode, price);
            case Stationery:
                return String.format("Kind of the %s is %s. Its barcode is %d and its price is %.2f", name, specialProperty, barcode, price);
        }
        return "";
    }
}
