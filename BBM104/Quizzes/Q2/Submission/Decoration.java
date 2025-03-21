/**
 * Represents a decoration item used for classroom interiors.
 */
public class Decoration {
    private final String name;
    private final Type type;
    private final int price;
    private final int area;

    /**
     * Constructor for creating a new decoration item.
     *
     * @param name  The name of the decoration.
     * @param type  The type of the decoration (Paint, Tile, or Wallpaper).
     * @param price The price of the decoration.
     * @param area  The area covered by the decoration (in square units).
     */

    public Decoration(String name, Type type, int price, int area) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getArea() {
        return area;
    }

    /**
     * Represents the type of decoration (Paint, Tile, or Wallpaper).
     */
    public enum Type {
        Paint, Tile, Wallpaper
    }

    /**
     * Builder class for constructing Decoration objects.
     */
    public static class Builder {
        private String name;
        private Decoration.Type type;
        private int price, area;

        /**
         * Default constructor for the Builder.
         */
        public Builder() {
        }

        /**
         * Sets the name of the decoration.
         *
         * @param name The name of the decoration.
         * @return The Builder instance.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the type of the decoration.
         *
         * @param type The type of the decoration.
         * @return The Builder instance.
         */
        public Builder type(Decoration.Type type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the price of the decoration.
         *
         * @param price The price of the decoration.
         * @return The Builder instance.
         */
        public Builder price(int price) {
            this.price = price;
            return this;
        }

        /**
         * Sets the area covered by the decoration.
         *
         * @param area The area covered by the decoration.
         * @return The Builder instance.
         */
        public Builder area(int area) {
            this.area = area;
            return this;
        }

        /**
         * Constructs a new Decoration object based on the Builder's parameters.
         *
         * @return A new Decoration object.
         */
        public Decoration build() {
            return new Decoration(name, type, price, area);
        }

        /**
         * Resets the Builder's parameters to default values.
         */
        public void reset() {
            name = null;
            type = null;
            price = 0;
            area = 0;
        }
    }
}