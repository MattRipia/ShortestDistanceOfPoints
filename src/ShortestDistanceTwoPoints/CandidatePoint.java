package ShortestDistanceTwoPoints;
import java.awt.Point;

public class CandidatePoint
{
    Point p1;
    Point p2;
    Double distance;
    
    public CandidatePoint(Point p1, Point p2)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.distance = Point.distance(p1.x, p1.y, p2.x, p2.y);
    }
    
    public CandidatePoint()
    {
    }
    
    @Override
    public String toString()
    {
        String temp = "";
        temp += "1: " + p1.x +"," + p1.y + "\n";
        temp += "2: " + p2.x +"," + p2.y + "\n";
        temp += "D: " + distance;
        return temp;
    }
}
