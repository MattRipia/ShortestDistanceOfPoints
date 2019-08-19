package ShortestDistanceTwoPoints;

// given a collection of points, find the closest pair

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class ShortestPointMain {
    
    public static void main(String[] args) 
    {
        // create a collection of points
        ArrayList<Point> points = new ArrayList();
        Random rand = new Random();
        
        // initialse random values
        for(int i = 0; i < 10; i ++){
            points.add(new Point(rand.nextInt(10) + 1, rand.nextInt(10) + 1));
        }
        
        // sort the collection by x value so we can split the points into two halfs
        sortX(points);
        
        // recursive call, constantly splitting the list till we get a max of two for each sub-problem
        CandidatePoint dc = findClosestPair(points);
        
        // a brute force approach checking all combinations to check the above result
        CandidatePoint bf = bruteForceCheck(points);
        
        System.out.println("closest points (d&c): \n" + dc.toString() + "\n");
        System.out.println("closest points (bruteForce): \n" + bf.toString());
    }

    private static CandidatePoint findClosestPair(ArrayList<Point> points) 
    {   
        CandidatePoint closestPoints = null;
        
        // split the list in halfs
        ArrayList<Point> firstHalf = new ArrayList();
        ArrayList<Point> secondHalf = new ArrayList();
        
        // only split if the array has more than 2 entries
        if(points.size() > 2)
        {
            int half = points.size() / 2;
            System.out.println("\nFirstHalf - ");
            for(int i = 0 ; i < half; i ++)
            {
                System.out.println("Index: " + i + " x: " + points.get(i).x + " y: " + points.get(i).y);
                firstHalf.add(points.get(i));
            }
            
            System.out.println("SecondHalf - ");
            for(int i = half ; i < points.size(); i ++)
            {
                System.out.println("Index: " + i + " x: " + points.get(i).x + " y: " + points.get(i).y);
                secondHalf.add(points.get(i));
            }
        }
        
        CandidatePoint firstHalfClosest = null;
        CandidatePoint secondHalfClosest = null;
        
        if(firstHalf.size() > 2)
        {
            firstHalfClosest = findClosestPair(firstHalf);
        }
        else if(firstHalf.size() == 2)
        {
            firstHalfClosest = new CandidatePoint(firstHalf.get(0), firstHalf.get(1));
        }
        
        if(secondHalf.size() > 2)
        {
            secondHalfClosest = findClosestPair(secondHalf);
        }
        else if(secondHalf.size() == 2)
        {
            secondHalfClosest = new CandidatePoint(secondHalf.get(0), secondHalf.get(1));
        }
        
        if(firstHalfClosest == null)
        {
            closestPoints = secondHalfClosest;
        }   
        else if (secondHalfClosest == null)
        {
            closestPoints = firstHalfClosest;
        }
        else if (firstHalfClosest.distance < secondHalfClosest.distance)
        {
            closestPoints = firstHalfClosest;
        }   
        else 
        {
            closestPoints = secondHalfClosest;
        }
        
        // checks the band inbetween the firstHalf and secondHalf collections
        closestPoints = checkMidRange(closestPoints, firstHalf, secondHalf);
        return closestPoints;
    }
    
    // cehcks the band between the first and second halfs.
    private static CandidatePoint checkMidRange(CandidatePoint currentClosest, ArrayList<Point> firstHalf, ArrayList<Point> secondHalf) 
    {
        // get furtherest right/left from in first/second half
        Point mostRightFirstHalf = firstHalf.get(firstHalf.size() - 1);
        Point mostLeftSecondHalf = secondHalf.get(0);
        
        // find the x value inbetween the above points
        int midPoint = (mostRightFirstHalf.x + mostLeftSecondHalf.x) / 2;
        
        // get the low/high x value by halfing the current closest distance and adding it to the midpoint
        int lowRange = midPoint - (int)Math.round(currentClosest.distance / 2);
        int highRange = midPoint + (int)Math.round(currentClosest.distance / 2);
        
        
        // find the range we need to check by using the currentClosest set of points
        System.out.println("curr lowest point distance: " + currentClosest.distance);
        System.out.println("low x : " + lowRange);
        System.out.println("high x: " + highRange + "\n");
        
        // add the points that are in range of the high/low points to a new array
        ArrayList<Point> pointsInRange = new ArrayList();
        for(Point p : firstHalf)
        {
            if(p.x >= lowRange && p.x <= highRange)
            {
                pointsInRange.add(p);
                System.out.println("added: " + p.x + "," + p.y);
            }
        }
        
        for(Point p : secondHalf)
        {
            if(p.x >= lowRange && p.x <= highRange)
            {
                pointsInRange.add(p);
                System.out.println("added: " + p.x + "," + p.y);
            }
        }
        
        // sorting the in range points on y
        sortY(pointsInRange);
        
        // for each point in range, check it against the others
        for(int i = 0 ; i < pointsInRange.size() - 1; i ++)
        {
            Point p1 = pointsInRange.get(i);
            for(int j = i + 1; j < pointsInRange.size(); j++)
            {
                Point p2 = pointsInRange.get(j);
                CandidatePoint candidate = new CandidatePoint(p1, p2);
                
                if(candidate.distance < currentClosest.distance)
                {
                    System.out.println("found a new lowest -\n " + candidate.toString());
                    currentClosest = candidate;
                }
            }
        }
        
        return currentClosest;
    }
    
    private static ArrayList<Point> sortX(ArrayList<Point> points) 
    {
        System.out.println("Before Sort - ");
        for(Point p : points)
        {
            System.out.println("x: " + p.x + " y: " + p.y);   
        }
        
        Collections.sort(points, new Comparator<Point>()
        {
            
            @Override
            public int compare(Point p1, Point p2){
                return Double.compare(p1.getX(), p2.getX());
            }
        });
        
        System.out.println("After Sort - ");
        for(Point p : points)
        {
            System.out.println("x: " + p.x + " y: " + p.y);   
        }
        
        return points;
    }
    
    private static ArrayList<Point> sortY(ArrayList<Point> points) 
    {
        System.out.println("Before Sort - ");
        for(Point p : points)
        {
            System.out.println("x: " + p.x + " y: " + p.y);   
        }
        
        Collections.sort(points, new Comparator<Point>()
        {
            
            @Override
            public int compare(Point p1, Point p2){
                return Double.compare(p1.getY(), p2.getY());
            }
        });
        
        System.out.println("After Sort - ");
        for(Point p : points)
        {
            System.out.println("x: " + p.x + " y: " + p.y);   
        }
        
        return points;
    }
    
    private static CandidatePoint bruteForceCheck(ArrayList<Point> points)
    {
        ArrayList<CandidatePoint> fullList = new ArrayList();
        
        for(Point firstLoop : points)
        {
            for(Point secondLoop : points)
            {
                // if we are looking at different points
                if(firstLoop != secondLoop)
                {
                    CandidatePoint cp = new CandidatePoint(firstLoop, secondLoop);
                    fullList.add(cp);
                    System.out.println("Added: \n" + cp.toString() + "\n");
                }
            }
        }
        
        CandidatePoint lowest = null;
        for(CandidatePoint cp: fullList)
        {
            if(lowest == null){
                lowest = cp;
            }
            
            if(lowest.distance > cp.distance){
                lowest = cp;
            }
        }
        
        System.out.println("Lowest: \n" + lowest + "\n");
        return lowest;
    }
}
