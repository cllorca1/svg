package grid;

import java.util.HashMap;
import java.util.Map;

public class Grid {

    private Map<Integer, Map<Integer, Double>> hLinks = new HashMap<Integer, Map<Integer, Double>>();
    private Map<Integer, Map<Integer, Double>> vLinks = new HashMap<Integer, Map<Integer, Double>>();
    private Map<Integer, Map<Integer, Double>> nodes = new HashMap<Integer, Map<Integer, Double>>();

    public Grid() {
    }

    public void generateGrid(double minX_m, double minY_m, double maxX_m, double maxY_m) {

        double width = maxX_m - minX_m;
        double height = maxY_m - minY_m;

        int links_width = (int) Math.floor(width / 1000) + 1;
        int links_height = (int) Math.floor(height / 1000) + 1;

        int minHIndex = (int) Math.floor(minX_m / 1000);
        int minVIndex = (int) Math.floor(minY_m / 1000);

        for (int i = minVIndex; i < minVIndex + links_height; i++) {
            hLinks.put(i, new HashMap<Integer, Double>());
            vLinks.put(i, new HashMap<Integer, Double>());
            nodes.put(i, new HashMap<Integer, Double>());
            for (int j = minHIndex; j < minHIndex + links_width; j++) {
                hLinks.get(i).put(j, 0.);
                vLinks.get(i).put(j, 0.);
                nodes.get(i).put(j, 0.);
            }
            nodes.get(i).put(minHIndex + links_width, 0.);
        }
        nodes.put(minVIndex + links_height, new HashMap<Integer, Double>());
        for (int j = minHIndex; j < minHIndex + links_width; j++) {
            nodes.get(minVIndex + links_height).put(j, 0.);
        }
        nodes.get(minVIndex + links_height).put(minHIndex + links_width, 0.);
    }

    private void addHFlowAtLink(int hIndex, int vIndex, double flow) {
        try {
        double current = hLinks.get(vIndex).get(hIndex);
        hLinks.get(vIndex).put(hIndex, current + flow); } catch(NullPointerException e){
            System.out.println("jjj");
        }
    }

    private void addVFlowAtLink(int hIndex, int vIndex, double flow) {

        try {
            double current = vLinks.get(vIndex).get(hIndex);
            vLinks.get(vIndex).put(hIndex, current + flow);
        } catch(NullPointerException e){
            System.out.println("jjj");
        }
        }


    public void drawPath(double origX, double origY, double destX, double destY) {

        //find origin node
        int origHIndex = (int) Math.floor(origX / 1000);
        int origVIndex = (int) Math.floor(origY / 1000);

        //find destination node
        int destHIndex = (int) Math.floor(destX / 1000);
        int destVIndex = (int) Math.floor(destY / 1000);

        int hDelta = destHIndex - origHIndex;
        int vDelta = destVIndex - origVIndex;

        int hSignum = (int) Math.signum(hDelta);
        int vSignum = (int) Math.signum(vDelta);

        int relH = 0;
        int relV = 0;

        int absH = origHIndex;
        int absV = destVIndex;

        while (relH != hDelta && relV != vDelta) {
            addHFlowAtLink(absH + relH, absV + relV, 1.);
            relH = relH + hSignum;

            if (vSignum > 0){
                addVFlowAtLink(absH + relH, absV + relV, 1.);
                relV = relV + vSignum;
            } else {
                relV = relV + vSignum;
                addVFlowAtLink(absH + relH, absV + relV, 1.);

            }


        }
        //finalize

        if (relH == hDelta) {
            //draw vertical line
            while (relV != vDelta) {
                addVFlowAtLink(absH + relH, absV + relV, 1.);
                relV = relV + vSignum;
            }

        } else if (relV == vDelta) {
            //draw horizontal line
            while (relH < hDelta) {
                addHFlowAtLink(absH + relH, absV + relV, 1.);
                relH = relH + hSignum;
            }
        }

    }


    public Map<Integer, Map<Integer, Double>> gethLinks() {
        return hLinks;
    }

    public Map<Integer, Map<Integer, Double>> getvLinks() {
        return vLinks;
    }
}
