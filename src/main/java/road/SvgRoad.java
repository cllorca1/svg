package road;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUnits;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SvgRoad {

    final static Color ROAD = new Color(176, 176, 176);
    final static Color LINE = new Color(1, 1, 1);

    public static void main(String[] args) throws IOException {

        int width = 5000;
        int height = 5000;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(width, height, SVGUnits.PT);

        double minL = 150;
        double maxL = 750;

        double minAngle = 45;
        double maxAngle = 180;

        double minR = 200;
        double maxR = 400;

        double laneWidth = 50;
        Road myRoad = new Road(2, laneWidth);

        Random random = new Random();
        double x = 2500;
        double y = 2500;
        double angle = 0;

        for (int seq = 0; seq < 20; seq = seq + 2) {
            Tangent myTangent = new Tangent(minL + random.nextDouble() * (maxL - minL), x, y, angle, laneWidth);
            svgGraphic.setStroke(new BasicStroke(1));
            myTangent.draw(svgGraphic);
            int curveSign = random.nextBoolean()? 1 : -1;
            Curve myCurve = new Curve(minR + random.nextDouble() * (maxR - minR),
                    myTangent.getX(), myTangent.getY(), myTangent.getFinalAngle(),
                    (minAngle + random.nextDouble() * (maxAngle - minAngle)) * curveSign, laneWidth);
            myRoad.getElements().put(seq, myTangent);
            myRoad.getElements().put(seq + 1, myCurve);
            myCurve.draw(svgGraphic);
            x = myCurve.getX();
            y = myCurve.getY();
            angle = myCurve.getFinalAngle();
        }


        SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());

    }
}
