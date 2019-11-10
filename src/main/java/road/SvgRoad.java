package road;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUnits;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SvgRoad {

    final static int buffer = 100;
    static Color BACKGROUND = new Color(115, 137, 123);
    static Color ROAD = new Color(56, 56, 56);
    final static int LIGHTENING = 3;
    final static Color LINE = new Color(251, 251, 251);
    final static Color COLOR_SHOULDER = new Color(238, 236, 238);
    static final BasicStroke STROKE_CENTERLINE = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1f, new float[]{20f, 20f}, 1);
    static final BasicStroke STROKE_SHOULDER = new BasicStroke(10);

    public static void main(String[] args) throws IOException {

        int width = 5000;
        int height = 5000;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(width, height, SVGUnits.PT);

        svgGraphic.setColor(BACKGROUND);
        svgGraphic.fillRect(0,0,width, height);
        double minL = 300;
        double maxL = 500;

        double minR = 500;
        double maxR = 1200;

        double minAngle = 30;
        double maxAngle = 100;

        double laneWidth = 50;
        int roads = 30;

        for (int r = 0; r < roads; r++) {
            Road myRoad = new Road(2, laneWidth);
            Random random = new Random();
            Side side = Side.chooseSide(random);
            double minInitialAngle = side.minAngle;
            double maxInitialAngle = side.maxAngle;
            double x;
            double y;
            if (side.equals(Side.TOP)) {
                x = random.nextInt(width);
                y = -buffer;
            } else if (side.equals(Side.LEFT)) {
                x = -buffer;
                y = random.nextInt(height);
            } else if (side.equals(Side.BOTTOM)) {
                x = random.nextInt(width);
                y = height + buffer;
            } else {
                x = width + buffer;
                y = random.nextInt(height);
            }

            double angle = minInitialAngle + (maxInitialAngle - minInitialAngle) * random.nextDouble();

            int seq = 0;
            boolean inside = true;
            while (seq < 1000 && inside) {
                Tangent myTangent = new Tangent(minL + random.nextDouble() * (maxL - minL), x, y, angle, laneWidth);
                svgGraphic.setStroke(new BasicStroke(1));
                myTangent.draw(svgGraphic);
                int curveSign = random.nextBoolean() ? 1 : -1;
                Curve myCurve = new Curve(minR + random.nextDouble() * (maxR - minR),
                        myTangent.getX(), myTangent.getY(), myTangent.getFinalAngle(),
                        (minAngle + random.nextDouble() * (maxAngle - minAngle)) * curveSign, laneWidth);
                myRoad.getElements().put(seq++, myTangent);
                myRoad.getElements().put(seq++, myCurve);
                myCurve.draw(svgGraphic);
                x = myCurve.getX();
                y = myCurve.getY();
                inside = isPointInsideDrawing(x, y, width, height);
                angle = myCurve.getFinalAngle();
            }
            try {
                ROAD = new Color(ROAD.getRed() + LIGHTENING, ROAD.getGreen() + LIGHTENING, ROAD.getBlue() + LIGHTENING);
            } catch (Exception e) {
                System.out.println("readjust colors!!!!");
            }
        }

        SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());

    }

    private static boolean isPointInsideDrawing(double x, double y, double w, double h) {
        if (x > h + SvgRoad.buffer) {
            return false;
        } else if (x < 0 - SvgRoad.buffer) {
            return false;
        } else if (y > h + SvgRoad.buffer) {
            return false;
        } else if (y < 0 - SvgRoad.buffer) {
            return false;
        } else {
            return true;
        }
    }

    enum Side {
        TOP(45, 135),
        BOTTOM(-135, -45),
        LEFT(45, -45),
        RIGHT(135, 225);

        private int maxAngle;
        private int minAngle;

        Side(int maxAngle, int minAngle) {
            this.maxAngle = maxAngle;
            this.minAngle = minAngle;
        }

        static Side chooseSide(Random random) {
            return Side.values()[random.nextInt(3)];
        }
    }
}
