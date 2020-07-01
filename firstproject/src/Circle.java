import java.awt.*;

public class Circle {

    public int x, y;
    private int r;
    private int vx, vy;
    public boolean isFilled = false;
    public Circle(int x, int y, int r, int vx, int vy) {

        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = vx;
        this.vy = vy;
    }

    public int getR() {return r;}

    public void move(Circle[] circles, int index, int minx, int miny, int maxx, int maxy) {

        x += vx;
        y += vy;
        checkCollision(circles, index, minx, miny, maxx, maxy);
    }

    private void checkCollision(Circle[] circles, int index, int minx, int miny, int maxx, int maxy) {

        for (int i = index + 1; i < circles.length; i++) {
            int dx = x - circles[i].x;
            int dy = y - circles[i].y;
            int d2 = dx * dx + dy * dy;
            if (d2 <= (r + circles[i].r) * (r + circles[i].r )) {
                int dvx = vx - circles[i].vx;
                int dvy = vy - circles[i].vy;
                int Dvx = (dvx * dx * dx + dvy * dx * dy) / d2;
                int Dvy = (dvy * dy * dy + dvx * dx * dy) / d2;
                vx = vx - Dvx;
                vy = vy - Dvy;
                circles[i].vx += Dvx;
                circles[i].vy += Dvy;
            }
        }
        if (x - r < minx) {
            x = r;
            vx = -vx;
        }
        if (x + r >= maxx) {
            x = maxx - r;
            vx = -vx;
        }
        if (y - r < miny) {
            y = r;
            vy = -vy;
        }
        if (y + r >= maxy) {
            y = maxy - r;
            vy = -vy;
        }
    }

    public boolean contain(Point p) {

        return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y) <= r * r;
    }
}
