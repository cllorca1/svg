package grid;

import java.util.HashMap;
import java.util.Map;

public class Grid {

    private Map<Integer, Map<Integer, Double>> hLinks = new HashMap<Integer, Map<Integer, Double>>();
    private Map<Integer, Map<Integer, Double>> vLinks = new HashMap<Integer, Map<Integer, Double>>();
    private Map<Integer, Map<Integer, Double>> nodes = new HashMap<Integer, Map<Integer, Double>>();

    public Grid() {
    }

    public void generateGrid(double minX_m, double minY_m, double maxX_m, double maxY_m){

        double width = maxX_m - minX_m;
        double height = maxY_m - minY_m;

        int links_width = (int) Math.floor(width/1000) + 1;
        int links_height = (int) Math.floor(height/1000) + 1;

        for (int i=0; i < links_height; i++){
            hLinks.put(i, new HashMap<Integer, Double>());
            vLinks.put(i, new HashMap<Integer, Double>());
            nodes.put(i, new HashMap<Integer, Double>());
            for (int j=0; j < links_width; j++){
                hLinks.get(i).put(j,0.);
                vLinks.get(i).put(j,0.);
                nodes.get(i).put(j,0.);
            }
            nodes.get(i).put(links_width,0.);
        }
        nodes.put(links_height, new HashMap<Integer, Double>());
        for (int j=0; j < links_width; j++){
            nodes.get(links_height).put(j,0.);
        }
        nodes.get(links_height).put(links_width,0.);
    }

    private void addHFlowAtLink(int hIndex, int vIndex, double flow){
        hLinks.get(hIndex).put(vIndex, hLinks.get(hIndex).get(vIndex) + flow);
    }

    private void addVFlowAtLink(int hIndex, int vIndex, double flow){
        vLinks.get(hIndex).put(vIndex, hLinks.get(hIndex).get(vIndex) + flow);
    }

    public void drawPath(double origX, double origY, double destX, double destY){
        //find origin node
        int origHIndex = (int) Math.floor(origX/1000);
        int origVIndex = (int) Math.floor(origY/1000);

        //find destination node
        int destHIndex = (int) Math.floor(destX/1000);
        int destVIndex = (int) Math.floor(destY/1000);

        int hDelta = origHIndex - destHIndex;
        int vDelta = origVIndex - destVIndex;

        int hSignum = (int) Math.signum(hDelta);
        int vSignum = (int) Math.signum(vDelta);

        int relH = 0;
        int relV = 0;
        int seqH = 0;
        int seqV = 0;

        int absH = origHIndex;
        int absV = destVIndex;

        while (seqH < Math.abs(hDelta)){
            seqH++;
            addHFlowAtLink(absH + relH, absV + relV, 1.);
            relH = relH - hSignum;

            while (seqV < Math.abs(vDelta)){
                seqV++;
                addVFlowAtLink(absH + relH, absV + relV, 1.);
                relV = relV - vSignum;
            }
        }
        //finalize

        if (seqH == Math.abs(hDelta)){
            //draw vertical line
            while (seqV < Math.abs(vDelta)){
                seqV++;
                addVFlowAtLink(absH + relH, absV + relV, 1.);
                relV = relV + vSignum;
            }

        } else if (seqV == Math.abs(vDelta)){
            //draw horizontal line
            while (seqH < Math.abs(hDelta)){
                seqH++;
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
