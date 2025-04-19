import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MaxScrollsDP {
    private ArrayList<ArrayList<Integer>> safesDiscovered = new ArrayList<>();
    // Input format will be the same as following:
    // Number of safes
    // [Complexity,Scroll] Pair
    // [Complexity,Scroll] Pair
    // .
    // .
    // .
    // [Complexity,Scroll] Pair
    // See example provided below:
    // 3
    // [5,10]
    // [10,10]
    // [5,20]

    public MaxScrollsDP(ArrayList<ArrayList<Integer>> safesDiscovered) {
        this.safesDiscovered = safesDiscovered;
    }

    public ArrayList<ArrayList<Integer>> getSafesDiscovered() {
        return safesDiscovered;
    }

    public OptimalScrollSolution optimalSafeOpeningAlgorithm() throws FileNotFoundException {
        int T = safesDiscovered.size();
        int maxKnowledge = 5 * T;
        int[][] dp = new int[T + 1][maxKnowledge + 1];

        for (int i = 0; i <= T; i++)
            for (int j = 0; j <= maxKnowledge; j++)
                dp[i][j] = Integer.MIN_VALUE;

        dp[0][0] = 0;

        for (int i = 1; i <= T; i++) {
            int ci = safesDiscovered.get(i - 1).get(0);
            int si = safesDiscovered.get(i - 1).get(1);

            for (int k = 0; k <= maxKnowledge; k++) {
                if (dp[i - 1][k] != Integer.MIN_VALUE) {
                    // Maintain state
                    dp[i][k] = Math.max(dp[i][k], dp[i - 1][k]);

                    // Generate knowledge
                    if (k + 5 <= maxKnowledge) {
                        dp[i][k + 5] = Math.max(dp[i][k + 5], dp[i - 1][k]);
                    }

                    // Open safe
                    if (k >= ci) {
                        if (dp[i - 1][k] + si > dp[i][k - ci]) {
                            dp[i][k - ci] = dp[i - 1][k] + si;
                        }
                    }
                }
            }
        }

        // Find max scrolls
        int maxScrolls = 0;
        for (int k = 0; k <= maxKnowledge; k++) {
            if (dp[T][k] > maxScrolls) {
                maxScrolls = dp[T][k];
            }
        }

        return new OptimalScrollSolution(safesDiscovered, maxScrolls);
    }
}
