package app;

import app.figures.Circle;
import app.figures.Ellipse;
import app.figures.Rectangle;
import app.figures.Square;

import java.io.FileWriter;
import java.io.IOException;

public class TestFigures {

    public static void testRectangle(FileWriter writer, Rectangle rectangle) throws IOException {
        writer.write("Rectangle test:\n");
        writer.write(String.format("width: %.1f, height: %.1f, perimeter: %.1f\n",
                rectangle.getWidth(), rectangle.getHeight(), rectangle.getPerimeter()));

        rectangle = rectangle.setWidth(32);

        writer.write(String.format("change width to %.1f\n", 32.));
        writer.write(String.format("width: %.1f, height: %.1f, perimeter: %.1f\n\n",
                rectangle.getWidth(), rectangle.getHeight(), rectangle.getPerimeter()));
    }

    public static void testSquare(FileWriter writer, Square square) throws IOException {
        writer.write("Square test:\n");
        writer.write(String.format("side: %.1f, perimeter: %.1f\n",
                square.getSide(), square.getPerimeter()));

        square = square.setSide(29);
        writer.write(String.format("change side to %.1f\n", 29.));
        writer.write(String.format("side: %.1f, perimeter: %.1f\n",
                square.getSide(), square.getPerimeter()));

        Rectangle rectangle = square.setWidth(11);
        writer.write(String.format("change width to %.1f\n", 11.));
        writer.write("now it's a rectangle\n");
        writer.write(String.format("width: %.1f, height: %.1f, perimeter: %.1f\n\n",
                rectangle.getWidth(), rectangle.getHeight(), rectangle.getPerimeter()));
    }

    public static void testEllipse(FileWriter writer, Ellipse ellipse) throws IOException {
        writer.write("Ellipse test:\n");
        writer.write(String.format("majorSemiAxis: %.1f, minorSemiAxis: %.1f, perimeter: %.1f\n",
                ellipse.getMajorSemiAxis(), ellipse.getMinorSemiAxis(), ellipse.getPerimeter()));

        ellipse = ellipse.setMinorSemiAxis(20);

        writer.write(String.format("change minorSemiAxis to %.1f\n", 20.));
        writer.write(String.format("majorSemiAxis: %.1f, minorSemiAxis: %.1f, perimeter: %.1f\n\n",
                ellipse.getMajorSemiAxis(), ellipse.getMinorSemiAxis(), ellipse.getPerimeter()));
    }

    public static void testCircle(FileWriter writer, Circle circle) throws IOException {
        writer.write("Square test:\n");
        writer.write(String.format("radius: %.1f, perimeter: %.1f\n",
                circle.getRadius(), circle.getPerimeter()));

        circle = circle.setRadius(2);
        writer.write(String.format("change radius to %.1f\n", 2.));
        writer.write(String.format("radius: %.1f, perimeter: %.1f\n",
                circle.getRadius(), circle.getPerimeter()));

        Ellipse ellipse = circle.setMajorSemiAxis(14);
        writer.write(String.format("change majorSemiAxis to %.1f\n", 14.));
        writer.write("now it's an ellipse\n");
        writer.write(String.format("majorSemiAxis: %.1f, minorSemiAxis: %.1f, perimeter: %.1f\n\n",
                ellipse.getMajorSemiAxis(), ellipse.getMinorSemiAxis(), ellipse.getPerimeter()));
    }
}
