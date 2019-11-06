package road;

import org.jfree.graphics2d.svg.SVGGraphics2D;

public interface RoadElement {

    double toRad = 3.1415/180;

    double getX0();
    double getYO();
    double getIntialAngle();

    double getX();
    double getY();
    double getFinalAngle();

    void draw(SVGGraphics2D svgGraphics2D);
}
