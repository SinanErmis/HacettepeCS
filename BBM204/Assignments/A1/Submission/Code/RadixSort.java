import java.util.Arrays;

public class RadixSort implements SortingAlgorithm {
    @Override
    public void sort(int[] A) {
        // Apparently, calculating max varies a lot depending on the status of the array etc. so, replaced with predefined max digit value
        //int maxDigits = getMaxDigits(A);
        radixSort(A, 9);
    }

    public void radixSort(int[] A, int maxDigits) {
        for (int pos = 0; pos < maxDigits; pos++) {
            A = countingSort(A, pos);
        }
    }

    private int[] countingSort(int[] A, int pos) {
        int[] count = new int[10]; // Assuming decimal digits (0-9)
        int[] output = new int[A.length];
        int size = A.length;

        // Counting
        for (int i = 0; i < size; i++) {
            int digit = getDigit(A[i], pos);
            count[digit]++;
        }

        // Addition
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Sorting
        for (int i = size - 1; i >= 0; i--) {
            int digit = getDigit(A[i], pos);
            count[digit]--;
            output[count[digit]] = A[i];
        }

        return output;
    }

    private static int getMaxDigits(int[] A) {
        int maxNumber = Arrays.stream(A).max().orElse(0);
        return Integer.toString(maxNumber).length();
    }

    private static int getDigit(int number, int pos) {
        return (number / (int) Math.pow(10, pos + 1)) % 10;
    }
}
