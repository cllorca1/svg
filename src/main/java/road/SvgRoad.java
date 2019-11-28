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
    static Color BACKGROUND = new Color(75, 87, 78);
    static Color ROAD = new Color(56, 56, 56);
    final static int LIGHTENING = 3;
    final static Color LINE = new Color(251, 251, 251);
    final static Color COLOR_SHOULDER = new Color(238, 236, 238);
    static final BasicStroke STROKE_CENTERLINE = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1f, new float[]{20f, 20f}, 1);
    static final BasicStroke STROKE_SHOULDER = new BasicStroke(10);
    static double factorWidth = 1.0;

    public static void main(String[] args) throws IOException {

        int width = 10000;
        int height = 10000;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(width, height, SVGUnits.PT);

        svgGraphic.setColor(BACKGROUND);
        svgGraphic.fillRect(0,0,width, height);
        double minL = 200;
        double maxL = 1000;

        double minR = 250;
        double maxR = 1000;

        double minAngle = 25;
        double maxAngle = 195;

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
                Tangent myTangent = new Tangent(minL + random.nextDouble() * (maxL - minL), x, y, angle, laneWidth, random);
                svgGraphic.setStroke(new BasicStroke(1));
                myTangent.draw(svgGraphic);
                int curveSign = random.nextBoolean() ? 1 : -1;
                double curveAngle = ((maxAngle - minAngle)/2 + random.nextGaussian() * (maxAngle - minAngle)/4) * curveSign;
                Curve myCurve = new Curve( Math.pow(Math.abs(curveAngle), -0.999) * 37000,
                        myTangent.getX(), myTangent.getY(), myTangent.getFinalAngle(),
                        curveAngle, laneWidth);
                myRoad.getElements().put(seq++, myTangent);
                myRoad.getElements().put(seq++, myCurve);
                myCurve.draw(svgGraphic);
                x = myCurve.getX();
                y = myCurve.getY();
                inside = isPointInsideDrawing(x, y, width, height);
                angle = myCurve.getFinalAngle();

            }
            try {
                int randomColorChange = random.nextInt(10);
                ROAD = new Color(ROAD.getRed() + LIGHTENING - randomColorChange,
                        ROAD.getGreen() + LIGHTENING - randomColorChange,
                        ROAD.getBlue() + LIGHTENING -randomColorChange);
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
        TOP(90, 90),
        BOTTOM(-90, -90),
        LEFT(0, 0),
        RIGHT(180, 180);

        private int maxAngle;
        private int minAngle;

        Side(int maxAngle, int minAngle) {
            this.maxAngle = maxAngle;
            this.minAngle = minAngle;
        }

        static Side chooseSide(Random random) {
            return Side.values()[random.nextInt(4)];
        }
    }
}
