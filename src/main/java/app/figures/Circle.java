package app.figures;

import app.interfaces.Movable;

public class Circle extends Ellipse implements Movable {

    public Circle(double radius, double x, double y) {
        super(radius, radius, x, y);
    }

    @Override
    public Ellipse setMinorSemiAxis(double minorSemiAxis) {
        return new Ellipse(minorSemiAxis, getMajorSemiAxis(), getX(), getY());
    }

    @Override
    public Ellipse setMajorSemiAxis(double majorSemiAxis) {
        return new Ellipse(getMinorSemiAxis(), majorSemiAxis, getX(), getY());
    }

    @Override
    public void setX(double x) {
        super.setX(x);
    }

    @Override
    public void setY(double y) {
        super.setY(y);
    }

    public Circle setRadius(double radius) {
        super.setMinorSemiAxis(radius);
        super.setMajorSemiAxis(radius);
        return this;
    }

    public double getRadius() {
        return getMajorSemiAxis();
    }
}
