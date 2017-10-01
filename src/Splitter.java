import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by Никита on 28.09.2017.
 */
public class Splitter implements Runnable{
    final static double STEP_DIST = 1;
    final static double MIN_SEGMENT_LENGTH = 3;

    private Path path;
    private double offset;
    private int start_point;

    public Splitter(Path path, int start_point, double dist){
        this.path = path;
        this.start_point = start_point;
        offset = dist;
    }

    /**
     *
     * @param i номер первой точки разбиваемого сегмента
     * @param p точка, куда проводятся 2 новых сегмента
     */
    void splitSegment(int i, Point2D p){
        path.insertPoint(i, p);
    }

    double getOffset(){
        return offset;
    }

    void optimizePath(){
        for(int i = 0; i < path.getPointsNum() - 2; i++){
            Point2D a = path.getPoint(i);

            for(int j = path.getPointsNum() - 1; j > i + 1; j--){
                Point2D b = path.getPoint(j);
                Point2D c = searchObstacle(a, b, true);
                if(c == null) {
                    path.removePoints(i + 1, j - 1);
                    break;
                }
            }
        }
    }

    @Override
    public void run() {
        double seg_leng;
        do {
            for ( ; start_point < path.getPointsNum() - 1; start_point += 2) {
                Point2D a = path.getPoint(start_point);
                Point2D b = path.getPoint(start_point + 1);
                Point2D c = GeomFunctions.getSegmentCenter(a, b);

                if (Main.isObstacle(c)) {
                    Point2D dist = new Point2D.Double(STEP_DIST, 0);
                    dist = GeomFunctions.rotatePoint(dist, GeomFunctions.getAngle(a, b) + Math.PI / 2);
                    Point2D newC = searchNewPoint(c, dist);
                    Point2D newC180 = searchNewPoint(c, GeomFunctions.rotatePoint(dist, Math.PI));

                    if(newC != null && newC180 == null)
                        c = newC;
                    else if(newC == null && newC180 != null)
                        c = newC180;
                    else if (newC != null && newC180 != null){
                        c = newC;
                        ArrayList<Point2D> new_path = path.getPoints();
                        new_path.add(start_point + 1, newC180);
                        new Splitter(new Path(new_path), start_point + 2, offset).run();
                    }
                }
                path.insertPoint(start_point + 1, c);
            }
            seg_leng = GeomFunctions.getVectorLength(path.getPoint(0), path.getPoint(1));
            start_point = 0;
        } while(seg_leng > MIN_SEGMENT_LENGTH);

        optimizePath();
        Main.addPath(path);
    }

    private Point2D searchNewPoint(Point2D c, Point2D dist){
        Point2D newC = new Point2D.Double(c.getX(), c.getY());
        while (Main.intoMap(newC) && Main.isObstacle(newC)) {
            newC.setLocation(newC.getX() + dist.getX(), newC.getY() + dist.getY());
        }
        if(Main.intoMap(newC) && !Main.isObstacle(newC))
            return newC;
        else
            return null;
    }

    private Point2D searchObstacle(Point2D a, Point2D b, boolean obstacle){
        boolean find = false;
        Point2D c = new Point2D.Double(a.getX(), a.getY());
        Point2D vec = GeomFunctions.getVector(a, b);
        int iter = (int)(GeomFunctions.getVectorLength(a, b) / STEP_DIST);
        Point2D delta = new Point2D.Double(vec.getX() / iter, vec.getY() / iter);

        for(int k = 0; k < iter; k++){
            c.setLocation(c.getX() + delta.getX(), c.getY() + delta.getY());
            if(Main.isObstacle(c) && obstacle || !Main.isObstacle(c) && !obstacle){
                find = true;
                break;
            }
        }

        return find? c : null;
    }
}
