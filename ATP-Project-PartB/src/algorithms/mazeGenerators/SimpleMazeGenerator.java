package algorithms.mazeGenerators;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * @Class Simple Maze represents a maze with at least one solution.
 *
 */

public class SimpleMazeGenerator extends AMazeGenerator {

    List<Position> root;
    Position goal;
    Position start;
    Random ran;
    Maze m;


    public SimpleMazeGenerator(){
        this.m = null;
        this.root = null;
        this.goal = null;
        this.start=null;
        this.ran = new Random();
    }

    public SimpleMazeGenerator(int row, int col){
        this.m = null;
        this.root = null;
        this.goal = null;
        this.start=null;
        this.ran = new Random();
    }

    public Maze generate(int row, int col){
        this.m = Maze.MazeGenerator(row, col);
        if(row == 0 || col == 0)
        {
            return this.m;
        }
        for(int r = 0; r < m.max_rowSize; r++)
        {
            for(int c = 0; c < m.max_colSize; c++)
            {
                //if((r != m.getStartPosition().getRowIndex()) || (c != m.getStartPosition().getColumnIndex())) {

                    this.m.set_val(r,c, ran.nextInt(2));
                //}
            }
        }
        /*
         * every cell gets random number 0 or 1 that represents wall or an empty cell.
         */


        this.start= m.getStartPosition();
        this.goal=m.getGoalPosition();

        this.m.make_zero(this.start);
        this.m.make_zero(this.goal);
        this.root= new ArrayList<Position>();

        ran= new Random();
        int row_dif;
        int col_dif;
        int temp_row= this.start.getRowIndex();
        int temp_col= this.start.getColumnIndex();
        int i;

        while(!(temp_row==this.goal.getRowIndex() && temp_col==this.goal.getColumnIndex())){
            row_dif= this.goal.getRowIndex()- temp_row;
            col_dif=this.goal.getColumnIndex() -  temp_col;
            this.m.set_val(temp_row, temp_col,0);

            i = ran.nextInt(2);

            if (i==0){
                if(row_dif<0){
                    temp_row--;
                }
                if(row_dif>0){
                    temp_row++;
                }
            }
            if(i==1){
                if(col_dif<0){
                    temp_col--;
                }
                if(col_dif>0){
                    temp_col++;
                }
            }
        }
        return m;

        /*
         * the while loop starts with temp_row and temp_col which are equal to the start Position of row and column.
         * in each iteration of loop we make the Position of temp_row, temp_col zero meaning empty, and randomly changing row of column
         * to be equal to goal's Position.
         */
    }
}

