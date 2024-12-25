package app;

import app.extra.Person;
import app.extra.Student;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class App {

    private static final String inputName = "resources/input.txt";
    private static final String outputName = "resources/output.txt";

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
        System.out.println(data);

        Predicate<Person> condition = (person) -> person.getAge() >= 18;
        Function<Person, String> ifTrue = Person::getAddress;
        Function<Student, String> ifFalse = Student::getStudyAddress;

        Function<Student, String> operator = ternaryOperator(condition, ifTrue, ifFalse);

        StringBuilder builder = new StringBuilder();
        List<Student> students = getStudents(data);

        for (Student student: students) {
            builder.append(operator.apply(student)).append(System.lineSeparator());
        }

        return builder.toString();
    }

    private static List<Student> getStudents(String data) {
        List<Student> students = new ArrayList<>();

        for (String properties: data.split(System.lineSeparator())) {
            try {
                List<String> fields = Arrays.stream(properties.split(" ")).toList();

                Student student = new Student(
                        fields.get(0),
                        fields.get(1),
                        Integer.parseInt(fields.get(2)),
                        fields.get(3),
                        fields.get(4),
                        Integer.parseInt(fields.get(5))
                );

                students.add(student);
            } catch (Exception ignored) {}
        }

        return students;
    }

    private static <T, R> Function<T, R> ternaryOperator(Predicate<? super T> condition,
                                                        Function<? super T, R> ifTrue,
                                                        Function<? super T, R> ifFalse) {

        return (param) ->
                (condition.test(param)) ? (ifTrue.apply(param)) : (ifFalse.apply(param));
    }
}