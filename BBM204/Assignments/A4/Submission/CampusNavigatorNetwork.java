import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CampusNavigatorNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageCartSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;
    public int numCartLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<CartLine> lines;

    /**
     * Write the necessary Regular Expression to extract string constants from the fileContent
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"(.*?)\"");
        Matcher m = p.matcher(fileContent);
        m.find();
        return m.group(1).trim();
    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from the fileContent
     * Your regular expression should support floating point numbers with an arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]*\\.?[0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Double.parseDouble(m.group(1));
    }

    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and separated by a comma
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
    }

    /**
     * Function to extract the cart lines from the fileContent by reading train line names and their 
     * respective stations.
     * @return List of CartLine instances
     */
    public List<CartLine> getCartLines(String fileContent) {

        // -------------------------------------------------------------------------------
        // I overengineered the hell out of it, still cannot pass tests related to this function.
        // Even though isolated tests of this function fail, other tests related to whole app works
        // It's like my 5th different approach to it. I gave up
        // -------------------------------------------------------------------------------

        List<CartLine> cartLines = new ArrayList<>();

        // Split content into lines
        String[] lines = fileContent.split("\\r?\\n");
        for (int i = 0; i < lines.length - 1; i++) {
            String nameLine = lines[i].trim();
            String stationLine = lines[i + 1].trim();

            if (nameLine.matches(".*cart_line_name.*") && stationLine.matches(".*cart_line_stations.*")) {
                String name = "";
                Matcher nameMatcher = Pattern.compile("cart_line_name\\s*=\\s*\"([^\"]*)\"").matcher(nameLine);
                if (nameMatcher.find()) {
                    name = nameMatcher.group(1);
                }

                List<Station> stationList = new ArrayList<>();
                Matcher stationMatcher = Pattern.compile("\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)").matcher(stationLine);
                int idx = 1;
                while (stationMatcher.find()) {
                    int x = Integer.parseInt(stationMatcher.group(1));
                    int y = Integer.parseInt(stationMatcher.group(2));
                    stationList.add(new Station(new Point(x, y), name + " Station " + idx++));
                }

                cartLines.add(new CartLine(name, stationList));
                i++; // Skip the next line since it's part of the current cart line
            }
        }

        return cartLines;
    }

    /**
     * Function to populate the given instance variables of this class by calling the functions above.
     */
    public void readInput(String filename) {

        try {
            String fileContent = new String(Files.readAllBytes(new File(filename).toPath()));

            this.numCartLines = getIntVar("num_cart_lines", fileContent);
            this.startPoint = new Station(getPointVar("starting_point", fileContent),"Starting Point" );
            this.destinationPoint = new Station(getPointVar("destination_point", fileContent), "Final Destination");

            //--------------------------------------------------------------------------------------------
            // It's so obscure that we need to convert it to meters/minutes before storing value.
            // It's impossible to guess without checking piazza
            // It's a minor inconvenience nevertheless
            //----------------------------------------------------------------------------------------------
            this.averageCartSpeed = getDoubleVar("average_cart_speed", fileContent) * 1000 / 60.0;

            this.lines = getCartLines(fileContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
