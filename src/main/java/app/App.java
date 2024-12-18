package app;

import app.figures.Circle;
import app.figures.Ellipse;
import app.figures.Rectangle;
import app.figures.Square;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class App {

    private static final String inputName = "inputFile.txt";
    private static final String outputName = "outputFile.txt";

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        File inputFile = new File(Objects.requireNonNull(App.class.getClassLoader().getResource(inputName)).getFile());
        File outputFile = new File(Objects.requireNonNull(App.class.getClassLoader().getResource(outputName)).getFile());

        try (FileReader reader = new FileReader(inputFile.getPath());
             FileWriter writer = new FileWriter(outputFile.getPath(), false)) {

            JSONArray arr = (JSONArray) parser.parse(reader);

            for (Object obj: arr) {
                JSONObject figure = (JSONObject) obj;
                analyzeFigure(writer, figure);
            }
            writer.flush();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void analyzeFigure(FileWriter writer, JSONObject figure) throws IOException {
        String name = (String) figure.get("name");
        List<Double> metrics = Arrays.stream(((String) figure.get("metrics"))
                .split(" "))
                .map(Double::parseDouble).toList();

        switch (name) {
            case "Rectangle" -> TestFigures.testRectangle(writer,
                    new Rectangle(metrics.get(0), metrics.get(1), metrics.get(2), metrics.get(3)));
            case "Square" -> TestFigures.testSquare(writer,
                    new Square(metrics.get(0), metrics.get(1), metrics.get(2)));
            case "Ellipse" -> TestFigures.testEllipse(writer,
                    new Ellipse(metrics.get(0), metrics.get(1), metrics.get(2), metrics.get(3)));
            case "Circle" -> TestFigures.testCircle(writer,
                    new Circle(metrics.get(0), metrics.get(1), metrics.get(2)));
        }
    }
}