import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    private static int DELAY = 5;
    private static int blockSide = 8;
    private static final int directions[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    // TODO: 创建自己的数据
    private MazeData data;        // 数据
    private AlgoFrame frame;    // 视图

    public AlgoVisualizer(int N, int M){

        data = new MazeData(N, M);
        int sceneHeight = data.getN() * blockSide;
        int sceneWidth = data.getM() * blockSide;

        EventQueue.invokeLater(()-> {
            frame = new AlgoFrame("Random Maze Generation Visualization", sceneWidth, sceneHeight);
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run() {

        setData(-1, -1);
        go(data.getEntranceX(), data.getEntranceY()+1);
        setData(-1, -1);
    }

    private void go(int x, int y) {
        if (!data.inArea(x, y))
            throw new IllegalArgumentException("x, y are out of index in go function!");
        data.visited[x][y] = true;
        for (int[] d: directions) {
            int newX = x + d[0] * 2;
            int newY = y + d[1] * 2;
            if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                setData(x + d[0], y + d[1]);
                go(newX, newY);
            }
        }
    }


    private void setData(int x, int y) {
        if (data.inArea(x, y))
            data.maze[x][y] = MazeData.ROAD;
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }
    public static void main(String[] args) {

        int N = 101;
        int M = 101;

        AlgoVisualizer vis = new AlgoVisualizer(N, M);
    }
}
