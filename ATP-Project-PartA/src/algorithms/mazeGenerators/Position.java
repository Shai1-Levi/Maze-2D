package algorithms.mazeGenerators;
import java.util.Random;

/**
 * This class represents a place in Maze that is represented by two Integers
 */

public class Position {
    private int rowPosition, colPosition;

    public Position(int rowPosition, int colPosition) {
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
    }

    public boolean equals(Position other)
    {
        if((this.rowPosition == other.getRowIndex()) && this.colPosition == other.getColumnIndex())
        {
            return true;
        }
        return false;
    }

    /**
     * @param o Object
     *          This class get Object type because in run time we dont know which type the func will get
     *          that contain Position type.
     * @return
     */
    public boolean equals(Object o)
    {
        Position p = (Position) o;
        if(this.equals(p)) {
            return true;
        }
        return false;
    }

    public int getRowIndex()
    {
        return this.rowPosition;
    }

    public int getColumnIndex()
    {
        return this.colPosition;
    }

    public String toString()

    {
        String out = "{" + this.getRowIndex() + "," + this.getColumnIndex() + "}";
        return out;
    }

}
