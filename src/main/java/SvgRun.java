import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUnits;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SvgRun {

    public static void main (String[] args) throws IOException {

        int width = 3840;
        int height = 2160;

        int minRectWidth = 100;
        int maxRectWidth = 400;

        double multiplicator = 1.01;

        int divergence = 10;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(3840,2160, SVGUnits.PT);

        svgGraphic.setColor(new Color(255, 255, 255));
        svgGraphic.fillRect(0,0,3840,2160);

        Random random = new Random();

        Color testColor = new Color(0, 100, 200);

        int y = 250;
        double scale = 3;

        int counter = 0;
        double grayAttenuation = 1.5;
        while (y < height && counter < 300){

            int x = (int) (random.nextDouble()*width);
            int recWidth = (int)( minRectWidth + (maxRectWidth - minRectWidth)*random.nextDouble()*scale/20);

            double colorDivergenceRandomNumber = random.nextDouble();
            int colorDeviation = (int)((divergence * (0.5 - colorDivergenceRandomNumber)) - counter / grayAttenuation);
            int r = 200 + colorDeviation;
            int g = 50 + colorDeviation;
            int b = 10 + colorDeviation;

            r = Math.max(r,1);
            g = Math.max(g,1);
            b = Math.max(b,1);
            Color color = new Color(r,g,b);
            svgGraphic.setColor(color);

            svgGraphic.fillRect(x,y,recWidth,height - y);
            svgGraphic.setColor(new Color(0,0,0));
            svgGraphic.setStroke(new BasicStroke());
            svgGraphic.draw(new Rectangle2D.Double(x,y,recWidth,height - y));
            y = y + (int)((1 - random.nextDouble()) *scale);
            scale = scale*multiplicator;

            counter++;
            System.out.println(counter);
        }



        SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());

    }
}
