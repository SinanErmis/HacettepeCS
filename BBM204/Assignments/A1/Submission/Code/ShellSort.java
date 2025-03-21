public class ShellSort implements SortingAlgorithm {
    @Override
    public void sort(int[] A) {
        shellSort(A);
    }

    public void shellSort(int[] A) {
        int n = A.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = A[i];
                int j = i;

                while (j >= gap && A[j - gap] > temp) {
                    A[j] = A[j - gap];
                    j -= gap;
                }

                A[j] = temp;
            }
        }
    }
}
