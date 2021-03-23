package algorithms.mazeGenerators;

/**
 * interface will have the connection to the ISearchable.
 * that connection allow ISearchable to solve other problems that similar to Maze problem
 */

public interface IMazeGenerator  {

    Maze generate(int numOfRows, int numOfCol);

    public long measureAlgorithmTimeMillis(int numOfRows, int numOfCol);

}
