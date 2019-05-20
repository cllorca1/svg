package grid;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUnits;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DrawTrips {

    public static void main(String[] args) throws IOException {

        Grid grid = new Grid();

        grid.generateGrid(0,0,10000,10000);


        grid.drawPath(2500,2500,8000,5000);



        SVGGraphics2D svg = new SVGGraphics2D(10000,10000, SVGUnits.PT);

        Color color = new Color(1,1,1);
        svg.setColor(color);
        svg.setStroke(new BasicStroke(20));

        Map<Integer, Map<Integer, Double>> hLinksMap = grid.gethLinks();
        for (int i : hLinksMap.keySet()){
            for (int j : hLinksMap.keySet()){
                if (hLinksMap.get(i).get(j)> 0){
                    int x1 = i*1000;
                    int y1 = j*1000;
                    svg.drawLine(x1,y1,x1+1000,y1);
                }
            }
        }

        Map<Integer, Map<Integer, Double>> vLinksMap = grid.getvLinks();

        for (int i : vLinksMap.keySet()){
            for (int j : vLinksMap.keySet()){
                if (vLinksMap.get(i).get(j)> 0){
                    int x1 = i*1000;
                    int y1 = j*1000;
                    svg.drawLine(x1,y1,x1,y1+1000);
                }
            }
        }

        SVGUtils.writeToSVG(new File("file.svg"), svg.getSVGElement());




    }

}
