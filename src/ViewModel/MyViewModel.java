package ViewModel;

import Model.IModel;
import View.MazeDisplayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * this Class responsible to connect between MyView and Model(logic level)
 * this Class is responsible for notify the relevant logic level that need to handle the event
 */
public class MyViewModel extends Observable implements Observer {

    private IModel imodel;

    private int characterPositionRowIndex;
    private int characterPositionColumnIndex;

    private ArrayList<String> solution;

    public StringProperty characterRow = new SimpleStringProperty("");
    public StringProperty characterColumn = new SimpleStringProperty("");

    public MyViewModel(IModel iModelARG){
        this.imodel = iModelARG;
    }

    public void generateMaze(int width, int height){
        solution = null;
        imodel.generateMaze(width, height);
    }

    public void solve(){
        imodel.solveMaze();
    }

    /**
     * this method notify Observable object/variables
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o==this.imodel){
            characterPositionRowIndex = this.imodel.getCharacterPositionRow();
            characterRow.set(characterPositionRowIndex + "");
            characterPositionColumnIndex = this.imodel.getCharacterPositionColumn();
            characterColumn.set(characterPositionColumnIndex + "");
            solution = this.imodel.getMazeSolution();
            setChanged();
            notifyObservers();
        }
    }

    public int[][] getMaze(){
        return this.imodel.getMaze();
    }

    public boolean reachTheGoal(){
       return this.imodel.isReachTheChesse();
    }

    public ArrayList<String> getSolution() {
        return this.imodel.getMazeSolution();
    }

    public void moveCharacter(KeyCode Event){
        this.imodel.moveCharacter(Event);
    }

    public void StopServers(){
        this.imodel.stopServers();
    }

    public void save(File file){
        this.imodel.save(file);
    }

    public void load(File file){
        this.imodel.load(file);
    }

    public int getCharacterPositionRow() {
        return characterPositionRowIndex;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumnIndex;
    }

    public int getEndPositionRow(){
        return this.imodel.getPositionEndMazeRow();
    }

    public int getEndPositionColumn() {
        return this.imodel.getPositionEndMazeCol();
    }

}
