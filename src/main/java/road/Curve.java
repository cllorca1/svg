package road;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Path2D;
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
    private final int sign;
    private ArrayList<Point> points;
    private double finalX;
    private double finalY;
    private double finalAngle;
    private final double laneWidth;
    private double centerX;
    private double centerY;

    public Curve(double radius, double x0, double y0, double intialAngle, double curveAngle, double laneWidth) {
        this.radius = radius;
        this.x0 = x0;
        this.y0 = y0;
        this.intialAngle = intialAngle;
        sign = (int) Math.signum(curveAngle);
        this.curveAngle = curveAngle;
        this.laneWidth = laneWidth;
        this.length = Math.abs(radius * curveAngle * RoadElement.toRad);
        cuerda = 2 * radius * Math.cos((180 - Math.abs(curveAngle)) / 2 * RoadElement.toRad);
        anguloCuerda = intialAngle + sign * (90 - (180 - Math.abs(curveAngle)) / 2);
        this.centerX = x0 - sign * radius * Math.sin(intialAngle * RoadElement.toRad);
        this.centerY = y0 + sign * radius * Math.cos(intialAngle * RoadElement.toRad);

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
            Curve miniCurve = new Curve(radius, x, y, alpha, miniAngle * sign, laneWidth);
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


    private double getXExact() {
        return x0 + cuerda * Math.cos(anguloCuerda * RoadElement.toRad);
    }


    private double getYExact() {
        return y0 + cuerda * Math.sin(anguloCuerda * RoadElement.toRad);
    }


    private double getFinalAngleExact() {
        return intialAngle + curveAngle;
    }

    @Override
    public void draw(SVGGraphics2D svgGraphics2D) {
        points = generateCurve();

        Path2D path2D = new Path2D.Float();
        svgGraphics2D.setColor(SvgRoad.ROAD);
        Point previousPoint = points.get(0);
        double proportion = (radius + laneWidth)/radius;
        path2D.moveTo(centerX + (previousPoint.x - centerX) * proportion,
                centerY + (previousPoint.y - centerY) * proportion);
        for (int i = 1; i < points.size(); i++) {
            Point thisPoint = points.get(i);
            path2D.lineTo(centerX + (thisPoint.x - centerX) * proportion,
                    centerY + (thisPoint.y - centerY) * proportion);
        }

        previousPoint = points.get(points.size()- 1);
        proportion = (radius - laneWidth)/radius;
        path2D.lineTo(centerX + (previousPoint.x - centerX) * proportion,
                centerY + (previousPoint.y - centerY) * proportion);
        for (int i = points.size() - 1; i >= 0; i--) {
            Point thisPoint = points.get(i);
            path2D.lineTo(centerX + (thisPoint.x - centerX) * proportion,
                    centerY + (thisPoint.y - centerY) * proportion);
        }
        path2D.closePath();
        svgGraphics2D.fill(path2D);
        previousPoint = points.get(0);
        svgGraphics2D.setColor(SvgRoad.LINE);
        for (int i = 1; i < points.size(); i++) {
            Point thisPoint = points.get(i);
            svgGraphics2D.setColor(SvgRoad.LINE);
            svgGraphics2D.drawLine(previousPoint.x, previousPoint.y, thisPoint.x, thisPoint.y);
            previousPoint = thisPoint;
        }
    }
}
