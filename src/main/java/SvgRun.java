import javafx.scene.shape.StrokeLineCap;
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

        int width = 5000;
        int height = 2160;

        double minRectWidth = 50;
        double maxRectWidth = 150;

        double multiplicator = 1.005;

        int divergence = 10;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(width,height, SVGUnits.PT);

        svgGraphic.setColor(new Color(226, 226, 226));
        svgGraphic.fillRect(0,0,width,height);

        Random random = new Random();

        Color testColor = new Color(0, 100, 200);

        int y = 250;
        double floorHeight = 10;
        int counter = 0;
        double grayAttenuation = 2;
        int previousX = - 500;
        while (y < height && counter < 300){
            int x = (int) (random.nextDouble()*width);
            if (Math.abs(x - previousX) < 100){
                continue;
            }
            previousX = x;
            int recWidth = (int)( minRectWidth + (maxRectWidth - minRectWidth)*random.nextDouble());
            double colorDivergenceRandomNumber = random.nextDouble();
            int colorDeviation = (int)((divergence * (0.5 - colorDivergenceRandomNumber)) - counter / grayAttenuation);
            int r = 200 + colorDeviation;
            int g = 200 + colorDeviation;
            int b = 200 + colorDeviation;

            for (int yLine = y; yLine<height; yLine = yLine+ (int) floorHeight){
                int thisGrayVariation = random.nextInt(5);
                int thisR = Math.max(r,1) + thisGrayVariation;
                int thisG = Math.max(g,1) + thisGrayVariation;
                int thisB = Math.max(b,1) + thisGrayVariation;
                thisR = Math.min(thisR,255);
                thisG = Math.min(thisG,255);
                thisB = Math.min(thisB,255);

                Color color = new Color(thisR ,thisG ,thisB);
                svgGraphic.setColor(color);
                svgGraphic.fillRect(x,yLine,recWidth,(int) floorHeight);
            }
            //svgGraphic.fillRect(x,y,recWidth,height - y);
            svgGraphic.setColor(new Color(0,0,0));
            svgGraphic.setStroke(new BasicStroke());
            svgGraphic.draw(new Rectangle2D.Double(x,y,recWidth,height - y));
            for (int yLine = y; yLine<height; yLine = yLine+ (int) floorHeight){
                svgGraphic.drawLine(x,yLine,x+recWidth,yLine);
            }
            y = y + Math.max((int)((1 - random.nextDouble()) ),5);
            minRectWidth = minRectWidth*multiplicator;
            maxRectWidth = maxRectWidth*multiplicator;
            counter++;
            floorHeight = floorHeight*multiplicator;
            System.out.println(counter);
        }



        SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());

    }
}
