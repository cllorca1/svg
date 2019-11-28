package road;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import static road.SvgRoad.factorWidth;

public class Tangent implements RoadElement {

    final double length;
    final double x0;
    final double y0;
    final double intialAngle;
    final double laneWidth;
    final static int correctionFill = 10;
    final Random random;

    final ArrayList<Color> carColors;

    public Tangent(double length, double x0, double y0, double intialAngle, double laneWidth, Random random) {
        this.length = length;
        this.x0 = x0;
        this.y0 = y0;
        this.intialAngle = intialAngle;
        this.laneWidth = laneWidth;
        this.random = random;
        carColors = new ArrayList<>();
        carColors.add(new Color(204, 211, 255));
        carColors.add(new Color(27, 197, 255));
        carColors.add(new Color(57, 85, 253));
        carColors.add(new Color(117, 57, 255));
        carColors.add(new Color(143, 195, 255));
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
        svgGraphics2D.rotate(intialAngle * RoadElement.toRad, x0, y0);
        svgGraphics2D.setColor(SvgRoad.BACKGROUND);
        svgGraphics2D.fillRect((int) x0, (int) (y0 - factorWidth * laneWidth), (int) length + correctionFill, (int) (2 * laneWidth * factorWidth));
        svgGraphics2D.setColor(SvgRoad.ROAD);
        svgGraphics2D.fillRect((int) x0, (int) (y0 - laneWidth), (int) length + correctionFill, (int) (2 * laneWidth));
        svgGraphics2D.setColor(SvgRoad.LINE);
        svgGraphics2D.setStroke(SvgRoad.STROKE_CENTERLINE);
        svgGraphics2D.drawLine((int) x0, (int) y0, (int) (x0 + length), (int) y0);
        svgGraphics2D.setStroke(SvgRoad.STROKE_SHOULDER);
        svgGraphics2D.setColor(SvgRoad.COLOR_SHOULDER);
        svgGraphics2D.drawLine((int) x0, (int) (y0 - laneWidth), (int) (x0 + length), (int) (y0 - laneWidth));
        svgGraphics2D.drawLine((int) x0, (int) (y0 + laneWidth), (int) (x0 + length), (int) (y0 + laneWidth));
        svgGraphics2D.setStroke(new BasicStroke());

        int nCars = random.nextInt((int) (length / 100) );
        for (int car = 0; car < nCars; car++) {
            svgGraphics2D.setColor(carColors.get(random.nextInt(carColors.size())));
            double sizeCar = laneWidth * 1.7;
            double widthCar = laneWidth * 0.55;
            int positionCar = random.nextInt((int) (length - sizeCar));
            double lateralPosition = random.nextBoolean()? y0 + (laneWidth - widthCar) / 2 : y0 - (laneWidth - widthCar)/2 - widthCar;
            svgGraphics2D.fillRect((int) x0 + positionCar, (int) lateralPosition, (int) sizeCar, (int) widthCar);
        }


        svgGraphics2D.rotate(-intialAngle * RoadElement.toRad, x0, y0);
    }
}
