package app;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

    private static final String inputName = "resources/map_input.txt";
    private static final String outputName = "resources/map_output.txt";

    public static void main(String[] args) {
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

        Map<Integer, String> map = Stream.of(data.split(System.lineSeparator()))
                .map(elem -> elem.split(" "))
                .collect(Collectors.toMap(elem -> Integer.parseInt(elem[0]), elem -> elem[1]));
        builder.append("Reading map: ").append(map).append(System.lineSeparator());

        map.put(-882, "second_string");
        map.put(98, "eighth_string");
        map.put(32, "first_string");
        map.put(-1, "eleventh_string");
        map.put(75, "fifth_string");
        builder.append("Adding some values: ").append(map).append(System.lineSeparator());

        builder.append("Iterating in map:").append(System.lineSeparator());
        for (Map.Entry<Integer, String> entry: map.entrySet()) {
            builder.append("\t").append(entry).append(System.lineSeparator());
        }

        map.put(932, "some_string");
        builder.append("Adding key duplicate 932").append(System.lineSeparator());

        builder.append("Making set with all keys: ").append(map.keySet()).append(System.lineSeparator());

        Set<String> values = Arrays.stream(map.values().toArray())
                .map(elem -> (String) elem)
                .collect(Collectors.toSet());
        builder.append("Making set of all values: ").append(values).append(System.lineSeparator());

        builder.append("Unique values count: ").append(
                values.stream().filter(elem -> Collections.frequency(values, elem) == 1).count()
        ).append(System.lineSeparator());

        builder.append("Is map contains a key '40'? - ").append(map.containsKey(40)).append(System.lineSeparator());

        builder.append("Is map contains a value 'fourteenth_string'? - ")
                .append(map.containsValue("fourteenth_string")).append(System.lineSeparator());

        builder.append("Map size is ").append(map.size()).append(System.lineSeparator());

        map.remove(2891, "eighth_string");
        builder.append("Element '2891 - eighth_string' was removed!").append(System.lineSeparator());

        builder.append("Final result: ").append(map);

        return builder.toString();
    }
}
