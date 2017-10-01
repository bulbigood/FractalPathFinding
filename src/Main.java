import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Никита on 28.09.2017.
 */
public class Main {

    static BufferedImage map;
    static Point2D startPoint, endPoint;

    private static ArrayList<Path> paths = new ArrayList();

    public static synchronized void addPath(Path p){
        boolean equal = false;
        for(int i = 0; i < paths.size(); i++){
            if(paths.get(i).equals(p)){
                equal = true;
                break;
            }
        }
        if(!equal)
            paths.add(p);
    }

    public static void main(String args[]){
        try {
            map = ImageIO.read(new File("map2.bmp"));
        } catch (IOException e) {

        }

        startPoint = new Point2D.Double(0, map.getHeight() - 1);
        //endPoint = new Point2D.Double(map.getWidth() - 1, map.getHeight() - 1);
        endPoint = new Point2D.Double(map.getWidth() - 1, 0);

        ArrayList<Point2D> path = new ArrayList();
        path.add(startPoint);
        path.add(endPoint);

        Splitter splitter = new Splitter(new Path(path), 0, 0);
        splitter.run();

        new GraphicShow(paths.get(0));
    }

    public static boolean isObstacle(Point2D p){
        int rgb = map.getRGB((int)p.getX(), (int)p.getY());
        int red = (rgb >> 16 ) & 0x000000FF;
        int green = (rgb >> 8 ) & 0x000000FF;
        int blue = (rgb) & 0x000000FF;

        return red < 120 && green < 120 && blue < 120;
    }

    public static boolean intoMap(Point2D p){
        return p.getX() >= 0 && p.getY() >= 0 && p.getX() < map.getWidth() && p.getY() < map.getHeight();
    }

    private static Path getShortestPath(){
        Path p = paths.get(0);
        for(int i = 1; i < paths.size(); i++){
            if(paths.get(i).getLength() < p.getLength())
                p = paths.get(i);
        }
        return p;
    }
}
