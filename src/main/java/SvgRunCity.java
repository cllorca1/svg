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

        Color sombra = new Color(87, 87, 87, 255);
        Color negro = new Color(0, 0, 0);

        int width = 8000;
        int height = 8000;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(width, height, SVGUnits.MM);
        Random random = new Random();

        float alpha = 35 * 3.1415f / 180;
        float beta = 25 * 3.1415f / 180;

        float xOrigin = 250;
        float yOrigin = 250;

        svgGraphic.setColor(new Color(148, 177, 98, 190));
        svgGraphic.fillRect(0, 0, 8000, 8000);

        Map<Integer, Map<Integer, House>> houseDimensionsMap = new HashMap<>();

        float offsetX = 0;
        int casaX = 0;
        while (offsetX < 8000) {
            houseDimensionsMap.put(casaX, new HashMap<>());
            float offsetY = 0;
            int casaY = 0;
            while (offsetY < 8000) {
                float deltaX = 0;
                float deltaY = 0;
                float thisX = xOrigin + offsetX + deltaX;
                float thisY = yOrigin + offsetY + deltaY;
                House house = creaCasa(random, thisX, thisY);
                offsetY += house.w + 150;
                if (random.nextInt(10) != 0) {
                    houseDimensionsMap.get(casaX).put(casaY, house);
                    dibujaSombraDeCasa(sombra, svgGraphic, alpha, beta, house);
                }
                casaY++;

            }
            if (casaX % 2 == 0) {
                offsetX += 800;
            } else {
                offsetX += 1200;
                float hFarola = 200;
                int b = 10;
                float yFarola = yOrigin;
                while (yFarola < 8000) {
                    dibujaFarola(sombra, negro, svgGraphic, alpha, beta, offsetX, hFarola, b, yFarola);
                    yFarola += 1000;
                }
                svgGraphic.setColor(new Color(77, 77, 69, 169));
                svgGraphic.fillRect((int) (offsetX - 250), 0, 250, 10000);
            }
            casaX++;
        }

        for (Map<Integer, House> housesThisRow : houseDimensionsMap.values()) {
            for (House house : housesThisRow.values()) {

                if (house != null) {
                    dibujaCasaTejado(svgGraphic, house);
                }

            }
        }

        svgGraphic.clipRect(0, 0, width, height);
        SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());


    }

    private static House creaCasa(Random random, float thisX, float thisY) {
        float b = 450 + random.nextFloat() * 0;
        float w = 300 + random.nextInt(10) * 50;
        float h = 100 + random.nextInt(2) * 100;
        float k = 100;

        return new House(thisX, thisY, b, w, h, k);
    }

    private static void dibujaCasaTejado(SVGGraphics2D svgGraphic, House house) {
        float thisX = house.deltaX;
        float thisY = house.deltaY;

        svgGraphic.setColor(new Color(98, 55, 50, 255));
        svgGraphic.fillRect((int) thisX, (int) thisY, (int) house.b, (int) (house.w / 2.));

        svgGraphic.setColor(new Color(0, 0, 0));
        svgGraphic.draw(new Rectangle2D.Float((int) thisX, (int) thisY, (int) house.b, (int) (house.w / 2.)));

        svgGraphic.setColor(new Color(226, 143, 136, 255));
        svgGraphic.fillRect((int) (thisX), (int) (thisY + house.w / 2.), (int) house.b, (int) (house.w / 2.));

        svgGraphic.setColor(new Color(0, 0, 0));
        svgGraphic.draw(new Rectangle2D.Float((int) (thisX), (int) (thisY + house.w / 2.), (int) house.b, (int) (house.w / 2.)));
    }

    private static void dibujaFarola(Color sombra, Color negro, SVGGraphics2D svgGraphic, float alpha, float beta, float offsetX, float hFarola, int b, float yFarola) {
        {
            svgGraphic.setColor(sombra);
            Path2D path2D = new Path2D.Float();
            float x0 = offsetX - 300;
            float y0 = yFarola;
            path2D.moveTo(x0, y0);
            path2D.lineTo(x0 + hFarola / Math.tan(beta) * Math.cos(alpha), y0 - hFarola / Math.tan(beta) * Math.sin(alpha));
            path2D.lineTo(x0 + hFarola / Math.tan(beta) * Math.cos(alpha) + b, y0 - hFarola / Math.tan(beta) * Math.sin(alpha));
            path2D.lineTo(x0 + hFarola / Math.tan(beta) * Math.cos(alpha) + b, y0 - hFarola / Math.tan(beta) * Math.sin(alpha) + b);
            path2D.lineTo(x0 + b, y0 + b);
            path2D.closePath();
            svgGraphic.fill(path2D);
            svgGraphic.setColor(new Color(80, 80, 80));
            svgGraphic.fillRect((int) x0, (int) y0, b, b);
            svgGraphic.setColor(negro);
            svgGraphic.draw(new Rectangle2D.Float((int) x0, (int) y0, b, b));

        }
        {
            svgGraphic.setColor(sombra);
            Path2D path2D = new Path2D.Float();
            float x0 = offsetX + 50;
            float y0 = yFarola;
            path2D.moveTo(x0, y0);
            path2D.lineTo(x0 + hFarola / Math.tan(beta) * Math.cos(alpha), y0 - hFarola / Math.tan(beta) * Math.sin(alpha));
            path2D.lineTo(x0 + hFarola / Math.tan(beta) * Math.cos(alpha) + b, y0 - hFarola / Math.tan(beta) * Math.sin(alpha));
            path2D.lineTo(x0 + hFarola / Math.tan(beta) * Math.cos(alpha) + b, y0 - hFarola / Math.tan(beta) * Math.sin(alpha) + b);
            path2D.lineTo(x0 + b, y0 + b);
            path2D.closePath();
            svgGraphic.fill(path2D);
            svgGraphic.setColor(new Color(80, 80, 80));
            svgGraphic.fillRect((int) x0, (int) y0, b, b);
            svgGraphic.setColor(negro);
            svgGraphic.draw(new Rectangle2D.Float((int) x0, (int) y0, b, b));
        }
    }

    private static void dibujaSombraDeCasa(Color sombra, SVGGraphics2D svgGraphic, float alpha, float beta, House house) {
        float b = house.b;
        float w = house.w;
        float h = house.h;
        float k = house.k;

        float thisX = house.deltaX;
        float thisY = house.deltaY;

        Path2D path2D = new Path2D.Float();
        path2D.moveTo(thisX, thisY);
        thisX += h / Math.tan(beta) * Math.cos(alpha);
        thisY -= h / Math.tan(beta) * Math.sin(alpha);
        path2D.lineTo(thisX, thisY);

        thisX += b;
        path2D.lineTo(thisX, thisY);

        thisX += k / Math.tan(beta) * Math.cos(alpha);
        thisY += w / 2;
        path2D.lineTo(thisX, thisY);

        thisX -= k / Math.tan(beta) * Math.cos(alpha);
        thisY += w / 2;
        path2D.lineTo(thisX, thisY);

        thisX -= h / Math.tan(beta) * Math.cos(alpha);
        thisY += h / Math.tan(beta) * Math.sin(alpha);
        path2D.lineTo(thisX, thisY);

        thisY -= w;
        path2D.lineTo(thisX, thisY);

        path2D.closePath();


        svgGraphic.setColor(sombra);
        svgGraphic.fill(path2D);
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
