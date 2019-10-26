import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUnits;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SvgRunCity {

    private static Random random = new Random();

    public static void main(String[] args) throws IOException {

        int width = 8000;
        int height = 8000;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(width, height, SVGUnits.MM);
        Random random = new Random();

        float alpha = 15 * 3.1415f / 180;
        float beta = 30 * 3.1415f / 180;

        float xOrigin = 250;
        float yOrigin = 250;


        Map<Integer, Map<Integer, House>> houseDimensionsMap = new HashMap<>();

        float offsetX = 0;
        for (int casaX = 0; casaX < 10; casaX++) {
            houseDimensionsMap.put(casaX, new HashMap<>());
            float offsetY = 0;
            for (int casaY = 0; casaY < 10; casaY++) {

                float deltaX = random.nextFloat() * 100;
                float deltaY = random.nextFloat() * 100;

                float thisX = xOrigin + offsetX + deltaX;
                float thisY = yOrigin + offsetY + 100  + deltaY;

                float x = thisX;
                float y = thisY;

                float b = 300 + random.nextFloat() * 100;
                float w = 300 + random.nextFloat() * 200;
                float h = 50 + random.nextFloat() * 400;
                float k = 100 + random.nextFloat() * 200;

                offsetY += w + 100;

                houseDimensionsMap.get(casaX).put(casaY, new House(thisX, thisY, b, w, h, k));

                Path2D path2D = new Path2D.Float();
                path2D.moveTo(x, y);
                x += h / Math.tan(beta) * Math.cos(alpha);
                y -= h / Math.tan(beta) * Math.sin(alpha);
                path2D.lineTo(x, y);

                x += b;
                path2D.lineTo(x, y);

                x += k / Math.tan(beta) * Math.cos(alpha);
                y += w / 2;
                path2D.lineTo(x, y);

                x -= k / Math.tan(beta) * Math.cos(alpha);
                y += w / 2;
                path2D.lineTo(x, y);

                x -= h / Math.tan(beta) * Math.cos(alpha);
                y += h / Math.tan(beta) * Math.sin(alpha);
                path2D.lineTo(x, y);

                y -= w;
                path2D.lineTo(x, y);

                path2D.closePath();

                svgGraphic.setColor(new Color(150, 150, 150));
                svgGraphic.fill(path2D);
            }
            offsetX += 1000;
        }

        for (int casaX = 0; casaX < 10; casaX++) {
            for (int casaY = 0; casaY < 10; casaY++) {

                House house = houseDimensionsMap.get(casaX).get(casaY);

                float thisX = house.deltaX;
                float thisY = house.deltaY;

                svgGraphic.clipRect(0, 0, width, height);
                SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());

                svgGraphic.setColor(new Color(98, 55, 50, 227));
                svgGraphic.fillRect((int) thisX, (int) thisY, (int) house.b, (int) (house.w / 2.));

                svgGraphic.setColor(new Color(0, 0, 0));
                svgGraphic.draw(new Rectangle2D.Float((int) thisX, (int) thisY, (int) house.b, (int) (house.w / 2.)));

                svgGraphic.setColor(new Color(226, 143, 136, 227));
                svgGraphic.fillRect((int) (thisX), (int) (thisY + house.w / 2.), (int) house.b, (int) (house.w / 2.));

                svgGraphic.setColor(new Color(0, 0, 0));
                svgGraphic.draw(new Rectangle2D.Float((int) (thisX), (int) (thisY + house.w / 2.), (int) house.b, (int) (house.w / 2.)));
            }
        }


        SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());


    }

    private static class House {
        float deltaX;
        float deltaY;
        float b;
        float w;
        float h;
        float k;

        public House(float deltaX, float deltaY, float b, float w, float h, float k) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.b = b;
            this.w = w;
            this.h = h;
            this.k = k;
        }
    }
}
