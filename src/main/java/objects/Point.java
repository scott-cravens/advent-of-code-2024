package objects;

// Simple class for point
public class Point {
    public int x, y;
    public Point(int x, int y) { this.x = x; this.y = y; }

    public Point getNextPosition(Direction dir) {
        return dir.move(this);
    }
}
