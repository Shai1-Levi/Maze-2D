package algorithms.search;

import algorithms.mazeGenerators.*;

import java.util.ArrayList;

/**
 *
 * transforms maze into more generic type that make easier to deal with and to solve with different algorithms.
 */

public class SearchableMaze implements ISearchable{

    private int max_row;
    private int max_col;
    private MazeState start;
    private MazeState goal;
    private Maze maze;

    public SearchableMaze(Maze m){
        this.maze = m;
        MazeState s = new MazeState(m.getStartPosition());
        this.start  = s;
        MazeState g = new MazeState(m.getGoalPosition());
        this.goal = g;
        this.max_row = m.getM_rowSize();
        this.max_col = m.getM_colSize();
    }

    public boolean checkFrame(int rowToCheck, int colToCheck)
    {
        if((rowToCheck < 0) || (rowToCheck >= this.max_row) || (colToCheck < 0) || (colToCheck >= this.max_col))
            return false;
        return true;
    }

    public ArrayList<AState> getAllPossibleStates(AState state)
    {
        int r = state.getCurrState().getRowIndex();
        int c = state.getCurrState().getColumnIndex();
        boolean pos1 = (checkFrame(r-1,c) &&(this.maze.get_val(r-1,c) == 0));
        boolean pos2 = (checkFrame(r-1,c+1) &&(this.maze.get_val(r-1,c+1) == 0));//DIAGONAL
        boolean pos3 = (checkFrame(r,c+1) &&(this.maze.get_val(r,c+1) == 0));
        boolean pos4 = (checkFrame(r+1,c+1) &&(this.maze.get_val(r+1,c+1) == 0));//DIAGONAL
        boolean pos5 = (checkFrame(r+1,c) &&(this.maze.get_val(r+1,c) == 0));
        boolean pos6 = (checkFrame(r+1,c-1) &&(this.maze.get_val(r+1,c-1) == 0));//DIAGONAL
        boolean pos7 = (checkFrame(r,c-1) &&(this.maze.get_val(r,c-1) == 0));
        boolean pos8 = (checkFrame(r-1,c-1) &&(this.maze.get_val(r-1,c-1) == 0)); //DIAGONAL

        ArrayList<AState> arr = new ArrayList<AState>();
        MazeState helper; // = new MazeState();
        // DIAGONAL = 15
        // REGULAR = 10
        if(pos1){
            helper = new MazeState(r-1,c,10,state);
            arr.add(helper);
        }
        if(pos2 && (pos1 || pos3)) {//DIAGONAL
            helper = new MazeState(r-1,c+1,15,state);
            arr.add(helper);
        }
        if(pos3){
            helper = new MazeState(r,c+1,10,state);
            arr.add(helper);
        }
        if(pos4 && (pos3 || pos5)){//DIAGONAL
            helper = new MazeState(r+1,c+1,15,state);
            arr.add(helper);
        }
        if(pos5){
            helper = new MazeState(r+1,c,10,state);
            arr.add(helper);
        }
        if(pos6 && (pos5 || pos7)){//DIAGONAL

            helper = new MazeState(r+1,c-1,15,state);
            arr.add(helper);
        }
        if(pos7)
        {
            helper = new MazeState(r,c-1,10,state);
            arr.add(helper);
        }
        if(pos8 && (pos1 || pos7)){//DIAGONAL
            helper = new MazeState(r-1,c-1,15,state);
            arr.add(helper);
        }
        return  arr;
    }

    public AState getStartState()
    {
        return this.start;
    }

    public AState getGoalState()
    {
        return this.goal;
    }
}
