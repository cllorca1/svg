import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUnits;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SvgRun {

    public static void main (String[] args) throws IOException {

        int width = 3840;
        int height = 2160;

        int minRectWidth = 50;
        int maxRectWidth = 300;

        double multiplicator = 1.01;

        int divergence = 20;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(3840,2160, SVGUnits.PT);


        Random random = new Random();


        int y = 500;
        double scale = 3;

        int counter = 0;

        while (y < height && counter < 300){

            int x = (int) (random.nextDouble()*width);
            int recWidth = (int)( minRectWidth + (maxRectWidth - minRectWidth)*random.nextDouble()*scale/20);

            int r = 220 + (int)(divergence * (0.5 - random.nextDouble())) - counter;
            int g = 220 + (int)(divergence * (0.5 - random.nextDouble())) - counter;
            int b = 220 + (int)(divergence * (0.5 - random.nextDouble())) - counter;

            r = Math.max(r,1);
            g = Math.max(g,1);
            b = Math.max(b,1);
            Color color = new Color(r,g,b);
            svgGraphic.setColor(color);
            svgGraphic.fillRect(x,y,recWidth,height - y);

            y = y + (int)((0.9 - random.nextDouble()) *scale);

            scale = scale*multiplicator;

            counter++;
            System.out.println(counter);
        }



        SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());

    }
}
