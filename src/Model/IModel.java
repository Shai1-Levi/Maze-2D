package Model;

import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.ArrayList;

/**
 * This method are required to implement at the class to who extend this interface.
 * the implementation behind them is logic part of the game.
 */

public interface IModel {
    void generateMaze(int width, int height);
    void solveMaze();
    void moveCharacter(KeyCode movement);
    int[][] getMaze();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    int getPositionEndMazeRow();
    int getPositionEndMazeCol();
    boolean isReachTheChesse();
    ArrayList<String> getMazeSolution();
    void stopServers();
    void save(File file);
    void load(File file);
}
