package app.figures;

public class Rectangle extends Figure {

    private double width;
    private double height;

    public Rectangle(double width, double height, double x, double y) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (getWidth() + getHeight());
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Rectangle setWidth(double width) {
        this.width = width;
        return this;
    }

    public Rectangle setHeight(double height) {
        this.height = height;
        return this;
    }
}
