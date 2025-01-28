package app;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Task2Class {

    private Task2Class() {}

    public static void start(String inputName, String outputName) {
        File inputFile = new File(inputName);
        File outputFile = new File(outputName);

        if (!inputFile.exists() || !outputFile.exists()) {
            throw new RuntimeException("file not exist!");
        }

        try (FileWriter writer = new FileWriter(outputFile, false)) {
            List<String> lines = Files.readAllLines(inputFile.toPath(), StandardCharsets.UTF_8);

            int topSize = Integer.parseInt(lines.getFirst());

            writer.write(findTopWords(lines.stream().skip(1).toList(), topSize).toString());
            writer.flush();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // метод подсчета встречаемости слов и их сортировки
    private static List<String> findTopWords(List<String> text, int topSize) {

        // сравнивает пары <String, Long> сначала по числу (Value), а затем лексикографически по строке (Key)
        Comparator<Map.Entry<String, Long>> wordsComparator = (elem1, elem2) ->
                (!elem1.getValue().equals(elem2.getValue())) ?
                        (Long.compare(elem2.getValue(), elem1.getValue())) :
                        (elem1.getKey().compareTo(elem2.getKey()));

        // разделяет текст на слова и группирует их по числу появлений в тексте
        Set<Map.Entry<String, Long>> wordsAndCountInText = text.stream()
                .flatMap(elem -> Arrays.stream(elem.split("\\W+")))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(elem -> elem, Collectors.counting()))
                .entrySet();

        // сортирует слова и выбирает первые n
        return wordsAndCountInText.stream()
                .sorted(wordsComparator)
                .limit(topSize)
                .map(Map.Entry::getKey)
                .toList();
    }
}
