package app.figures;

import java.io.FileWriter;

public class Square extends Rectangle implements Movable {

    public Square(double side, double x, double y) {
        super(side, side, x, y);
    }

    @Override
    public Rectangle setWidth(double width) {
        return new Rectangle(width, getHeight(), getX(), getY());
    }

    @Override
    public Rectangle setHeight(double height) {
        return new Rectangle(getWidth(), height, getX(), getY());
    }

    @Override
    public void setX(double x) {
        super.setX(x);
    }

    @Override
    public void setY(double y) {
        super.setY(y);
    }

    public Square setSide(double side) {
        super.setWidth(side);
        super.setHeight(side);
        return this;
    }

    public double getSide() {
        return getWidth();
    }
}
