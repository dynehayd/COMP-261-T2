package code;

import javafx.scene.input.MouseDragEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Map_Comp261 extends GUI {
    private HashMap<Integer,Node> nodeList = new HashMap<>();
    private HashMap<Integer,Road> roadList = new HashMap<>();

    private int scale = 1;
    private Location drawOrigin = Location.newFromLatLon(-36.847622,174.763444);

    @Override
    protected void redraw(Graphics g) {
        //draw all the nodes
        for (Node node : nodeList.values()) {
            node.draw(g,drawOrigin,scale,Color.red);
        }

        //draw all the roads
        for (Road road : roadList.values()){
            road.draw(g,drawOrigin,scale,Color.RED);
        }
    }

    @Override
    protected void onClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Point currentNodePoint;
        Node closestNode = null;
        int value = Integer.MAX_VALUE;

        for (Node node : nodeList.values()) {
            currentNodePoint = node.asPoint(drawOrigin,scale);
            int total = Math.abs(currentNodePoint.x-x) + Math.abs(currentNodePoint.y-y);

            if (total < value){
                value = total;
                closestNode = node;
            }
        }

        if (closestNode!=null) {
            System.out.println(closestNode.toString());
            closestNode.setHiglighted(true);
        }

        redraw();
    }

    @Override
    protected void onMouseDrag(MouseEvent e) {
        int dx = e.getX() - e.getXOnScreen();
        int dy = e.getY() - e.getYOnScreen();

//        drawOrigin = drawOrigin.moveBy(dx,dy);
        redraw();
    }

    @Override
    protected void onMouseWheel(MouseWheelEvent e) {
        scale += e.getWheelRotation();
        redraw();
    }


    @Override
    protected void onSearch() {
        String query = getSearchBox().getText();
        for (Road road : roadList.values()) {
            if (road.getRoadName().toLowerCase().contains(query.toLowerCase())){
                road.setHighlighted(true);
            }
        }
    }

    @Override
    protected void onMove(Move m) {
        switch (m) {
            case NORTH:
                drawOrigin = drawOrigin.moveBy(0,5);
                break;
            case SOUTH:
                drawOrigin = drawOrigin.moveBy(0,-5);
                break;
            case EAST:
                drawOrigin = drawOrigin.moveBy(5,0);
                break;
            case WEST:
                drawOrigin = drawOrigin.moveBy(-5,0);
                break;
            case ZOOM_IN:
                scale += 1;
                break;
            case ZOOM_OUT:
                scale -= 1;
                break;
        }

        redraw();
    }

    @Override
    protected void onLoad(File nodes, File roads, File segments, File polygons) {
        try {

            //setting up the buffered readers for each file
            BufferedReader brNodes = new BufferedReader(new FileReader(nodes));
            BufferedReader brRoads = new BufferedReader(new FileReader(roads));
            BufferedReader brSegments = new BufferedReader(new FileReader(segments));
            BufferedReader brPolygons = new BufferedReader(new FileReader(polygons));

            brNodes.lines().forEach(s -> {
                String[] nodeInformation = s.split("\t");

                //making a node from the information gathered by the file
                Location location = Location.newFromLatLon(
                        Double.parseDouble(nodeInformation[1]),
                        Double.parseDouble(nodeInformation[2]));
                Node node = new Node(nodeInformation[0],location);
                nodeList.put(Integer.parseInt(nodeInformation[0]),node);
            });

            brRoads.lines().filter(s -> !s.startsWith("roadid")).forEach(s->{
                String[] roadInformation = s.split("\t");

                //gets the attributes for the current road
                int roadId = Integer.parseInt(roadInformation[0]);
                int roadType = Integer.parseInt(roadInformation[1]);
                String roadName = roadInformation[2];
                String roadCity = roadInformation[3];
                int oneWay = Integer.parseInt(roadInformation[4]);
                int roadSpeed = Integer.parseInt(roadInformation[5]);
                int roadClass = Integer.parseInt(roadInformation[6]);
                int notForCar = Integer.parseInt(roadInformation[7]);
                int notForPede = Integer.parseInt(roadInformation[8]);
                int notForBike = Integer.parseInt(roadInformation[9]);

                this.roadList.put(roadId,new Road(roadId,roadType,roadName,roadCity,oneWay,roadSpeed,
                        roadClass,notForCar,notForPede,notForBike));
                //will need to add segments and nodes later
            });

            brSegments.lines().filter(s -> !s.startsWith("roadID")).forEach(s -> {
                String[] segmentInformation = s.split("\t");

                //gets the attributes for the current segment
                int roadID = Integer.parseInt(segmentInformation[0]);
                double edgeLength = Double.parseDouble(segmentInformation[1]);
                Node nodeTo = nodeList.get(Integer.parseInt(segmentInformation[2]));
                Node nodeFrom = nodeList.get(Integer.parseInt(segmentInformation[3]));

                ArrayList<Double> latitudeList = new ArrayList<>();
                ArrayList<Double> longitudeList = new ArrayList<>();

                //gets the latitude and longitude for each bit in the segment
                for (int i = 4; i < segmentInformation.length; i++) {
                    if (i%2==0){
                        latitudeList.add(Double.parseDouble(segmentInformation[i]));
                    }else{
                        longitudeList.add(Double.parseDouble(segmentInformation[i]));
                    }
                }

                roadList.get(roadID).addSegment(new Segment(nodeTo,nodeFrom,edgeLength,latitudeList,longitudeList));

                //add shit for the segments in the each node

            });

            brSegments.close();
            brRoads.close();
            brNodes.close();
        } catch (IOException ignore) {
        }
    }

    public static void main(String[] args) {
        new Map_Comp261();
    }

}
