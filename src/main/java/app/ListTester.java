package app;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class ListTester {

    private ListTester() {}

    public static void start(String inputName, String outputName) {
        File inputFile = new File(inputName);
        File outputFile = new File(outputName);

        if (!inputFile.exists() || !outputFile.exists()) {
            throw new RuntimeException("file not exist!");
        }

        try (FileWriter writer = new FileWriter(outputFile, false)) {
            String inputData = Files.readString(inputFile.toPath(), StandardCharsets.UTF_8);

            writer.write(test(inputData));
            writer.flush();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String test(String data) {
        StringBuilder builder = new StringBuilder();

        int[] array = Arrays.stream(data.split(" ")).mapToInt(Integer::parseInt).toArray();
        builder.append("Input array: ").append(Arrays.toString(array)).append(System.lineSeparator());

        List<Integer> receivedList = Arrays.stream(array).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        builder.append("Making List: ").append(receivedList).append(System.lineSeparator());

        Collections.sort(receivedList);
        builder.append("Natural sort: ").append(receivedList).append(System.lineSeparator());

        receivedList.sort(Collections.reverseOrder());
        builder.append("Reversed sort: ").append(receivedList).append(System.lineSeparator());

        Collections.shuffle(receivedList);
        builder.append("Shuffled list: ").append(receivedList).append(System.lineSeparator());

        Collections.rotate(receivedList, 1);
        builder.append("Shifted by 1 list: ").append(receivedList).append(System.lineSeparator());

        builder.append("Unique list: ").append(receivedList.stream()
                .filter(elem -> Collections.frequency(receivedList, elem) == 1).collect(Collectors.toSet())).append(System.lineSeparator());

        builder.append("Duplicates: ").append(receivedList.stream()
                .filter(elem -> Collections.frequency(receivedList, elem) > 1).collect(Collectors.toSet())).append(System.lineSeparator());

        builder.append("Making array: ").append(Arrays.toString(receivedList.toArray(new Integer[0]))).append(System.lineSeparator());

        return builder.toString();
    }
}