package road;

import java.util.HashMap;
import java.util.Map;

public class Road {

    final int lanes;
    final double laneWidth;
    final Map<Integer, RoadElement> elements;


    public Road(int lanes, double laneWidth) {
        this.lanes = lanes;
        this.laneWidth = laneWidth;
        this.elements = new HashMap<>();
    }

    public int getLanes() {
        return lanes;
    }

    public Map<Integer, RoadElement> getElements() {
        return elements;
    }
}
