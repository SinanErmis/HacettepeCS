import java.io.File;

public class Main {
    public static void main(String[] args) {
        // DONE: Create an instance of AlienFlora and run readGenomes. After
        // that run commands for reading and evaluating evolution and
        // adaptation pairs. File name is given in first argument.
        File xmlFile = new File(args[0]);
        AlienFlora flora = new AlienFlora(xmlFile);
        flora.readGenomes();
        flora.evaluateEvolutions();
        flora.evaluateAdaptations();
    }
}
