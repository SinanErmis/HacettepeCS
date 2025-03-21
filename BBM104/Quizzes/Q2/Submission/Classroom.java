/**
 * Represents a classroom with specific dimensions and decorations.
 */

public class Classroom {
    private final String name;
    private final Type type;
    private final int width, length, height;
    private Decoration wallDecoration, floorDecoration;

    /**
     * Constructor for creating a new classroom.
     *
     * @param name   The name of the classroom.
     * @param type   The type of the classroom (Circle or Rectangle).
     * @param width  The width of the classroom.
     * @param length The length of the classroom.
     * @param height The height of the classroom.
     */

    public Classroom(String name, Type type, int width, int length, int height) {
        this.name = name;
        this.type = type;
        this.width = width;
        this.length = length;
        this.height = height;
    }

    /**
     * Sets the decorations for the classroom walls and floor.
     *
     * @param wallDecoration  The decoration for the walls.
     * @param floorDecoration The decoration for the floor.
     */
    public void setDecorations(Decoration wallDecoration, Decoration floorDecoration) {
        this.wallDecoration = wallDecoration;
        this.floorDecoration = floorDecoration;
    }

    public String getName() {
        return name;
    }


    /**
     * Represents the type of classroom (Circle or Rectangle).
     */
    public enum Type {
        Circle, Rectangle
    }

    private double calculateFloorArea() {
        if (type == Type.Rectangle) return width * length;
        return Math.PI * Math.pow(width / 2.0, 2);
    }

    private double calculateWallArea() {
        if (type == Type.Rectangle) return 2 * (width + length) * height;

        return (Math.PI * width * height);
    }

    private int calculateFloorTileCount() {
        return (int) Math.ceil(calculateFloorArea() / floorDecoration.getArea());
    }

    private int calculateWallTileCount() {
        return (int) Math.ceil(calculateWallArea() / wallDecoration.getArea());
    }

    private String getWallDecorationInfo() {
        Decoration.Type type = wallDecoration.getType();
        if (type == Decoration.Type.Tile) {
            return calculateWallTileCount() + " Tiles";
        }
        return (int) Math.ceil(calculateWallArea()) + "m2 of " + type.name();
    }

    private String getFloorDecorationInfo() {
        return calculateFloorTileCount() + " Tiles";
    }


    /**
     * Calculates the total cost of decorations for the classroom.
     *
     * @return The total cost in TL (Turkish Lira).
     */
    public int getTotalCost() {
        double cost = 0;

        Decoration.Type wallDecorationType = wallDecoration.getType();
        if (wallDecorationType == Decoration.Type.Tile) {
            cost += calculateWallTileCount() * wallDecoration.getPrice();
        } else {
            cost += calculateWallArea() * wallDecoration.getPrice();
        }

        cost += calculateFloorTileCount() * floorDecoration.getPrice();

        return (int) Math.ceil(cost);
    }


    /**
     * Generates a string representation of the classroom.
     *
     * @return A string describing the classroom and its decorations.
     */
    @Override
    public String toString() {
        return "Classroom " + name + " used " + getWallDecorationInfo() + " for walls and used " + getFloorDecorationInfo() + " for flooring, these costed " + getTotalCost() + "TL.";
    }

    public static class Builder {
        private String name;
        private Classroom.Type type;
        private int width, length, height;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(Classroom.Type type) {
            this.type = type;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder length(int length) {
            this.length = length;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Classroom build() {
            return new Classroom(name, type, width, length, height);
        }

        public void reset() {
            name = null;
            type = null;
            width = 0;
            length = 0;
            height = 0;
        }
    }
}
