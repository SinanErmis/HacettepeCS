public class CombSort implements SortingAlgorithm {
    @Override
    public void sort(int[] A) {
        combSort(A);
    }

    public void combSort(int[] A) {
        int gap = A.length;
        final double SHRINK = 1.3;
        boolean sorted = false;

        while (!sorted) {
            gap = (int) Math.floor(gap / SHRINK);
            if (gap < 1) {
                gap = 1;
            }
            sorted = (gap == 1);

            for (int i = 0; i + gap < A.length; i++) {
                if (A[i] > A[i + gap]) {
                    swap(A, i, i + gap);
                    sorted = false;
                }
            }
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}