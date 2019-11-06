package road;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Curve implements RoadElement {

    private final double radius;
    private final double x0;
    private final double y0;
    private final double intialAngle;
    private final double curveAngle;
    private final double length;
    private final double cuerda;
    private final double anguloCuerda;
    private final static double DELTA_L = 1;
    private ArrayList<Point> points;
    private double finalX;
    private double finalY;
    private double finalAngle;

    public Curve(double radius, double x0, double y0, double intialAngle, double curveAngle) {
        this.radius = radius;
        this.x0 = x0;
        this.y0 = y0;
        this.intialAngle = intialAngle;
        this.curveAngle = curveAngle;
        this.length = radius * curveAngle * RoadElement.toRad;
        cuerda = 2 * radius * Math.cos((180 - curveAngle) / 2 * RoadElement.toRad);
        anguloCuerda = intialAngle + (90 - (180 - curveAngle) / 2);

    }

    private ArrayList<Point> generateCurve() {
        ArrayList<Point> points = new ArrayList<>();
        double cumLength = 0;
        double x = this.x0;
        double y = this.y0;
        points.add(new Point((int) x, (int) y));
        double alpha = this.intialAngle;
        while (cumLength < this.length) {
            double miniAngle = 180 - 2 * Math.acos(DELTA_L / 2 / radius) / RoadElement.toRad;
            Curve miniCurve = new Curve(radius, x, y, alpha, miniAngle);
            x = miniCurve.getXExact();
            y = miniCurve.getYExact();
            alpha = miniCurve.getFinalAngleExact();
            points.add(new Point((int) x, (int) y));
            cumLength += miniCurve.length;
        }
        finalX = x;
        finalY = y;
        finalAngle = alpha;

        return points;
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
        return finalX;
    }

    @Override
    public double getY() {
        return finalY;
    }

    @Override
    public double getFinalAngle() {
        return finalAngle;
    }

    public double getXExact() {
        return x0 + cuerda * Math.cos(anguloCuerda * RoadElement.toRad);
    }


    public double getYExact() {
        return y0 + cuerda * Math.sin(anguloCuerda * RoadElement.toRad);
    }


    public double getFinalAngleExact() {
        return intialAngle + curveAngle;
    }

    @Override
    public void draw(SVGGraphics2D svgGraphics2D) {
        points = generateCurve();
        Point previousPoint = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            Point thisPoint = points.get(i);
            svgGraphics2D.drawLine((int) previousPoint.x, (int) previousPoint.y, (int) thisPoint.x, (int) thisPoint.y);
            previousPoint = thisPoint;
        }
    }
}
