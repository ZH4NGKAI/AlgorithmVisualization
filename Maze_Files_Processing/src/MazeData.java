import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeData {

    private int N, M;
    private char[][] maze;

    public MazeData(String filename) {

        if (filename == null)
            throw new IllegalArgumentException(" File Name cannot be null!");

        Scanner scanner = null;
        try {
            File file = new File(filename);
            if (!file.exists())
                throw  new IllegalArgumentException("File" + filename + "doesn't exist");
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            String nmline = scanner.nextLine();// scan first line to get N, M
            String[] nm = nmline.trim().split("\\s+");//trim() method removes whit espace from both ends of a string

            N = Integer.parseInt(nm[0]);
            M = Integer.parseInt(nm[1]);

            maze = new char[N][M];
            for (int i = 0; i < N; i++) {
                String line = scanner.nextLine();

                if (line.length() != M)
                    throw new IllegalArgumentException("Maze file " + filename + "is invalid");

                for (int j = 0; j < M; j++)
                    maze[i][j] = line.charAt(j);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (scanner != null)
                scanner.close();
        }
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public char getMaze(int i, int j) {
        if (!isValid(i, j))
            throw new IllegalArgumentException("i or j is out of index in maze! ");
        return maze[i][j];
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public void print() {
        System.out.println(N + " " + M);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)
                System.out.print(maze[i][j]);
            System.out.println();
        }
        return;
    }
}
