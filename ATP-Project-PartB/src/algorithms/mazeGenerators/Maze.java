package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * maze is represented by 2D array of Integers an has start Position and goal Position
 * the value at the array is 0,1 that 0 is empty cell and 1 is wall
 * implements Serializable because we wont to write to file with one server and read from file with other server
 */

public class Maze implements Serializable {
    private int[][] m_maze = null;
    protected int max_rowSize;
    protected int max_colSize;
    private Position StartPosition;
    private Position endPosition;
    protected byte[] bytes = null;

    /**
     *
     * @param bytee is a value that is + or - that convert from the byte we send
     * @return int. that is the real value of the byte we encoded to shrink the size of the maze
     */
    private int getRealVal(int bytee) {
        if (bytee < 0) {
            return 256 + bytee;
        } else {
            return bytee;
        }
    }

    /**
     * in the constructor we build maze, start position, end position from b that is byte[] type
     * for read right the sizes of maze, start, end we decided that if we see 255
     * the next number we need to sum with 255.
     * when we done to read sizes of maze, start, end, every byte in b represent a cell in maze
     * @param b all the byte that describe the maze after we decompressed the bytes
     */
    public Maze(byte[] b) {
        ArrayList<Integer> ConvertToInt = new ArrayList<Integer>();
        int index = 0;
        int sum = 0;
        while (ConvertToInt.size() < 6) {
            do {
                sum += getRealVal(b[index]);
                index++;
            } while (getRealVal(b[index - 1]) == 255);
            ConvertToInt.add(sum);
            sum = 0;
        }
        this.max_rowSize = ConvertToInt.get(0);
        this.max_colSize = ConvertToInt.get(1);
        this.StartPosition = new Position(ConvertToInt.get(2), ConvertToInt.get(3));
        this.endPosition = new Position(ConvertToInt.get(4), ConvertToInt.get(5));

        this.m_maze = new int[this.max_rowSize][this.max_colSize];
        this.bytes = b;

        for (int row = 0; row < this.max_rowSize; row++) {
            for (int col = 0; col < this.max_colSize; col++) {
                this.m_maze[row][col] = b[index];
                index++;
            }
        }
    }

    /**
     * to save run time we keep the bytes and for the next we called the method we just return the bytes
     * because the maze would not be changed from out side.
     * @return byte[] all the maze data
     */
    public byte[] toByteArray() {
        if (this.bytes == null)
            return toByteArrayHelper();
        return this.bytes;
    }

    /**
     * we convert to bytes the size, start and end that if we see 255, it is a sign that the next byte is connected to
     * previous number.
     * after we get all pf them. we represent the maze that every cell is 1 byte
     * @return byte[] convert the maze size, start position, end position and the grid of the maze to byte.
     */
    private byte[] toByteArrayHelper() {
        Vector byteList = new Vector();

        int[] nums_maze = new int[]{this.max_rowSize, this.max_colSize, this.StartPosition.getRowIndex(),
                this.StartPosition.getColumnIndex(), this.endPosition.getRowIndex(), this.endPosition.getColumnIndex()};

        int num;
        Integer t;
        byte b;
        Integer bigNum;
        Integer big = 255;
        b = big.byteValue();
        byte newB;

        for (num = 0; num < 6; num++) {
            bigNum = (Integer) nums_maze[num];
            while (bigNum >= 255) {
                byteList.add(b);
                bigNum -= 255;
            }
            newB = bigNum.byteValue();
            byteList.add(newB);
        }

        for (int row = 0; row < this.max_rowSize; row++) {
            for (int col = 0; col < this.max_colSize; col++) {
                t = this.m_maze[row][col];
                byteList.add(t.byteValue());
            }
        }

        this.bytes = new byte[byteList.size()];

        for(int i =0; i< byteList.size(); i++){
            this.bytes[i] = (byte) byteList.get(i);
        }
        return this.bytes;
        /*Vector byteList = new Vector();
        int[] nums_maze = new int[]{this.max_rowSize, this.max_colSize, this.StartPosition.getRowIndex(),
                this.StartPosition.getColumnIndex(), this.endPosition.getRowIndex(), this.endPosition.getColumnIndex()};

        int num;
        int lSize;
        byte b;
        Integer bigNum;
        Integer big = 255;
        b = big.byteValue();
        byte newB;
        for (num = 0; num < 6; num++) {
            bigNum = (Integer) nums_maze[num];
            while (bigNum >= 255) {
                byteList.add(b);
                bigNum -= 255;
            }
            newB = bigNum.byteValue();
            byteList.add(newB);
        }

        Integer sum = 0;
        int counter = 0;
        int temp = 0;

        for (int row = 0; row < this.max_rowSize; row++) {
            for (int col = 0; col < this.max_colSize; col++) {
                if((col + counter) < this.max_colSize ) {
                    if (counter == 8) {
                        sum = 0;
                        counter = 0;
                    }
                    if (this.m_maze[row][col + counter] == 1) {
                        temp = (int) Math.pow(2, counter);
                        sum += temp;
                    }
                    if (counter == 7) {
                        byteList.add(sum.byteValue());
                    }
                    counter++;
                }
            }
        }
        if ( counter > 0 && counter < 7) {
            byteList.add(sum.byteValue());
        }
        lSize = byteList.size();
        this.bytes = new byte[lSize];
        for (int i = 0; i < lSize; i++) {
            this.bytes[i] = (byte) byteList.get(i);
        }

        return this.bytes;*/
    }

    /**
     * @param rowSize,colSize the constructor is private to ensure that only mazeGeanerator function can instance maze
     */
    private Maze(int rowSize, int colSize) {
        this.m_maze = new int[rowSize][colSize];
        this.max_rowSize = rowSize;
        this.max_colSize = colSize;

        if (rowSize > 0 && colSize > 0) {

            Random r_start = new Random();
            Random c_start = new Random();

            Random r_Goal = new Random();
            Random c_Goal = new Random();

            Random r_state = new Random();
            do {
                int startRow = r_start.nextInt(this.max_rowSize);
                int startCol = c_start.nextInt(this.max_colSize);
                int GoalRow = r_Goal.nextInt(this.max_rowSize);
                int GoalCol = c_Goal.nextInt(this.max_colSize);

                int state = r_state.nextInt(2);

                if (state == 0) {
                    StartPosition = new Position(startRow, 0);
                    endPosition = new Position(GoalRow, this.max_colSize - 1);
                } else {
                    StartPosition = new Position(0, startCol);
                    endPosition = new Position(this.max_rowSize - 1, GoalCol);
                }
            } while (StartPosition.equals(endPosition));
        }
    }

    /**
     * @param row,col need for create Maze
     *                this function ensure that a new maze can be instance only by the type MazeGenerator
     *                and this function is static that the world can access  to her and create maze
     * @return Mzae
     */
    public static Maze MazeGenerator(int row, int col) {
        Maze newMaze = new Maze(row, col);
        return newMaze;
    }

    public boolean is_valid(int row, int col) {
        if ((row >= 0) && (row < this.max_rowSize) && (col >= 0) && (col < this.max_colSize)) {
            return true;
        }
        return false;
    }

    /**
     * @param p get Position in the Maze and changed the value to zero.
     */
    public void make_zero(Position p) {
        this.m_maze[p.getRowIndex()][p.getColumnIndex()] = 0;
    }

    public int getM_rowSize() {
        return max_rowSize;
    }

    public int getM_colSize() {
        return max_colSize;
    }

    public void set_val(int row, int col, int val) {
        if (is_valid(row, col))
            this.m_maze[row][col] = val;
    }

    public int get_val(int row, int col) {
        if (is_valid(row, col)) {
            return this.m_maze[row][col];
        }
        return -1;
    }

    public Position getStartPosition() {
        return this.StartPosition;
    }

    public void setStartPosition(int row, int col)
    {
        this.StartPosition = new Position(row,col);
    }

    public Position getGoalPosition() {
        return this.endPosition;
    }

    public int[][] getMaze(){
        return this.m_maze;
    }

    /**
     * Print the Maze that by this instructions:
     * 0 is empty
     * 1 is wall
     * E = Entrance is strat of Maze
     * G = Goal, is End of Maze
     */

    public void print() {
        if (this.m_maze.length == 0 || m_maze == null) {
            return;
        }
        int start_r = this.StartPosition.getRowIndex();
        int start_c = this.StartPosition.getColumnIndex();
        int goal_r = this.endPosition.getRowIndex();
        int goal_c = this.endPosition.getColumnIndex();

        for (int r = 0; r < this.max_rowSize; r++) {
            for (int c = 0; c < this.max_colSize; c++) {
                if ((r == start_r) && (c == start_c)) {
                    System.out.print("S");
                } else if ((r == goal_r) && (c == goal_c)) {
                    System.out.print("E");
                } else {
                    /*if(this.m_maze[r][c] == 1)
                    {
                        System.out.print("▓");
                    }
                    else
                    {
                        System.out.print(" ");
                    }*/
                    System.out.print(this.m_maze[r][c]);
                }
                if (c == this.max_colSize - 1) {
                    System.out.println("");
                }
            }
        }
    }
}
