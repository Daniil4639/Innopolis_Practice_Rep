package app;

import app.test_clesses.Car;
import app.test_clesses.Person;
import app.test_clesses.School;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SetTester {

    private SetTester() {}

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

    public static String test(String data) {
        StringBuilder builder = new StringBuilder();

        Set<String> receiverSet = new HashSet<>();
        Collections.addAll(receiverSet, data.split(System.lineSeparator()));
        builder.append("Making set: ").append(receiverSet).append(System.lineSeparator());

        receiverSet.add(new Person("Андрей", "Лимонов", 19).toString());
        receiverSet.add(new Car("Fabia", "Skoda", 4_000_000L).toString());
        receiverSet.add(new School("Some School, 1", 1987, "Some Street, 1", 1500).toString());
        receiverSet.add(new Person("Иван", "Андросов", 43).toString());
        receiverSet.add(new Car("iX", "BMW", 12_000_000L).toString());
        builder.append("Adding items: ").append(receiverSet).append(System.lineSeparator());

        builder.append("Printing set: ").append(System.lineSeparator());
        for (String elem: receiverSet) {
            builder.append("\t").append(elem).append(System.lineSeparator());
        }

        builder.append("Old size = ").append(receiverSet.size()).append(System.lineSeparator());
        receiverSet.add("sixth string");
        builder.append("Adding 'sixth string' to set and check: ").append(receiverSet).append(System.lineSeparator());
        builder.append("New size = ").append(receiverSet.size()).append(" -> element wasn't inserted").append(System.lineSeparator());

        receiverSet.remove("sixth string");
        builder.append("Set contains 'sixth string'? - ").append(receiverSet.contains("sixth string")).append(System.lineSeparator());

        builder.append("Now set size is ").append(receiverSet.size()).append(System.lineSeparator());

        receiverSet.clear();
        builder.append("Now set is empty? - ").append(receiverSet.isEmpty()).append(System.lineSeparator());

        return builder.toString();
    }
}
