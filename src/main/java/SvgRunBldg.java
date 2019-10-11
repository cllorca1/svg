import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUnits;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SvgRunBldg {

    private static final Color remateFachada = new Color(244, 177, 131);
    private static Color fachadaSombra = new Color(191, 144, 0);
    private static Color color = new Color(255, 230, 153);
    private static Color ventanaMuyOscura = new Color(35, 35, 35);
    private static Color ventanaMedia = new Color(64, 64, 64);
    private static Color barandilla = new Color(187, 178, 139);
    private static Color ventanaClara = new Color(118, 113, 113);

    public static void main(String[] args) throws IOException {

        int width = 2000;
        int height = 4000;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(width, height, SVGUnits.MM);

        Random random = new Random();

        int xOrigin = 250;
        int yOrigin = 250;

        int alturaPiso = 70 + 110 + 110 + 45;

        for (int piso = 0; piso < 15; piso++) {

            dibujaBalcon1(svgGraphic, xOrigin, yOrigin  +  piso * (alturaPiso));
            dibujaLateralIzq(svgGraphic, xOrigin, yOrigin  +  piso * (alturaPiso));
            dibujaBloqueVacio(svgGraphic, xOrigin, yOrigin  +  piso * (alturaPiso));
            dibujaBalcon1(svgGraphic, xOrigin + 145*8, yOrigin  +  piso * (alturaPiso));
        }


        svgGraphic.clipRect(0, 0, width, height);
        SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());


    }

    private static void dibujaBloqueVacio(SVGGraphics2D svgGraphic, int xOrigin, int yOrigin) {
        svgGraphic.setColor(remateFachada);
        svgGraphic.fillRect(xOrigin + 7 * 145, yOrigin, 145, 70 + 110 + 110 + 45);
    }

    private static void dibujaLateralIzq(SVGGraphics2D svgGraphic, int xOrigin, int yOrigin) {

        svgGraphic.setColor(barandilla);
        svgGraphic.fillRect(xOrigin - 80, yOrigin + 70 + 110, 80, 110);

        svgGraphic.setColor(remateFachada);
        svgGraphic.fillRect(xOrigin - 80, yOrigin + 70 + 110 + 110, 80, 45);
    }

    private static void dibujaBalcon1(SVGGraphics2D svgGraphic, int xOrigin, int yOrigin) {
        Color color;

        color = fachadaSombra;
        svgGraphic.setColor(color);
        svgGraphic.fillRect(xOrigin, yOrigin, 7 * 145, 70);

        for (int i = 0; i < 3; i++) {
            color = ventanaMuyOscura;
            svgGraphic.setColor(color);
            svgGraphic.fillRect(xOrigin + 145 * (2 * i + 1), yOrigin + 40, 145, 70);
        }

        color = SvgRunBldg.color;
        svgGraphic.setColor(color);
        svgGraphic.fillRect(xOrigin, yOrigin + 70, 7 * 145, 110);

        for (int i = 0; i < 3; i++) {
            color = ventanaMedia;
            svgGraphic.setColor(color);
            svgGraphic.fillRect(xOrigin + 145 * (2 * i + 1), yOrigin + 70, 145, 110);
        }

        color = barandilla;
        svgGraphic.setColor(color);
        svgGraphic.fillRect(xOrigin, yOrigin + 70 + 110, 7 * 145, 110);

        for (int i = 0; i < 3; i++) {
            color = ventanaClara;
            svgGraphic.setColor(color);
            svgGraphic.fillRect(xOrigin + 145 * (2 * i + 1), yOrigin + 70 + 110, 145, 110);
        }

        color = remateFachada;
        svgGraphic.setColor(color);
        svgGraphic.fillRect(xOrigin, yOrigin + 70 + 110 + 110, 7 * 145, 45);
    }
}
