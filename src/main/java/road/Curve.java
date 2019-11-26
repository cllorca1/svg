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
    private final static double DELTA_L = 10;
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
        Path2D background = new Path2D.Float();
        Path2D platform = new Path2D.Float();
        Path2D leftSideShoulder = new Path2D.Float();
        Path2D rightSideShoulder = new Path2D.Float();

        Point previousPoint = points.get(0);

        double proportion = (radius + laneWidth)/radius;
        double proportionBackground = (radius + laneWidth * SvgRoad.factorWidth)/radius;
        background.moveTo(centerX + (previousPoint.x - centerX) * proportionBackground,
                centerY + (previousPoint.y - centerY) * proportionBackground);
        platform.moveTo(centerX + (previousPoint.x - centerX) * proportion,
                centerY + (previousPoint.y - centerY) * proportion);
        leftSideShoulder.moveTo(centerX + (previousPoint.x - centerX) * proportion,
                centerY + (previousPoint.y - centerY) * proportion);
        for (int i = 1; i < points.size(); i++) {
            Point thisPoint = points.get(i);
            background.lineTo(centerX + (thisPoint.x - centerX) * proportionBackground,
                    centerY + (thisPoint.y - centerY) * proportionBackground);
            platform.lineTo(centerX + (thisPoint.x - centerX) * proportion,
                    centerY + (thisPoint.y - centerY) * proportion);
            leftSideShoulder.lineTo(centerX + (thisPoint.x - centerX) * proportion,
                    centerY + (thisPoint.y - centerY) * proportion);
        }

        previousPoint = points.get(points.size()- 1);
        proportion = (radius - laneWidth)/radius;
        proportionBackground = (radius - laneWidth * SvgRoad.factorWidth)/radius;
        background.lineTo(centerX + (previousPoint.x - centerX) * proportionBackground,
                centerY + (previousPoint.y - centerY) * proportionBackground);
        platform.lineTo(centerX + (previousPoint.x - centerX) * proportion,
                centerY + (previousPoint.y - centerY) * proportion);
        rightSideShoulder.moveTo(centerX + (previousPoint.x - centerX) * proportion,
                centerY + (previousPoint.y - centerY) * proportion);
        for (int i = points.size() - 1; i >= 0; i--) {
            Point thisPoint = points.get(i);
            background.lineTo(centerX + (thisPoint.x - centerX) * proportionBackground,
                    centerY + (thisPoint.y - centerY) * proportionBackground);
            platform.lineTo(centerX + (thisPoint.x - centerX) * proportion,
                    centerY + (thisPoint.y - centerY) * proportion);
            rightSideShoulder.lineTo(centerX + (thisPoint.x - centerX) * proportion,
                    centerY + (thisPoint.y - centerY) * proportion);
        }
        svgGraphics2D.setColor(SvgRoad.BACKGROUND);
        background.closePath();
        svgGraphics2D.fill(background);
        svgGraphics2D.setColor(SvgRoad.ROAD);
        platform.closePath();
        svgGraphics2D.fill(platform);
        previousPoint = points.get(0);
        svgGraphics2D.setColor(SvgRoad.LINE);
        svgGraphics2D.setStroke(SvgRoad.STROKE_CENTERLINE);
        Path2D centerLine = new Path2D.Float();
        centerLine.moveTo(previousPoint.x, previousPoint.y);
        for (int i = 1; i < points.size(); i++) {
            Point thisPoint = points.get(i);
            centerLine.lineTo(thisPoint.x, thisPoint.y);
        }
        svgGraphics2D.draw(centerLine);
        svgGraphics2D.setColor(SvgRoad.COLOR_SHOULDER);
        svgGraphics2D.setStroke(SvgRoad.STROKE_SHOULDER);
        svgGraphics2D.draw(leftSideShoulder);
        svgGraphics2D.draw(rightSideShoulder);
        svgGraphics2D.setStroke(new BasicStroke());
    }
}
