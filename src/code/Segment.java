package code;

import java.awt.*;
import java.util.ArrayList;

public class Segment {
    Node to;
    Node from;

    double length;

    ArrayList<Double> latitude = new ArrayList<>();
    ArrayList<Double> longitude = new ArrayList<>();
    ArrayList<Location> locations = new ArrayList<>();

    public Segment(Node to, Node from,double length , ArrayList<Double> latitude, ArrayList<Double> longitude) {
        this.to = to;
        this.from = from;
        this.length = length;
        this.latitude = latitude;
        this.longitude = longitude;

        for (int i = 0; i < latitude.size(); i++) { //assign the locations based upon the lat and log
            locations.add(Location.newFromLatLon(latitude.get(i),longitude.get(i)));
        }

    }

    public void draw(Graphics g, Location origin, int scale, Color color){
        g.setColor(color);

        ArrayList<Point> points = new ArrayList<>();

        //gets the drawing points from the locations gathered by the segments file
        for (Location location : locations) {
            points.add(location.asPoint(origin,scale));
        }

        //draws the roads as lines
        for (int i = 0; i < points.size()-1; i++) {
            g.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
        }


    }

}
