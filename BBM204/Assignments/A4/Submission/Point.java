import java.io.Serializable;

public class Point implements Serializable {
    static final long serialVersionUID = 22L;
    public int x, y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //A simple helper method
    public double calculateDistance(Point p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }
}
