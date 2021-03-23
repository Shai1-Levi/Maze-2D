package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {
    /**
     @param row,column for initialize the grid of maze
     @return Maze = grid that he is a Maze
     */
    public abstract Maze generate(int row, int col);

    /**
     * @param numOfRows, numOfCols
     * @return the time that take to create a Maze
     */
    public long measureAlgorithmTimeMillis(int numOfRows, int numOfCols)
    {
        long start = System.currentTimeMillis();  //time of starting generate function
        Maze GenrMaze = generate(numOfRows, numOfCols);
        long end = System.currentTimeMillis();   //after generate function
        return (end - start);
    }

}
