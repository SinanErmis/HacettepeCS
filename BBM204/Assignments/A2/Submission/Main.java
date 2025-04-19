import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws IOException {

        //* Safe-lock Opening Algorithm Below *

        System.out.println("##Initiate Operation Safe-lock##");

        String scrollsFilePath = args[0];
        BufferedReader br = new BufferedReader(new FileReader(scrollsFilePath));

        int numSafes = Integer.parseInt(br.readLine().trim());
        ArrayList<ArrayList<Integer>> safes = new ArrayList<>();

        for (int i = 0; i < numSafes; i++) {
            String line = br.readLine().trim();
            String[] parts = line.split(",");
            ArrayList<Integer> safe = new ArrayList<>();
            safe.add(Integer.parseInt(parts[0].trim())); // complexity
            safe.add(Integer.parseInt(parts[1].trim())); // scrolls
            safes.add(safe);
        }
        br.close();

        MaxScrollsDP scrollSolver = new MaxScrollsDP(safes);
        OptimalScrollSolution scrollResult = scrollSolver.optimalSafeOpeningAlgorithm();
        scrollResult.printSolution(scrollResult);

        System.out.println("##Operation Safe-lock Completed##");

        //* Operation Artifact Algorithm Below *

        System.out.println("##Initiate Operation Artifact##");

        String artifactFilePath = args[1];
        BufferedReader reader = new BufferedReader(new FileReader(artifactFilePath));
        String line = reader.readLine();
        reader.close();

        ArrayList<Integer> artifacts = new ArrayList<>();
        if (line != null && !line.isEmpty()) {
            String[] parts = line.split(",");
            for (String part : parts) {
                artifacts.add(Integer.parseInt(part.trim()));
            }
        }

        MinShipsGP shipPlanner = new MinShipsGP(artifacts);
        OptimalShipSolution shipSolution = shipPlanner.optimalArtifactCarryingAlgorithm();
        shipSolution.printSolution(shipSolution);

        System.out.println("##Operation Artifact Completed##");

    }
}