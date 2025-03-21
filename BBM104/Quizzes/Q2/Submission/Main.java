import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main class responsible for managing classroom and decoration data.
 */
public class Main {
    private static final String CLASSROOM_LINE_START = "CLASSROOM";

    /**
     * Entry point for the application.
     *
     * @param args Command-line arguments: [itemsFileName] [decorationFileName] [outputFileName]
     */
    public static void main(String[] args) {
        String itemsFileName = args[0];
        String decorationFileName = args[1];
        String outputFileName = args[2];

        // Read item and decoration data from files
        String[] itemLines = FileUtilities.readFile(itemsFileName, true, true);
        assert itemLines != null;
        String[] decorationLines = FileUtilities.readFile(decorationFileName, true, true);
        assert decorationLines != null;

        // Initialize builders for classrooms and decorations
        Classroom.Builder classroomBuilder = new Classroom.Builder();
        Decoration.Builder decorationBuilder = new Decoration.Builder();

        // Lists to hold classroom and decoration objects
        List<Classroom> classrooms = new ArrayList<>();
        List<Decoration> decorations = new ArrayList<>();

        // Process item lines to create classrooms or decorations
        for (String line : itemLines) {
            String[] parts = Arrays.stream(line.split("\t")).map(String::trim).toArray(String[]::new);

            if (parts[0].equals(CLASSROOM_LINE_START)) {
                Classroom classroom = createClassroom(parts, classroomBuilder);
                classrooms.add(classroom);
            } else {
                Decoration decoration = createDecoration(parts, decorationBuilder);
                decorations.add(decoration);
            }
        }

        // Assign decorations to classrooms based on decoration lines
        for (String line : decorationLines) {
            String[] parts = Arrays.stream(line.split("\t")).map(String::trim).toArray(String[]::new);
            String classroomName = parts[0];
            String wallDecorationName = parts[1];
            String floorDecorationName = parts[2];

            Classroom classroom = classrooms.stream()
                    .filter(c -> c.getName().equals(classroomName))
                    .findFirst()
                    .orElse(null);

            Decoration wallDecoration = decorations.stream()
                    .filter(d -> d.getName().equals(wallDecorationName))
                    .findFirst()
                    .orElse(null);

            Decoration floorDecoration = decorations.stream()
                    .filter(d -> d.getName().equals(floorDecorationName))
                    .findFirst()
                    .orElse(null);

            if (classroom != null && wallDecoration != null && floorDecoration != null) {
                classroom.setDecorations(wallDecoration, floorDecoration);
            }
        }

        // Calculate total cost and write classroom info to output file
        int totalCost = 0;
        for (Classroom c : classrooms) {
            FileUtilities.writeToFile(outputFileName, c.toString(), true, true);
            totalCost += c.getTotalCost();
        }

        // Write total cost to the end of the output file
        String totalPriceText = "Total price is: " + totalCost + "TL.";
        FileUtilities.writeToFile(outputFileName, totalPriceText, true, false);
    }

    /**
     * Creates a Decoration object based on the provided data parts.
     *
     * @param parts             Data parts representing the decoration.
     * @param decorationBuilder The builder used to construct the Decoration object.
     * @return A new Decoration object.
     */
    private static Decoration createDecoration(String[] parts, Decoration.Builder decorationBuilder) {
        String name = parts[1];
        Decoration.Type type = Decoration.Type.valueOf(parts[2]);
        int price = Integer.parseInt(parts[3]);

        if (type == Decoration.Type.Tile) {
            int area = Integer.parseInt(parts[4]);
            decorationBuilder.area(area);
        }

        Decoration decoration = decorationBuilder.name(name).type(type).price(price).build();
        decorationBuilder.reset();
        return decoration;
    }

    /**
     * Creates a Classroom object based on the provided data parts.
     *
     * @param parts            Data parts representing the classroom.
     * @param classroomBuilder The builder used to construct the Classroom object.
     * @return A new Classroom object.
     */
    private static Classroom createClassroom(String[] parts, Classroom.Builder classroomBuilder) {
        String name = parts[1];
        Classroom.Type type = Classroom.Type.valueOf(parts[2]);
        int width = Integer.parseInt(parts[3]);
        int length = Integer.parseInt(parts[4]);
        int height = Integer.parseInt(parts[5]);

        Classroom classroom = classroomBuilder
                .name(name)
                .type(type)
                .width(width)
                .length(length)
                .height(height)
                .build();
        classroomBuilder.reset();
        return classroom;
    }
}
