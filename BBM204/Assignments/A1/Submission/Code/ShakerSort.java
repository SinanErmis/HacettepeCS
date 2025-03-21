public class ShakerSort implements SortingAlgorithm {
    @Override
    public void sort(int[] A) {
        shakerSort(A);
    }

    public void shakerSort(int[] A) {
        boolean swapped = true;
        int start = 0;
        int end = A.length - 1;

        while (swapped) {
            swapped = false;

            // Forward pass
            for (int i = start; i < end; i++) {
                if (A[i] > A[i + 1]) {
                    swap(A, i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped) break;

            swapped = false;
            end--;

            // Backward pass
            for (int i = end; i > start; i--) {
                if (A[i] < A[i - 1]) {
                    swap(A, i, i - 1);
                    swapped = true;
                }
            }

            start++;
        }
    }

    private void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }
}
