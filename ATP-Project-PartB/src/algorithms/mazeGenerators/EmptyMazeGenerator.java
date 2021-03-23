package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {

    private Maze emptyMaze;

    /**
     * initialize the empty maze
     */
    public EmptyMazeGenerator() {
        this.emptyMaze = null;
    }

    /**
     * @param row,col
     * constructor with parameter that initialize the Maze
     */
    public EmptyMazeGenerator(int row, int col) {
        this.generate(row,col);
    }

    /**
     * @param row,column for creating initialize the grid of maze
     * @return Maze without walls
     */
    @Override
    public Maze generate(int row, int col) {
        Maze emptyMaze = Maze.MazeGenerator(row, col);

        return emptyMaze;
    }
}
