import java.awt.geom.Point2D;

/**
 * Created by Никита on 28.09.2017.
 */
public class GeomFunctions {
    public static Point2D getSegmentCenter(Point2D a, Point2D b){
        return new Point2D.Double((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
    }

    public static Point2D getVector(Point2D a, Point2D b){
        return new Point2D.Double(b.getX() - a.getX(), b.getY() - a.getY());
    }

    public static double getVectorLength(Point2D a, Point2D b){
        return Math.hypot(b.getX() - a.getX(), b.getY() - a.getY());
    }

    public static double getVectorLength(Point2D vec){
        return Math.hypot(vec.getX(), vec.getY());
    }

    public static double[] getLineEquation(Point2D a, Point2D b){
        double[] mat = new double[3];
        mat[0] = a.getY() - b.getY();
        mat[1] = b.getX() - a.getX();
        mat[2] = a.getX()*b.getY() - a.getY()*b.getX();
        return mat;
    }

    public static Point2D rotatePoint(Point2D p, double rad){
        return new Point2D.Double(p.getX()*Math.cos(rad) - p.getY()*Math.sin(rad), p.getX()*Math.sin(rad) + p.getY()*Math.cos(rad));
    }

    public static double getAngle(Point2D a, Point2D b){
        Point2D vec = getVector(a, b);
        return Math.atan2(vec.getY(), vec.getX());
    }


}
