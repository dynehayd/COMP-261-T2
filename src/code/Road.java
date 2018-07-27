package code;

import java.awt.*;
import java.util.ArrayList;

public class Road {
    private int roadID;
    private int roadType;
    private String roadName;
    private String city;
    private int oneWay;
    private int roadSpeed;
    private int roadClass;
    private int notForCar;
    private int notForPede;
    private int notForBike;
    private ArrayList<Segment> segments = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();

    private boolean highlighted = false;

    public Road(int roadID, int roadType, String roadName, String city, int oneWay, int roadSpeed, int roadClass, int notForCar, int notForPede, int notForBike) {
        this.roadID = roadID;
        this.roadType = roadType;
        this.roadName = roadName;
        this.city = city;
        this.oneWay = oneWay;
        this.roadSpeed = roadSpeed;
        this.roadClass = roadClass;
        this.notForCar = notForCar;
        this.notForPede = notForPede;
        this.notForBike = notForBike;
        //this.segments = segments;
        //this.nodes = nodes;
    }

    public boolean addSegment(Segment segment){
        return segments.add(segment);
    }

    public boolean addNode(Node node){
        return nodes.add(node);
    }

    public void draw(Graphics g, Location origin,int scale, Color color){
        for (Segment segment : segments) {
            if (highlighted){
                segment.draw(g, origin, scale, Color.GREEN);
            }else {
                segment.draw(g, origin, scale, color);
            }
        }
    }

    @Override
    public String toString() {
        return "ID: " +roadID;
    }

    //getters and setters...

    public int getRoadID() {
        return roadID;
    }

    public int getRoadType() {
        return roadType;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getCity() {
        return city;
    }

    public int getOneWay() {
        return oneWay;
    }

    public int getRoadClass() {
        return roadClass;
    }

    public int getNotForCar() {
        return notForCar;
    }

    public int getNotForPede() {
        return notForPede;
    }

    public int getNotForBike() {
        return notForBike;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public int getRoadSpeed() {
        return roadSpeed;
    }


    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
}
