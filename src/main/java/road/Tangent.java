package road;

import org.jfree.graphics2d.svg.SVGGraphics2D;

public class Tangent implements RoadElement {

    final double length;
    final double x0;
    final double y0;
    final double intialAngle;

    public Tangent(double length, double x0, double y0, double intialAngle) {
        this.length = length;
        this.x0 = x0;
        this.y0 = y0;
        this.intialAngle = intialAngle;
    }


    @Override
    public double getX0() {
        return x0;
    }

    @Override
    public double getYO() {
        return y0;
    }

    @Override
    public double getIntialAngle() {
        return intialAngle;
    }

    @Override
    public double getX() {
        return x0 + length * Math.cos(intialAngle * RoadElement.toRad);
    }

    @Override
    public double getY() {
        return y0 + length * Math.sin(intialAngle * RoadElement.toRad);
    }

    @Override
    public double getFinalAngle() {
        return intialAngle;
    }

    @Override
    public void draw(SVGGraphics2D svgGraphics2D) {
        svgGraphics2D.drawLine((int) x0, (int)y0, (int)this.getX(), (int)this.getY());
    }
}
