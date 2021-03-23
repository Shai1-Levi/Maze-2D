package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {

    private int[][] maze;
    private ArrayList<String> solution;

    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    private StringProperty ImageFileEndCharacter = new SimpleStringProperty();

    /*public StringProperty imageFileCongratulationImageProperty() {
        return ImageFileCongratulationImage;
    }*/

    private StringProperty ImageFileCongratulationImage = new SimpleStringProperty();

    private boolean firstShow  = true;
    private int characterStratPositionRow = 1;
    private int characterStartPositionColumn = 1;

    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;

    private int positionEndMazeRow = 1;
    private int positionEndMazeCol = 1;

    private boolean reachTheChesse = false;


    public MazeDisplayer(){
        // Redraw canvas when size changes but not after the player reach the goal
        if(reachTheChesse != true) {
            widthProperty().addListener(evt -> draw());
            heightProperty().addListener(evt -> draw());
        }
    }

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }

    public String getImageFileEndCharacter() {
        return ImageFileEndCharacter.get();
    }

    public void setImageFileEndCharacter(String imageFileEndCharacter) {
        this.ImageFileEndCharacter.set(imageFileEndCharacter);
    }

    public String getImageFileCongratulationImage() {
        return ImageFileCongratulationImage.get();
    }

    public void setImageFileCongratulationImage(String imageFileCongratulationImage) {
        this.ImageFileCongratulationImage.set(imageFileCongratulationImage);
    }

    //endregion

    public void setMaze(int[][] maze, ArrayList<String> solutionARG) {
        this.maze = maze;
        this.solution = solutionARG;
        draw();
    }

    public void draw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int row = maze.length;
            int col = maze[0].length;
            double cellHeight = canvasHeight / row;
            double cellWidth = canvasWidth / col;

            try {
                GraphicsContext graphicsContext2D = getGraphicsContext2D();
                graphicsContext2D.clearRect(0, 0, canvasWidth, canvasHeight);
                if (this.reachTheChesse == true) {
                    //Image congratulationImage = new Image(new FileInputStream(ImageFileCongratulationImage.get()));
                    //graphicsContext2D.drawImage(congratulationImage, positionEndMazeCol * cellHeight, positionEndMazeRow * cellWidth, cellWidth, cellHeight);
                    //reachTheChesse = false;
                    //this.maze = null;
                    //Image congratulationImage = new Image(new FileInputStream(ImageFileCongratulationImage.get()));
                    //graphicsContext2D.drawImage(congratulationImage, positionEndMazeCol * cellWidth, positionEndMazeRow * cellHeight, cellWidth, cellHeight);
                }else{
                    Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));

                    graphicsContext2D.setFill(Color.RED);
                    for (int r = 0; r < row; r++) {
                        for (int c = 0; c < col; c++) {
                            if (this.maze[r][c] == 1) {
                                //graphicsContext2D.fillRect(r * cellHeight, c * cellWidth, cellHeight, cellWidth);
                                graphicsContext2D.drawImage(wallImage, c * cellWidth, r * cellHeight, cellWidth, cellHeight);
                            }

                        }
                    }

                    Image characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));
                    graphicsContext2D.drawImage(characterImage, characterPositionColumn * cellWidth, characterPositionRow * cellHeight, cellWidth, cellHeight);

                    Image endImage = new Image(new FileInputStream(ImageFileEndCharacter.get()));
                    graphicsContext2D.drawImage(endImage, positionEndMazeCol * cellWidth, positionEndMazeRow * cellHeight, cellWidth, cellHeight);
                    String State;
                    if (this.solution != null) {
                        for (int r = 0; r < row; r++) {
                            for (int c = 0; c < col; c++) {
                                State = String.format("{%d,%d}", r, c);
                                if ((c != characterPositionColumn) || (r != characterPositionRow)) {
                                    if (this.solution.contains(State)) {
                                        graphicsContext2D.drawImage(endImage, c * cellWidth, r * cellHeight, cellWidth, cellHeight);
                                    }
                                }
                                else if((characterStratPositionRow != r)||(characterStartPositionColumn != c)) {
                                    graphicsContext2D.setFill(Color.BLUE);
                                    graphicsContext2D.fillRect(characterStartPositionColumn * cellWidth, characterStratPositionRow * cellHeight, cellWidth, cellHeight);
                                }
                            }
                        }
                    }
                    if((characterStratPositionRow != characterPositionRow)||(characterStartPositionColumn != characterPositionColumn)){
                        graphicsContext2D.setFill(Color.BLUE);
                        graphicsContext2D.fillRect(characterStartPositionColumn * cellWidth, characterStratPositionRow * cellHeight, cellWidth, cellHeight);
                    }
                }
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(String.format("Image doesn't exist: %s", e.getMessage()));
                alert.show();
            }
        }
    }

    /**
     * This method implements the zoom in, zoom out events
     *
     */
    public void Zoom(){
        setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
                double zoom_fac = 1.05;
                double delta_y = scrollEvent.getDeltaY();
                if(delta_y < 0) {
                    zoom_fac = 2.0 - zoom_fac;
                    setScaleX(getScaleX()*zoom_fac);
                    setScaleY(getScaleY()*zoom_fac);
                }
                else
                {
                    zoom_fac = 1.05;
                    setScaleX(getScaleX()*zoom_fac);
                    setScaleY(getScaleY()*zoom_fac);
                }
                scrollEvent.consume();
            }
        });
    }

    /**
     * This method set boolean that represent if the player reach the goal or not
     * @param gameStatus
     */
    public void drawFinishImage(Boolean gameStatus)
    {
        reachTheChesse = gameStatus;
    }

    public void setEndPosition(int endPositionRow, int endPositionCol) {
        this.positionEndMazeRow = endPositionRow;
        this.positionEndMazeCol = endPositionCol;
    }

    /**
     * This method update the position of the character at the grid and set the entrance position for mark the entrance on the grid
     * after the player move the character
     * @param characterRowNewPosition
     * @param characterColumnNewPosition
     * @param FirstShow
     */
    public void setCharacterPosition(int characterRowNewPosition, int characterColumnNewPosition, boolean FirstShow) {
        if(FirstShow)
        {
            characterStratPositionRow = characterRowNewPosition;
            characterStartPositionColumn = characterColumnNewPosition;
        }
        this.characterPositionRow = characterRowNewPosition;
        this.characterPositionColumn = characterColumnNewPosition;
    }

    /**
     * Thus method are required for resize the grid according the stage window
     * @return
     */
    @Override
    public boolean isResizable(){
        return true;
    }
    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

}
