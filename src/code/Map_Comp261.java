package code;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class Map_Comp261 extends GUI {
    private ArrayList<Node> nodeList = new ArrayList<>();

    @Override
    protected void redraw(Graphics g) {
        for (Node node : nodeList) {
            node.draw(g);
//            System.out.println(node.toString());
        }
    }

    @Override
    protected void onClick(MouseEvent e) {
        redraw();
    }

    @Override
    protected void onSearch() {

    }

    @Override
    protected void onMove(Move m) {

    }

    @Override
    protected void onLoad(File nodes, File roads, File segments, File polygons) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nodes));

            br.lines().forEach(s -> {
                String[] nodeInformation = s.split("\t");
                Location location = Location.newFromLatLon(
                        Double.parseDouble(nodeInformation[1]),
                        Double.parseDouble(nodeInformation[2]));
                Node node = new Node(nodeInformation[0],location);
                nodeList.add(node);
            });

            br.close();
        } catch (IOException ignore) {
        }
    }

    public static void main(String[] args) {
        new Map_Comp261();
    }

}
