package app;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

public class Task1Class {

    private Task1Class() {}

    public static void start(String inputName, String outputName) {
        File inputFile = new File(inputName);
        File outputFile = new File(outputName);

        if (!inputFile.exists() || !outputFile.exists()) {
            throw new RuntimeException("file not exist!");
        }

        try (FileWriter writer = new FileWriter(outputFile, false)) {
            int inputData = Integer.parseInt(Files.readString(inputFile.toPath(), StandardCharsets.UTF_8));

            writer.write(printStream(getGreyCodes(inputData), inputData));
            writer.flush();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // код создания последовательности Грея
    private static Stream<Integer> getGreyCodes(int bits) {
        int upperBound = 1 << bits;

        return Stream.iterate(0, i -> (i < upperBound - 1) ? (i + 1) : (0)).map(i -> (i ^ (i >> 1)));
    }

    // печать в файл 2 - х итераций полного цикла кодов Грея
    private static String printStream(Stream<Integer> stream, int bits) {
        return stream.limit((1L << bits) * 2).toList().toString();
    }
}