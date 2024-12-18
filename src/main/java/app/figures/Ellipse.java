package app.figures;

public class Ellipse extends Figure {

    private double minorSemiAxis;
    private double majorSemiAxis;

    public Ellipse(double minorSemiAxis, double majorSemiAxis, double x, double y) {
        super(x, y);
        this.minorSemiAxis = minorSemiAxis;
        this.majorSemiAxis = majorSemiAxis;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * Math.sqrt(
                (Math.pow(getMajorSemiAxis(), 2) + Math.pow(getMinorSemiAxis(), 2)) / 2
        );
    }

    public double getMinorSemiAxis() {
        return minorSemiAxis;
    }

    public double getMajorSemiAxis() {
        return majorSemiAxis;
    }

    public Ellipse setMinorSemiAxis(double minorSemiAxis) {
        this.minorSemiAxis = minorSemiAxis;
        return this;
    }

    public Ellipse setMajorSemiAxis(double majorSemiAxis) {
        this.majorSemiAxis = majorSemiAxis;
        return this;
    }
}
