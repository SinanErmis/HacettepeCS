import java.io.*;
import java.util.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

public class Main {

    public static void main(String[] args) {
        try {
            int[] A = readDataInColumn("TrafficFlowDataset.csv", 2);

            // ! ATTENTION, this array contains 3 additional calculations at the start. It's purpose is to make JVM warm-up
            // and going to be skipped when plotting
            // Update! Removed this logic to prevent confusion
            int[] inputSizes = {/*100, 200, 500,*/ 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
            //int[] inputsSkippingFirst = Arrays.stream(inputSizes).skip(3).toArray();

            SortingAlgorithm[] algorithms = {new CombSort(), new InsertionSort(), new ShakerSort(), new ShellSort(), new RadixSort() };
            String[] algorithmNames = {"Comb", "Insertion", "Shaker", "Shell", "Radix"};

            Map<SortingAlgorithm, List<Long>> algorithmToRandomTime = new HashMap<>();
            Map<SortingAlgorithm, List<Long>> algorithmToSortedTime = new HashMap<>();
            Map<SortingAlgorithm, List<Long>> algorithmToReverseTime = new HashMap<>();

            for (int i = 0; i < algorithms.length; i++) {
                List<Long> randomTimes = new ArrayList<>();
                List<Long> sortedTimes = new ArrayList<>();
                List<Long> reverseTimes = new ArrayList<>();

                for (int inputSize: inputSizes) {
                    // Acc stands for accumulation
                    long randomDurationAcc = 0;
                    long sortedDurationAcc = 0;
                    long reverseDurationAcc = 0;
                    final int TRY_COUNT = 10;

                    for (int tryIndex = 0; tryIndex < TRY_COUNT; tryIndex++) {
                        int[] input = Arrays.copyOf(A, inputSize);
                        long startTime = System.nanoTime();
                        algorithms[i].sort(input);
                        long endTime = System.nanoTime();
                        randomDurationAcc += (endTime - startTime);

                        startTime = System.nanoTime();
                        algorithms[i].sort(input);
                        endTime = System.nanoTime();
                        sortedDurationAcc += (endTime - startTime);

                        reverseArray(input);
                        startTime = System.nanoTime();
                        algorithms[i].sort(input);
                        endTime = System.nanoTime();
                        reverseDurationAcc += (endTime - startTime);
                    }

                    randomDurationAcc /= TRY_COUNT;
                    sortedDurationAcc /= TRY_COUNT;
                    reverseDurationAcc /= TRY_COUNT;

                    randomTimes.add(randomDurationAcc);
                    sortedTimes.add(sortedDurationAcc);
                    reverseTimes.add(reverseDurationAcc);
                    System.out.println(algorithmNames[i] + " [" + inputSize + "]\tRandom: " +randomDurationAcc + "\tSorted: " + sortedDurationAcc + "\tReverse: " + reverseDurationAcc + "\n");
                }

                // Remove the first 3 entry, which is used for JVM warm-up
                /*for (int j = 0; j < 3; j++) {
                    randomTimes.remove(0);
                    sortedTimes.remove(0);
                    reverseTimes.remove(0);
                }*/

               plotAlgorithm(inputSizes,
                        randomTimes,
                        sortedTimes,
                        reverseTimes,
                        algorithmNames[i]);

                algorithmToRandomTime.put(algorithms[i], randomTimes);
                algorithmToSortedTime.put(algorithms[i], sortedTimes);
                algorithmToReverseTime.put(algorithms[i], reverseTimes);
            }

            plotInputRandomness("Comparison Between Algorithms With Random Inputs", algorithmToRandomTime, inputSizes, algorithms, algorithmNames);
            plotInputRandomness("Comparison Between Algorithms With Sorted Inputs", algorithmToSortedTime, inputSizes, algorithms, algorithmNames);
            plotInputRandomness("Comparison Between Algorithms With Reverse Sorted Inputs", algorithmToReverseTime, inputSizes, algorithms, algorithmNames);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void plotInputRandomness(String title, Map<SortingAlgorithm, List<Long>> sortingAlgorithmToTimeList, int[] xAxis, SortingAlgorithm[] algorithms, String[] algorithmNames) throws IOException {
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();
        double[] xAxisAsDouble = Arrays.stream(xAxis).asDoubleStream().toArray();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        for (int i = 0; i < algorithms.length; i++) {
            chart.addSeries(algorithmNames[i], xAxisAsDouble, sortingAlgorithmToTimeList.get(algorithms[i]).stream().mapToDouble(Main::nsToMs).toArray());
        }
        BitmapEncoder.saveBitmap(chart, title, BitmapEncoder.BitmapFormat.PNG);
    }

    private static void plotAlgorithm(int[] xAxis, List<Long> randomTime, List<Long> sortedTime, List<Long> reverseTime, String name) throws IOException {
        XYChart chart = new XYChartBuilder().width(800).height(600).title(name)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();
        double[] xAxisAsDouble = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Random Input", xAxisAsDouble, randomTime.stream().mapToDouble(Main::nsToMs).toArray());
        chart.addSeries("Sorted Input", xAxisAsDouble, sortedTime.stream().mapToDouble(Main::nsToMs).toArray());
        chart.addSeries("Reverse Sorted Input", xAxisAsDouble, reverseTime.stream().mapToDouble(Main::nsToMs).toArray());

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, name + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        //new SwingWrapper(chart).displayChart();
    }

    private static double nsToMs(double d){
        return d / 1000000.0;
    }

    private static int[] readDataInColumn(String filePath, int columnIndex) throws IOException {
        List<Integer> values = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }

                String[] columns = line.split(",");
                if (columnIndex < columns.length) {
                    values.add(Integer.parseInt(columns[columnIndex].trim()));
                }
            }
        }

        return values.stream().mapToInt(i -> i).toArray();
    }

    private static void reverseArray(int[] arr){
        for (int i = 0; i < arr.length / 2; i++) {
            int t = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = t;
        }
    }
}
