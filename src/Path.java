import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Никита on 28.09.2017.
 */
public class Path implements Comparable {
    ArrayList<Point2D> points;

    public Path(ArrayList<Point2D> p){
        points = new ArrayList(p);
    }

    double getLength(){
        double length = 0;
        for(int i = 1; i < points.size(); i++){
            Point2D a = points.get(i-1);
            Point2D b = points.get(i);
            length += Math.hypot(b.getX() - a.getX(), b.getY() - a.getY());
        }
        return length;
    }

    int getPointsNum(){
        return points.size();
    }

    Point2D getPoint(int i){
        return points.get(i);
    }

    ArrayList<Point2D> getPoints() {
        return new ArrayList<>(points);
    }

    void removePoints(int i, int j){
        for(int k = i; k <= j; k++)
            points.remove(i);
    }

    void insertPoint(int i, Point2D p){
        points.add(i, p);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Path))
            return false;

        Path p = (Path) obj;
        if(p.getPointsNum() != getPointsNum())
            return false;

        boolean equal = true;
        for(int i = 0; i < getPointsNum(); i++){
            if(!getPoint(i).equals(p.getPoint(i))){
                equal = false;
                break;
            }
        }
        return equal;
    }

    public int compareTo(Object o) {
        if(!(o instanceof Path))
            return 0;
        Path p = (Path) o;
        double l1 = getLength();
        double l2 = p.getLength();
        return l1 > l2 ? 1 : l1 < l2 ? -1 : 0;
    }
}
