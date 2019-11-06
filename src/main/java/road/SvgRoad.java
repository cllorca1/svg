package road;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUnits;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SvgRoad {


    public static void main(String[] args) throws IOException {

        int width = 5000;
        int height = 5000;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(width, height, SVGUnits.PT);

        double minL = 200;
        double maxL = 350;

        double minAngle = 65;
        double maxAngle = 180;

        double minR = 100;
        double maxR = 300;

        Road myRoad = new Road(2, 10);

        Random random = new Random();
        double x = 2500;
        double y = 2500;
        double angle = 45;

        for (int seq = 0; seq < 20; seq = seq + 2) {
            Tangent myTangent = new Tangent(minL + random.nextDouble() * (maxL - minL), x, y, angle);
            svgGraphic.setColor(new Color(0, 0, 0));

            myTangent.draw(svgGraphic);
            int curveSign = random.nextBoolean()? 1 : -1;
            Curve myCurve = new Curve(minR + random.nextDouble() * (maxR - minR),
                    myTangent.getX(), myTangent.getY(), myTangent.getFinalAngle(),
                    minAngle + random.nextDouble() * (maxAngle - minAngle) * curveSign);
            myRoad.getElements().put(seq, myTangent);
            myRoad.getElements().put(seq + 1, myCurve);
            svgGraphic.setColor(new Color(255, 0, 0));
            myCurve.draw(svgGraphic);
            x = myCurve.getX();
            y = myCurve.getY();
            angle = myCurve.getFinalAngle();
        }


        SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());

    }
}
