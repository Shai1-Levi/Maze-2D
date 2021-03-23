package algorithms.mazeGenerators;
import java.util.Random;

/**
 * maze is represented by 2D array of Integers an has start Position and goal Position
 * the value at the array is 0,1 that 0 is empty cell and 1 is wall
 */

public class Maze {
    private int[][] m_maze = null;
    protected int max_rowSize;
    protected int max_colSize;
    private Position StartPosition;
    private Position endPosition;

    /**
     * @param rowSize,colSize
     * the constructor is private to ensure that only mazeGeanerator function can instance maze
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
                int startCol = c_start.nextInt( this.max_colSize);
                int GoalRow = r_Goal.nextInt(this.max_rowSize);
                int GoalCol = c_Goal.nextInt( this.max_colSize);

                int state = r_state.nextInt(2);

                if (state == 0)
                {
                    StartPosition = new Position(startRow, 0);
                    endPosition = new Position(GoalRow,  this.max_colSize-1);
                }
                else
                {
                    StartPosition = new Position(0, startCol);
                    endPosition = new Position(this.max_rowSize-1 , GoalCol);
                }
            }while(StartPosition.equals(endPosition));
        }
    }

    /**
     * @param row,col need for create Maze
     * this function ensure that a new maze can be instance only by the type MazeGenerator
     *  and this function is static that the world can access  to her and create maze
     * @return Mzae
     */
    public static Maze MazeGenerator(int row, int col){
        Maze newMaze = new Maze(row,col);
        return newMaze;
    }

    public boolean is_valid(int row, int col)
    {
        if((row >= 0) &&(row < this.max_rowSize) && (col >= 0) && (col < this.max_colSize))
        {
            return true;
        }
        return false;
    }

    /**
     * @param p get Position in the Maze and changed the value to zero.
     */
    public void make_zero(Position p){
        this.m_maze[p.getRowIndex()][p.getColumnIndex()]=0;
    }

    public int getM_rowSize() {
        return max_rowSize;
    }

    public int getM_colSize() {
        return max_colSize;
    }

    public void set_val(int row, int col, int val)
    {
        if(is_valid(row, col))
            this.m_maze[row][col] = val;
    }

    public int get_val(int row, int col)
    {
        if(is_valid(row, col))
        {
            return this.m_maze[row][col];
        }
        return -1;
    }

    public Position getStartPosition()
    {
        return this.StartPosition;
    }

    public Position getGoalPosition()
    {
        return this.endPosition;
    }

    /**
     * Print the Maze that by this instructions:
     * 0 is empty
     * 1 is wall
     * E = Entrance is strat of Maze
     * G = Goal, is End of Maze
     */

    public void print()
    {
        if(this.m_maze.length==0 || m_maze==null){return;}
        int start_r  = this.StartPosition.getRowIndex();
        int start_c  = this.StartPosition.getColumnIndex();
        int goal_r  = this.endPosition.getRowIndex();
        int goal_c = this.endPosition.getColumnIndex();

        for(int r = 0; r < this.max_rowSize; r++)
        {
            for(int c = 0; c < this.max_colSize; c++)
            {
                if((r == start_r) && (c == start_c))
                {
                    System.out.print("S");
                }
                else if((r == goal_r) && (c == goal_c))
                {
                    System.out.print("E");
                }
                else
                {
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
                if(c == this.max_colSize-1)
                {
                    System.out.println("");
                }
            }
        }
    }
}
