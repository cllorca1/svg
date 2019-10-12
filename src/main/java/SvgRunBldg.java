import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUnits;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SvgRunBldg {

    private static final Color remateFachada = new Color(255, 230, 153);
    private static Color fachadaSombra = new Color(91, 74, 45);
    private static Color fachada = new Color(204, 179, 109);
    private static Color ventanaMuyOscura = new Color(35, 35, 35);
    private static Color ventanaMedia = new Color(64, 64, 64);
    private static Color barandilla = new Color(187, 178, 139);
    private static Color barandillaClara = new Color(166, 187, 182);
    private static Color barandillaSombra = new Color(134, 125, 108);
    private static Color ventanaClara = new Color(118, 113, 113);

    private static Random random = new Random();

    public static void main(String[] args) throws IOException {

        int width = 2000;
        int height = 4000;

        SVGGraphics2D svgGraphic = new SVGGraphics2D(width, height, SVGUnits.MM);
        Random random = new Random();

        int xOrigin = 250;
        int yOrigin = 250;

        int alturaPiso = 70 + 110 + 110 + 45;

        for (int piso = 0; piso < 30; piso++) {

            int thisYOrigin = yOrigin + piso * (alturaPiso);
            dibujaBalcon1(svgGraphic, xOrigin, thisYOrigin);
            dibujaLateralIzq(svgGraphic, xOrigin, thisYOrigin);
            dibujaBloqueVacio(svgGraphic, xOrigin + 7 * 145, thisYOrigin);
            dibujaBalcon1(svgGraphic, xOrigin + 145 * 8, thisYOrigin);
            dibujaSombraBalcon1Izquierdo(svgGraphic, xOrigin + 145 * 8, thisYOrigin);
            dibujaBloqueVacio(svgGraphic, xOrigin + 15 * 145, thisYOrigin);
            dibujaBalcon1(svgGraphic, xOrigin + 145 * 16, thisYOrigin);
            dibujaSombraBalcon1Izquierdo(svgGraphic, xOrigin + 145 * 16, thisYOrigin);
            dibujaLateralDerecho(svgGraphic, xOrigin + 23 * 145, thisYOrigin);
            dibujaRopa(svgGraphic, xOrigin, thisYOrigin);
            dibujaRopa(svgGraphic, xOrigin + 145 * 8, thisYOrigin);
            dibujaRopa(svgGraphic, xOrigin + 145 * 16, thisYOrigin);


        }


        svgGraphic.clipRect(0, 0, width, height);
        SVGUtils.writeToSVG(new File("file.svg"), svgGraphic.getSVGElement());


    }

    private static void dibujaRopa(SVGGraphics2D svgGraphic, int xOrigin, int thisYOrigin) {
        int numberOfPieces = random.nextInt(10);
        int initialPosition = random.nextInt(145 * 7);
        int minWidth = 10;
        int maxWidth = 100;
        int minHeight = 30;
        int maxHeight = 120;

        boolean arriba = false;


        int piece = 1;
        int oscurecimiento = arriba? 50 : 0;
        int altura = arriba? 55 : 185;
        int xMax = initialPosition + xOrigin;
        int xMin = initialPosition + xOrigin;
        while (initialPosition + maxWidth < 145 * 7 && piece < numberOfPieces) {

            Color pieceColor = new Color(255 - random.nextInt(30) - oscurecimiento,
                    255 - random.nextInt(30) - oscurecimiento,
                    255 - random.nextInt(30) - oscurecimiento);

            int width = minWidth + Math.round(random.nextFloat() * (maxWidth - minWidth));
            int height = minHeight + Math.round(random.nextFloat() * (maxHeight - minHeight));
            if (!arriba) {
                svgGraphic.setColor(barandillaSombra);
                svgGraphic.fillRect(xOrigin + 10 + initialPosition, thisYOrigin + altura + 10, width, height);

            }
            svgGraphic.setColor(pieceColor);
            svgGraphic.fillRect(xOrigin + initialPosition, thisYOrigin + altura, width, height);
            int separation = 5 + random.nextInt(20);
            initialPosition = initialPosition + width + separation;
            piece++;
            xMax += width + separation;
        }

        if (xMin != xMax){
            svgGraphic.setColor(ventanaMuyOscura);
            svgGraphic.fillRect(xMin - 5, thisYOrigin+ altura, xMax - xMin + 10, 3);
        }
    }

    private static void dibujaBloqueVacio(SVGGraphics2D svgGraphic, int xOrigin, int yOrigin) {
        svgGraphic.setColor(remateFachada);
        svgGraphic.fillRect(xOrigin, yOrigin, 145, 70 + 110 + 110 + 45);
        svgGraphic.setColor(fachadaSombra);
        svgGraphic.fillRect(xOrigin, yOrigin, 145, 15);

        svgGraphic.setColor(fachada);
        svgGraphic.fillRect(xOrigin, yOrigin + 70 + 110 + 105, 145, 5);
    }

    private static void dibujaLateralIzq(SVGGraphics2D svgGraphic, int xOrigin, int yOrigin) {

        svgGraphic.setColor(barandillaClara);
        svgGraphic.fillRect(xOrigin - 80, yOrigin + 70 + 110, 80, 110);

        svgGraphic.setColor(remateFachada);
        svgGraphic.fillRect(xOrigin - 80, yOrigin + 70 + 110 + 110, 80, 45);

        svgGraphic.setColor(barandillaSombra);
        svgGraphic.fillRect(xOrigin - 80, yOrigin + 70 + 107, 80, 3);
    }

    private static void dibujaLateralDerecho(SVGGraphics2D svgGraphic, int xOrigin, int yOrigin) {

        svgGraphic.setColor(barandillaClara);
        svgGraphic.fillRect(xOrigin, yOrigin + 70 + 110, 80, 110);

        svgGraphic.setColor(remateFachada);
        svgGraphic.fillRect(xOrigin, yOrigin + 70 + 110 + 110, 80, 45);

        svgGraphic.setColor(barandillaSombra);
        svgGraphic.fillRect(xOrigin, yOrigin + 70 + 107, 80, 3);
    }

    private static void dibujaBalcon1(SVGGraphics2D svgGraphic, int xOrigin, int yOrigin) {

        int posAC = random.nextInt(3);

        svgGraphic.setColor(fachadaSombra);
        svgGraphic.fillRect(xOrigin, yOrigin, 7 * 145, 70);

        for (int i = 0; i < 3; i++) {
            svgGraphic.setColor(ventanaMuyOscura);
            svgGraphic.fillRect(xOrigin + 145 * (2 * i + 1), yOrigin + 40, 145, 30);
            svgGraphic.fillRect(xOrigin + 145 * (2 * i + 1), yOrigin + 40, 25, 30);
            svgGraphic.setColor(new Color(55,55,55));
            svgGraphic.fillRect(xOrigin + 145 * (2 * i + 1) + 70, yOrigin + 40, 5, 30);
        }

        svgGraphic.setColor(fachada);
        svgGraphic.fillRect(xOrigin, yOrigin + 70, 7 * 145, 110);

        for (int i = 0; i < 3; i++) {
            int randomShade = 20 - random.nextInt(40);
            Color estaVentanaMedia = new Color(ventanaMedia.getRed() + randomShade,
                    ventanaMedia.getGreen() + randomShade,
                    ventanaMedia.getBlue() + randomShade);
            svgGraphic.setColor(estaVentanaMedia);
            svgGraphic.fillRect(xOrigin + 145 * (2 * i + 1), yOrigin + 70, 145, 110);
            svgGraphic.setColor(ventanaMuyOscura);
            svgGraphic.fillRect(xOrigin + 145 * (2 * i + 1), yOrigin + 70, 25, 110);
            svgGraphic.setColor(new Color(125,125,125));
            svgGraphic.fillRect(xOrigin + 145 * (2 * i + 1) + 70, yOrigin + 70, 5, 110);

        }

        svgGraphic.setColor(barandilla);
        svgGraphic.fillRect(xOrigin, yOrigin + 70 + 110, 7 * 145, 110);


        for (int i = 0; i < 3; i++) {
            svgGraphic.setColor(ventanaClara);
            svgGraphic.fillRect(xOrigin + 145 * (2 * i + 1), yOrigin + 70 + 110, 145, 110);
        }

        svgGraphic.setColor(remateFachada);
        svgGraphic.fillRect(xOrigin, yOrigin + 70 + 110 + 110, 7 * 145, 45);

        svgGraphic.setColor(barandillaSombra);
        svgGraphic.fillRect(xOrigin, yOrigin + 70 + 107, 7 * 145, 3);

        svgGraphic.setColor(fachadaSombra);
        svgGraphic.fillRect(xOrigin + 145* (3 + 2 * posAC) - 120 + 15, yOrigin + 10 + 15, 95, 70);
        svgGraphic.setColor(new Color (223, 217, 206));
        svgGraphic.fillRect(xOrigin + 145* (3 + 2 * posAC) - 120, yOrigin + 10, 95, 70);
        svgGraphic.setColor(new Color (87, 81, 74));
        svgGraphic.fillRect(xOrigin + 145* (3 + 2 * posAC) - 120, yOrigin + 10, 95, 35);
        svgGraphic.setColor(new Color (43, 38, 32));
        svgGraphic.fillOval(xOrigin + 145* (3 + 2 * posAC) - 120 + 5, yOrigin + 10 + 5, 60, 60);
    }
    private static void dibujaSombraBalcon1Izquierdo(SVGGraphics2D svgGraphic, int xOrigin, int yOrigin) {
        svgGraphic.setColor(fachadaSombra);
        svgGraphic.fillRect(xOrigin, yOrigin, 70, 180);

        svgGraphic.setColor(barandillaSombra);
        svgGraphic.fillRect(xOrigin, yOrigin + 180, 70, 110);

        svgGraphic.setColor(barandillaSombra);
        svgGraphic.fillRect(xOrigin, yOrigin + 70 + 107, 70, 3);
    }
}
