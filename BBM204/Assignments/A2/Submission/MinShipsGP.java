import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MinShipsGP {
    private final ArrayList<Integer> artifactsFound = new ArrayList<>();
    // Weight of artifacts as list will be provided in the input file, and the list
    // should be populated using this format.
    // [3,2,3,4,5,4]

    public ArrayList<Integer> getArtifactsFound() {
        return artifactsFound;
    }

    MinShipsGP(ArrayList<Integer> artifactsFound) {
        this.artifactsFound.addAll(artifactsFound);
    }

    public OptimalShipSolution optimalArtifactCarryingAlgorithm() throws FileNotFoundException {
        // Sort artifacts in descending order
        ArrayList<Integer> sortedArtifacts = new ArrayList<>(artifactsFound);
        Collections.sort(sortedArtifacts, Collections.reverseOrder());

        ArrayList<Integer> shipLoads = new ArrayList<>();

        // Greedily pack each artifact
        for (int artifact : sortedArtifacts) {
            boolean placed = false;

            // Try placing in existing ships
            for (int i = 0; i < shipLoads.size(); i++) {
                if (shipLoads.get(i) + artifact <= 100) { //Constant max capacity for ships
                    shipLoads.set(i, shipLoads.get(i) + artifact);
                    placed = true;
                    break;
                }
            }

            // If it doesn't fit, open a new ship
            if (!placed) {
                shipLoads.add(artifact);
            }
        }

        return new OptimalShipSolution(artifactsFound, shipLoads.size());
    }
}
