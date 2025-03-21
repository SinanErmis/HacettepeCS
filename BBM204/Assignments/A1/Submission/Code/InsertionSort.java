public class InsertionSort implements SortingAlgorithm {
    @Override
    public void sort(int[] A) {
        insertionSort(A);
    }

    public void insertionSort(int[] A) {
        int n = A.length;
        for (int j = 1; j < n; j++) {
            int key = A[j];
            int i = j - 1;

            while (i >= 0 && A[i] > key) {
                A[i + 1] = A[i];
                i = i - 1;
            }
            A[i + 1] = key;
        }
    }
}
