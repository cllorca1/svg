package odConectors;

import grid.Grid;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUnits;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawTrips {

    public static void main(String[] args) throws IOException {

        double scaleFactor = 0.001;

        String fileName = args[0];
        List<Trip> trips = readFile(fileName, scaleFactor);

        Map<String, Double> boundingBox = getBoundingBox(trips);

        Grid grid = new Grid();
        grid.generateGrid(boundingBox.get("minX") - 1000 , boundingBox.get("minY") - 1000 ,
                boundingBox.get("maxX") + 1000 , boundingBox.get("maxY") + 1000);


        int leftCorner = (int) Math.floor(boundingBox.get("minX"));
        int bottomCorner = (int) Math.floor(boundingBox.get("minY"));
        int topCorner = (int) Math.floor(boundingBox.get("maxY"));

        SVGGraphics2D svg = new SVGGraphics2D((int) (boundingBox.get("maxX") - leftCorner),
                (int) (boundingBox.get("maxY") - bottomCorner), SVGUnits.PT);
        Color color = new Color(201, 115, 114);
        svg.setColor(color);
        svg.setStroke(new BasicStroke(20));

        for (Trip trip : trips) {
            svg.drawLine((int) trip.origX - leftCorner,
                    topCorner - (int)trip.origY,
                    (int) trip.destX - leftCorner,
                    topCorner - (int) trip.destY);
        }

        SVGUtils.writeToSVG(new File("file.svg"), svg.getSVGElement());


    }

    static List<Trip> readFile(String fileName, double scaleFactor) throws IOException {
        List<Trip> trips = new ArrayList<Trip>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        br.readLine(); // skip CSV header
        while ((line = br.readLine()) != null) {
            if (Math.random() < scaleFactor) {
                try {
                    Trip trip = new Trip(line);
                    trips.add(trip);
                } catch (NumberFormatException e){

                }
            }
        }

        br.close();
        System.out.println("Number of trips = " + trips.size());
        return trips;
    }

    static class Trip {
        double origX;
        double origY;
        double destX;
        double destY;

        public Trip(String line) {
            String[] data = line.split(",");
            this.origX = Double.parseDouble(data[2]);
            this.origY = Double.parseDouble(data[3]);
            this.destX = Double.parseDouble(data[5]);
            this.destY = Double.parseDouble(data[6]);
        }
    }

    static Map<String, Double> getBoundingBox(List<Trip> trips) {
        Map<String, Double> boundingBox = new HashMap<String, Double>();

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (Trip trip : trips) {
            if (trip.destX < minX) {
                minX = trip.destX;
            }
            if (trip.origX < minX) {
                minX = trip.origX;
            }

            if (trip.destX > maxX) {
                maxX = trip.destX;
            }
            if (trip.origX > maxX) {
                maxX = trip.origX;
            }

            if (trip.destY < minY) {
                minY = trip.destY;
            }
            if (trip.origY < minY) {
                minY = trip.origY;
            }

            if (trip.destY > maxY) {
                maxY = trip.destY;
            }
            if (trip.origY > maxY) {
                maxY = trip.origY;
            }

        }
        boundingBox.put("minX", minX);
        boundingBox.put("maxX", maxX);
        boundingBox.put("minY", minY);
        boundingBox.put("maxY", maxY);
        return boundingBox;
    }

}
