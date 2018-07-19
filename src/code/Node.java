package code;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    private ArrayList<Road> roads;
    private Location location;
    private String id;
    private Point drawingPoint;

    public Node(String id,Location location, Road ... roads){
        this.roads = new ArrayList<>(Arrays.asList(roads));
        this.location = location;
        this.id = id;
        this.drawingPoint = location.asPoint(location,1.5);
    }

    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.fillOval(drawingPoint.x+200,drawingPoint.y,4,4);
    }

    @Override
    public String toString(){
        return id+": "+drawingPoint.x+", "+drawingPoint.y;
    }
}
