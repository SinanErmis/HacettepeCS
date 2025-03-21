import java.util.Locale;

public class Main {
    public static final String SEPERATOR = "------------------------------";
    public static void main(String[] args){

        //Here is pretty straightforward, we are just reading the commands from the file and executing them
        //The commands are parsed and executed in the order they are read from the file

        //To prevent any issues with the decimal separator, we set the default locale to US
        Locale.setDefault(Locale.US);

        String commandsPath = args[0];
        String outputPath = args[1];

        String[] commands = FileUtilities.readFile(commandsPath, true, true);
        if(commands == null){
            throw new IllegalArgumentException("Invalid input file");
        }

        StringBuilder output = new StringBuilder();

        CommandParser commandParser = new CommandParser();
        for (String commandLine : commands) {
            Command command = commandParser.parse(commandLine);
            command.execute();
            output.append(command.toString());
        }
        FileUtilities.writeToFile(outputPath, output.toString(), false, false);
    }
}
