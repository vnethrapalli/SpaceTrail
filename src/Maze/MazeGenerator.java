package Maze;

import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator {
    private int[][] MAZE;
    public final int WALL = -1;
    public final int ROOM = 0;
    public final int UNCONNECTED = 2;
    public final int DEFAULT_DIMENSION=41;
    public final int NAN = Integer.MIN_VALUE;
    private Random rand = new Random();
    private int[] start = new int[2];
    private int[] end = new int[2];
    private ArrayList<Integer> border = new ArrayList<Integer>();
    private int numfrontiers = 0;

    public MazeGenerator() {
        MAZE = new int[DEFAULT_DIMENSION][DEFAULT_DIMENSION];
        populateMaze();
        prims();
    }

    public MazeGenerator(int x, int y) {
        if (x % 2 == 0 || y % 2 == 0) {
            throw new IllegalArgumentException("maze must have an odd number of rows and columns");
        }
        MAZE = new int[x][y];
        populateMaze();
        prims();
    }

    public void makeNewMaze(int x, int y) {
        if (x % 2 == 0 || y % 2 == 0) {
            throw new IllegalArgumentException("maze must have an odd number of rows and columns");
        }
        MAZE = new int[x][y];
        populateMaze();
        prims();
    }

    public void makeNewMaze() {
        MAZE = new int[DEFAULT_DIMENSION][DEFAULT_DIMENSION];
        populateMaze();
        prims();
    }

    private void prims() {
        start[0] = rand.nextInt(MAZE.length);
        start[1] = rand.nextInt(MAZE[0].length);
        end[0] = rand.nextInt(MAZE.length);
        end[1] = rand.nextInt(MAZE[0].length);

        if ((start[0] != 0 && start[0] != MAZE.length) || (start[1] != 0 && start[1] != MAZE.length)) {
            int temp2 = rand.nextInt(2);
            start[temp2] = rand.nextInt(2) * (MAZE.length - 1);
            //makes sure its a room instead of a wall
            start[1 - temp2] += (start[1 - temp2] % 2 == 0) ? 0 : 1;
        }

        if ((end[0] != 0 && end[0] != MAZE.length) || (end[1] != 0 && end[1] != MAZE.length)) {
            int temp2 = rand.nextInt(2);
            end[temp2] = rand.nextInt(2) * (MAZE.length - 1);
            //makes sure its a room instead of a wall
            end[1 - temp2] += (end[1 - temp2] % 2 == 0) ? 0 : 1;
        }

        MAZE[start[0]][start[1]] = ROOM;
        int roomx = start[0];
        int roomy = start[1];
        int temp;
        findFrontiers(roomx, roomy);

        do {
            // find num and pos of frontiers
            roomy = rand.nextInt(border.size());
            roomy += (roomy % 2 == 1) ? -1 : 0;
            roomx = border.get(roomy);
            temp = roomy;
            roomy = border.get(roomy + 1);
            border.remove(temp);
            border.remove(temp);
            numfrontiers--;
            MAZE[roomx][roomy] = ROOM;

            if (roomx % 2 == 0) {
                if (MAZE[roomx][roomy + 1] == UNCONNECTED) {
                    MAZE[roomx][roomy + 1] = ROOM;
                    roomy++;
                } else {
                    MAZE[roomx][roomy - 1] = ROOM;
                    roomy--;
                }
            } else {
                if (MAZE[roomx + 1][roomy] == 2) {
                    MAZE[roomx + 1][roomy] = ROOM;
                    roomx++;
                } else {
                    MAZE[roomx - 1][roomy] = ROOM;
                    roomx--;
                }
            }
            findFrontiers(roomx, roomy);
        } while (border.size() > 0);

        System.out.println("start: (" + start[0] + "," + start[1] + ") end: (" + end[0] + "," + end[1] + ")");
    }

    private void findFrontiers(int x, int y) {
        if (x % 2 != 0 || y % 2 != 0) {
            System.out.println(x + "," + y);
            throw new IllegalArgumentException("the coordinates inputted are not of a room");

        }
        if (x != 0) {
            if (MAZE[x - 2][y] == UNCONNECTED)
                addborder(x - 1, y);
            else {
                removeborder(x - 1, y);
            }
        }
        if (!(x + 2 > MAZE.length)) {
            if (MAZE[x + 2][y] == UNCONNECTED)
                addborder(x + 1, y);
            else {
                removeborder(x + 1, y);
            }
        }
        if (!(y + 2 > MAZE[x].length)) {
            if (MAZE[x][y + 2] == UNCONNECTED)
                addborder(x, y + 1);
            else {
                removeborder(x, y + 1);
            }
        }
        if (y != 0) {
            if (MAZE[x][y - 2] == UNCONNECTED)
                addborder(x, y - 1);
            else {
                removeborder(x, y - 1);
            }
        }
    }

    private void removeborder(int x, int y) {
        for (int i = 0; i < border.size(); i += 2) {
            if (border.get(i) == x && border.get(i + 1) == y) {
                border.remove(i);
                border.remove(i);
            }
        }
    }

    private void populateMaze() {
        for (int i = 0; i < MAZE.length; i++) {
            for (int j = 0; j < MAZE[i].length; j++) {
                if ((i % 2 == 0) && (j % 2 == 0)) {
                    MAZE[i][j] = UNCONNECTED;
                } else if (i % 2 == 1 && j % 2 == 1) {
                    MAZE[i][j] = NAN;
                } else {
                    MAZE[i][j] = WALL;
                }
            }
        }
    }

    public int getPoint(int x, int y) {
        if (x >= MAZE.length || y >= MAZE[x].length) {
            throw new IndexOutOfBoundsException("that is not in the range of the maze");
        }
        return MAZE[x][y];

    }

    public int[][] getMAZE() {
        return MAZE.clone();
    }

    public int getwidth() {
        return MAZE.length;
    }

    public int getheight() {
        return MAZE[0].length;
    }

    public int[] getStart() {
        return start.clone();
    }

    public int[] getEnd() {
        return end.clone();
    }

    private void addborder(int x, int y) {
        border.add(x);
        border.add(y);
        numfrontiers++;
    }

}