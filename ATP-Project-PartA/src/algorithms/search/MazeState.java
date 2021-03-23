package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * This Class represented a move in Maze by his position, parent and cost from this state to his parent
 */

public class MazeState extends AState {
    private Position currState;
    public String state;
    private int cost;
    private AState cameFrom;

    public MazeState(Position p) {

        this.currState = p;
        this.state = p.toString();
        this.cameFrom = null;
        this.cost = 0;
    }

    public MazeState(int Row, int Col){
        this.currState = new Position(Row,Col);
        this.state = this.currState.toString();
        this.cost = 0;
        this.cameFrom = null;
    }

    public MazeState(int Row, int Col, int cost , AState cameFrom){
        this.currState = new Position(Row,Col);
        this.state = this.currState.toString();
        this.cost = cost;
        this.cameFrom = cameFrom;
    }

    @Override
    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    @Override
    public AState getCameFrom()
    {
        return this.cameFrom;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public Position getCurrState() {
        return this.currState;
    }

    @Override
    public int getCost() {
        return cost;
    }

}
