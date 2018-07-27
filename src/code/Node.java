package code;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    private ArrayList<Segment> outgoingEdges = new ArrayList<>();
    private ArrayList<Segment> incomingEdges = new ArrayList<>();
    private Location location;
    private String id;
    private Point drawingPoint;
    private boolean higlighted = false;

    public Node(String id,Location location){
        this.location = location;
        this.id = id;
    }

    public void draw(Graphics g, Location origin, int scale,Color color){

        if (higlighted){
            g.setColor(Color.blue);
        }else{
            g.setColor(color);
        }
        Point drawCoords = location.asPoint(origin,scale);
        int diameter = Math.abs(scale/10);
        g.fillOval(drawCoords.x,drawCoords.y,diameter,diameter);
    }

    public boolean addIncomingEdge(Segment segment){
        return incomingEdges.add(segment);
    }

    public boolean addOutgoingEdge(Segment segment){
        return outgoingEdges.add(segment);
    }

    public Point asPoint(Location origin, int scale){
        return location.asPoint(origin,scale);
    }

    @Override
    public String toString(){
        return id+": "+location.toString();
    }

    public boolean isHiglighted() {
        return higlighted;
    }

    public void setHiglighted(boolean higlighted) {
        this.higlighted = higlighted;
    }
}
