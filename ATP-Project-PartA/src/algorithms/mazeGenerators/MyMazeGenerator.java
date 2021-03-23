package algorithms.mazeGenerators;

import java.util.HashSet;
import java.util.Random;
import java.util.*;

/**
 * MyMazeGenerator implemented Randomly Prim's algorithm that create Maze with solution
 * and contain a Maze.
 */

public class MyMazeGenerator extends AMazeGenerator{

    private Maze grid;
    private ArrayList<Position> List_marked_point = null;
    private Position GoalPosition = null;
    Random ran = null;


    public MyMazeGenerator() {
        this.grid = null;
        this.List_marked_point = null;
        this.GoalPosition = null;
        this.ran = new Random();
    }

    public MyMazeGenerator(int row, int col) {
        this.ran = new Random();
        this.List_marked_point = null;
        this.GoalPosition = null;
    }

    /**
     *
     * @param row,column for initialize the grid of maze
     * @return Maze that implement by prim
     */
    @Override
    public Maze generate(int row, int col) {
        this.grid = Maze.MazeGenerator(row, col); // maze have a new empty  grid
        if(row == 0 || col == 0)
        {
            return this.grid;
        }

        for(int r = 0; r < grid.max_rowSize; r++)
        {
            for(int c = 0; c < grid.max_colSize; c++)
            {
                grid.set_val(r,c,1);
            }
        }
        this.GoalPosition = this.grid.getGoalPosition();
        grid.set_val(this.GoalPosition.getRowIndex(),this.GoalPosition.getColumnIndex(),0);
        Prim();
        return grid;
    }

    /**
     * @param Pcell,val
     * @return List of neighbors that are valid in the borders of the maze
     */

    private List<Position> neighbors(Position Pcell, int val){
        ArrayList<Position> arr = new ArrayList<Position>();
        Position vaildCell = null;

        if((this.grid.is_valid(Pcell.getRowIndex(),Pcell.getColumnIndex()+1)))
        {
            vaildCell = new Position(Pcell.getRowIndex(),Pcell.getColumnIndex()+1);
            arr.add(vaildCell);
        }

        if((this.grid.is_valid(Pcell.getRowIndex(),Pcell.getColumnIndex()-1)))
        {
            vaildCell = new Position(Pcell.getRowIndex(),Pcell.getColumnIndex()-1);
            arr.add(vaildCell);

        }

        if ((this.grid.is_valid(Pcell.getRowIndex()-1,Pcell.getColumnIndex())))
        {
            vaildCell = new Position(Pcell.getRowIndex()-1,Pcell.getColumnIndex());
            arr.add(vaildCell);

        }

        if((this.grid.is_valid(Pcell.getRowIndex()+1,Pcell.getColumnIndex())))
        {
            vaildCell = new Position(Pcell.getRowIndex()+1,Pcell.getColumnIndex());
            arr.add(vaildCell);
        }
        if(arr.size()>0){
            return arr;
        }
        else
        {
            return null;
        }
    }

    /**
     * @param P
     * make the Position be an empty cell.
     */

    private void mark(Position P)
    {
        if(P != null)
        {
                grid.set_val(P.getRowIndex(),P.getColumnIndex(),0);
        }
    }

    /**
     * @param Pcell
     * This function is checking if the Pcell is not closing a circle
     * @return
     */
    private int check(Position Pcell)
    {
        int counter = 0;
        if((this.grid.is_valid(Pcell.getRowIndex(),Pcell.getColumnIndex()+1)) && (this.grid.get_val(Pcell.getRowIndex(),Pcell.getColumnIndex()+1) == 0))
        {
            counter++;
        }

        if((this.grid.is_valid(Pcell.getRowIndex(),Pcell.getColumnIndex()-1)) && (this.grid.get_val(Pcell.getRowIndex(),Pcell.getColumnIndex()-1) == 0))
        {
            counter++;
        }

        if ((this.grid.is_valid(Pcell.getRowIndex()-1,Pcell.getColumnIndex()))   && (this.grid.get_val(Pcell.getRowIndex()-1,Pcell.getColumnIndex()) == 0))
        {
            counter++;
        }

        if((this.grid.is_valid(Pcell.getRowIndex()+1,Pcell.getColumnIndex())) && (this.grid.get_val(Pcell.getRowIndex()+1,Pcell.getColumnIndex()) == 0))
        {
            counter++;
        }
        return counter;

    }

    /**
     * Ranmdom Prim algorithm create a random paths, one of them will be the solution path of the maze
     * the paths is represented by empty cells (cell value is 0)
     */
    private void Prim()
    {
        HashSet<String> hashPossibleNeighbors = new HashSet<String>();
        List_marked_point = new ArrayList<Position>();
        List<Position> Possible_neighbors;
        List<Position> Possible_neighbors_temp;
        Position temp, selected;

        List_marked_point.add(this.GoalPosition);
        temp = List_marked_point.remove(0);
        Possible_neighbors = neighbors(temp,1 /*search for neighbor with 1 */);
        for( Position p : neighbors(temp,1 /*search for neighbor with 1 */))
        {
            hashPossibleNeighbors.add(p.toString());
        }

        /**
         * in each iteration we add the positions neighbors to hashPossibleNeighbors.
         * at the while below we check the neighbors of the neighbors of List_marked_point
         * that is a group of all possible paths in maze by prim algorithm
         */

        while(Possible_neighbors.size() > 0)
        {
            int index = this.ran.nextInt(Possible_neighbors.size());
            selected = Possible_neighbors.get(index);

            if(check(selected) < 2) {
                mark(selected);
                List_marked_point.add(selected);
                Possible_neighbors_temp = neighbors(selected,1 /*neighbor that are not a passage*/);

                for(int i =0 ; i< Possible_neighbors_temp.size(); i++)
                {
                    temp = Possible_neighbors_temp.get(i);
                    if(!(hashPossibleNeighbors.contains(temp.toString())))
                    {
                        Possible_neighbors.add(temp);
                        hashPossibleNeighbors.add(temp.toString());
                    }
                }
            }
            Possible_neighbors.remove(selected);
            hashPossibleNeighbors.remove(selected.toString());
        }
    }
}
