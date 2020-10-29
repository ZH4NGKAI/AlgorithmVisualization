import java.awt.*;
import java.util.Stack;

public class AlgoVisualizer {

    private static int DELAY = 5;
    private static int blockSide = 8;

    private MazeData data;
    private AlgoFrame frame;

    private static final int directions[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public AlgoVisualizer(String mazeFile){

        // 初始化数据
        data = new MazeData(mazeFile);
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){

        setData(-1, -1, false);

        Stack<Position> stack = new Stack();
        Position entrance = new Position(data.getEntranceX(), data.getEntranceY());
        stack.push(entrance);
        data.visited[entrance.getX()][entrance.getY()] = true;

        boolean isSolved = false;
        while (!stack.isEmpty()) {
            Position p = stack.pop();
            setData(p.getX(), p.getY(), true);
            if (p.getX() == data.getExitX() && p.getY() == data.getExitY()) {
                isSolved = true;
                findPath(p);
                break;
            }
            for (int[] direction: directions) {
                int newX = p.getX() + direction[0];
                int newY = p.getY() + direction[1];
                if (data.inArea(newX, newY) && data.getMaze(newX, newY) != MazeData.WALL && !data.visited[newX][newY]) {
                    stack.push(new Position(newX, newY, p));
                    data.visited[newX][newY] = true;
                }
            }
        }
        if (!isSolved)
            System.out.println("The maze has no solution!");
//        if (!go(data.getEntranceX(), data.getEntranceY()))
//            System.out.println("The maze has no solution!");

        setData(-1, -1, false);
    }

    private void findPath(Position des) {
        Position cur = des;
        while(cur != null) {
            data.res[cur.getX()][cur.getY()] = true;
            cur = cur.getPrev();
        }
    }

//    private boolean go(int x, int y) {
//        if (!data.inArea(x, y))
//            throw new IllegalArgumentException("x, y are out of index in go function!");
//        data.visited[x][y] = true;
//        setData(x, y,true);
//        System.out.println("x: " + x + " y: " + y);
//        if (x == data.getExitX() && y == data.getExitY())
//            return true;
//        for (int[] direction: directions) {
//            int newX = x + direction[0];
//            int newY = y + direction[1];
//            if (data.inArea(newX, newY) && data.getMaze(newX, newY) != MazeData.WALL && !data.visited[newX][newY])
//                if (go(newX, newY))
//                    return true;
//        }
//        setData(x, y, false);
//        return false;
//    }

    private void setData(int x, int y, boolean isPath){
        if (data.inArea(x, y))
            data.path[x][y] = isPath;
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        String mazeFile = "maze_101_101.txt";

        AlgoVisualizer vis = new AlgoVisualizer(mazeFile);

    }
}