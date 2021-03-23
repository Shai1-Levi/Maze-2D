package View;

import Server.Server.Configurations;
import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class MyViewController implements Observer, IView, Initializable {

    //*********************** Start of music region *********************************

    MediaPlayer mediaPlayerBackground;
    MediaPlayer mediaPlayerWin = null;

    public void Music(){

        String pathToMusic = "resources/music1.mp3";
        Media media = new Media(Paths.get(pathToMusic).toUri().toString());
        mediaPlayerBackground = new MediaPlayer(media);
        mediaPlayerBackground.play();
        //***************** loop (repeat) the music  ******************
        mediaPlayerBackground.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayerBackground.seek(Duration.ZERO);
            }
        });
        //*************** end of loop (repeat) the music  **************
    }

    public void stopMusic()
    {
        mediaPlayerBackground.stop();
    }

    private void startFinishMusic(){
        String pathToMusic = "resources/triumph.mp3";
        Media media = new Media(Paths.get(pathToMusic).toUri().toString());
        mediaPlayerWin = new MediaPlayer(media);
        mediaPlayerWin.play();
    }
    //*********************** End of music region *********************************

    @FXML
    public Label Maze_rows;
    @FXML
    public Label Maze_columns;
    @FXML
    public Label Player_row;
    @FXML
    public Label Player_column;

    @FXML
    public TextField textField_mazeRows;

    @FXML
    public TextField textField_mazeColumns;

    @FXML
    public javafx.scene.control.Button btn_generateMaze;

    @FXML
    public javafx.scene.control.Button btn_solveMaze;

    @FXML
    public MazeDisplayer mazeDisplayer;

    @FXML
    public VBox Vbox;

    @FXML
    public Label lbl_characterRow;

    @FXML
    public Label lbl_characterColumn;

    @FXML
    public BorderPane borderPane;

    public GridPane gridPane = new GridPane();

    private static Stage mainStage;
    private MyViewModel myViewModel;

    private boolean EnterFlag = false;
    private boolean firstShow = true;

    //************************** start region configurationProperties ****************
    private StringProperty configurationProperties = new SimpleStringProperty();


    public StringProperty configurationPropertiesProperty() {
        return configurationProperties;
    }
    public void setConfigurationProperties(String configurationProperties) {
        this.configurationProperties.set(configurationProperties);}

    public String getConfigurationProperties() {return configurationProperties.get();}

    //*************************** end region configurationProperties ************************

    //<editor-fold desc="Data Binding for Character Row & Column Positions">
    public StringProperty characterRow = new SimpleStringProperty();
    public StringProperty characterColumn = new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Set Binding for Properties
        lbl_characterRow.textProperty().bind(characterRow);
        lbl_characterColumn.textProperty().bind(characterColumn);

    }
    //</editor-fold>

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public void setViewModel(MyViewModel myViewModel) {
        btn_solveMaze.setDisable(true);
        this.myViewModel = myViewModel;
        bindProperties(myViewModel);
    }

    private void bindProperties(MyViewModel myViewModel) {
        lbl_characterRow.textProperty().bind(myViewModel.characterRow);
        lbl_characterColumn.textProperty().bind(myViewModel.characterColumn);
    }

    /*public void setResizeEvent(Scene scene) {
        long width = 0;
        long height = 0;

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                //Vbox.prefWidthProperty().bind(borderPane.widthProperty());
                //Vbox.prefHeight(newSceneWidth.intValue());
                //btn_generateMaze.prefWidthProperty().bind(Vbox.widthProperty().multiply(0.2));
                //btn_generateMaze.prefHeightProperty().bind(Vbox.heightProperty().multiply(0.02));
                //.prefWidth(newSceneWidth.intValue() * 0.02);
                //btn_solveMaze.prefWidth(newSceneWidth.intValue() * 0.02);
                /*Vbox.prefWidthProperty().bind(observableValue);
                textField_mazeRows.prefHeight(newSceneWidth.intValue());//.bind(Vbox.widthProperty().multiply(-0.3));
                textField_mazeRows.setAlignment(Pos.CENTER_LEFT);
                Vbox.prefWidthProperty().bind(borderPane.widthProperty());
                btn_generateMaze.prefWidthProperty().bind(Vbox.widthProperty().multiply(0.2));
                btn_generateMaze.prefHeightProperty().bind(Vbox.heightProperty().multiply(0.02));

                btn_solveMaze.prefWidthProperty().bind(Vbox.widthProperty().multiply(0.2));
                btn_solveMaze.prefHeightProperty().bind(Vbox.heightProperty().multiply(0.02));
                Player_column.prefWidthProperty().bind(Vbox.widthProperty().multiply(0.2));
                Player_row.prefWidthProperty().bind(Vbox.widthProperty().multiply(0.2));
                Maze_rows.prefWidthProperty().bind(Vbox.widthProperty().multiply(0.2));
                Maze_columns.prefWidthProperty().bind(Vbox.widthProperty().multiply(0.2));
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                Vbox.prefHeightProperty().bind(observableValue);
                textField_mazeRows.prefHeight(newSceneHeight.intValue());//.bind(Vbox.widthProperty().multiply(-0.3));
                textField_mazeRows.setAlignment(Pos.CENTER_LEFT);
            }
        });
    }*/

    /**
     * this method is connected to generate maze button at the MyView.fxml
     * and this method is responsible to generate maze once at the start of the game
     */
    public void generateMaze(){
        if(!btn_generateMaze.isCancelButton()) {

            // force the field to be numeric only
            try {
                int rows = Integer.parseInt(textField_mazeRows.getText());
                int cols = Integer.parseInt(textField_mazeColumns.getText());
                if((rows <3)||(cols<3))
                {
                    throw new NegativeArraySizeException();
                }
                firstShow = true;
                btn_generateMaze.setDisable(false);
                btn_solveMaze.setDisable(false);
                myViewModel.generateMaze(rows, cols);
            }
            catch (NumberFormatException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Digits only please");
                alert.showAndWait();
            }
            catch (NegativeArraySizeException neg){
                Alert alert = new Alert(Alert.AlertType.ERROR, "insert Positive numbers only and bigger then 2 please!");
                alert.showAndWait();
            }
        }
    }

    /**
     * this method is connected to solve maze button at the MyView.fxml
     * and notify myViewModel that an event occur
     * @param actionEvent
     */
    public void solveMaze(ActionEvent actionEvent) {
        myViewModel.solve();
    }

    /**
     * handle mouse clicked
     * @param mouseEvent
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    /**
     * this method handle the key pressed and classified the events
     * @param keyEvent
     */
    public void KeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode().getName().equals("Ctrl"))
        {
            if(btn_generateMaze.isDisable()){
                mazeDisplayer.Zoom();
                keyEvent.consume();
            }
        }
        else if(keyEvent.getCode().getName().equals("Enter"))
        {
            if(EnterFlag == false) {
                generateMaze();
                EnterFlag = true;
            }
        }
        else if(keyEvent.getCode().getName().equals("Esc")){
            Exit();
        }
        else {
            myViewModel.moveCharacter(keyEvent.getCode());
            keyEvent.consume();
        }
    }

    /**
     * this method override implements the Observers objects/ variables
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == this.myViewModel){
            if(this.myViewModel.reachTheGoal())
            {
                finish();
                btn_solveMaze.setDisable(true);
            }
            displayMaze(this.myViewModel.getMaze(), this.myViewModel.getSolution());
            btn_generateMaze.setDisable(true);
        }
    }

    /**
     * this method is implements of the required method at the interface
     * and handle the mazeDisplayer canvas
     * @param maze
     * @param solution
     */
    @Override
    public void displayMaze(int[][] maze, ArrayList<String> solution) {

        int characterPositionRow = this.myViewModel.getCharacterPositionRow();
        int characterPositionColumn = this.myViewModel.getCharacterPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn,firstShow);
        firstShow = false;
        mazeDisplayer.setEndPosition(this.myViewModel.getEndPositionRow(), this.myViewModel.getEndPositionColumn());
        this.characterRow.set(characterPositionRow + "");
        this.characterColumn.set(characterPositionColumn + "");
        mazeDisplayer.setMaze(maze ,solution);
    }

    /**
     * This method handle the event of the character is reaching the goal at the grid
     */
    public void finish(){
        stopMusic();
        mazeDisplayer.drawFinishImage(true);
        if(mediaPlayerBackground.isAutoPlay())
        {
            mediaPlayerBackground.stop();
        }
        startFinishMusic();
    }


     //********************************* this region is for the menu of Maze Game ************************************


    public void NewGame(){
        showAlertNewGame();
        mazeDisplayer.drawFinishImage(false);
        generateMaze();

        if((this.mediaPlayerWin != null) &&(this.mediaPlayerWin.getStatus() != MediaPlayer.Status.STOPPED))
        {
            mediaPlayerWin.stop();
        }

        if((this.mediaPlayerBackground != null) &&(this.mediaPlayerBackground.getStatus() == MediaPlayer.Status.STOPPED))
        {
            Music();
        }

    }

    private void showAlertNewGame() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to save the game?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            SaveGame();
        }
        else if (result.get() == ButtonType.CLOSE) {
        }
    }

    public void SaveGame(){
        if(btn_generateMaze.isDisable()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text", "*.txt"));
            File file = fileChooser.showSaveDialog(mainStage);
            if (file != null) {
                myViewModel.save(file);
                showAlertSaveGame(true);
            }
        }
        else {
            showAlertSaveGame(false);
    }
    }

    private void showAlertSaveGame(boolean mazeWasCreated) {
        if(mazeWasCreated) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Maze is Saved");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Generate a Mouse Maze please!");
            alert.showAndWait();
        }
    }

    public void LoadGame() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(mainStage);

        if(file != null)
        {
            btn_solveMaze.setDisable(false);
            firstShow = true;
            myViewModel.load(file);
        }
    }

    /**
     * this method close the music and servers and exit the game
     */

    public void Exit(){
        this.myViewModel.StopServers();
        if((this.mediaPlayerWin != null) &&(this.mediaPlayerWin.getStatus() != MediaPlayer.Status.STOPPED))
        {
            mediaPlayerWin.stop();
        }

        if((this.mediaPlayerBackground != null) &&(this.mediaPlayerBackground.getStatus() == MediaPlayer.Status.STOPPED))
        {
            stopMusic();
        }
        System.exit(0);
    }

    /**
     * This method show the Configuration Properties that are inused and can change them to other Configuration Properties
     *
     */

    public void Properties(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Configuration Properties");

            Button submitButton = new Button("Submit");

            javafx.scene.control.ComboBox<String> comboBoxGenerate = new javafx.scene.control.ComboBox<>();
            comboBoxGenerate.setPromptText("Please Choose Algorithm to generate Mouse Maze");
            comboBoxGenerate.getItems().addAll("Prime Maze", "Simple Maze", "Empty Maze");

            javafx.scene.control.ComboBox<String> comboBoxSolve = new javafx.scene.control.ComboBox<>();
            comboBoxSolve.setPromptText("Please Choose Algorithm to solve Mouse Maze");
            comboBoxSolve.getItems().addAll("Depth first search", "Breadth first search", "Best first search");

            Label label = new Label("Add number of threads: ");
            TextField ThreadsTf = new TextField();
            ThreadsTf.setPromptText("Numbers only");

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20, 20, 20, 20));
            Configurations config = new Configurations();
            Label MazeAlgo = new Label();
            Label MazeSolveAlgo= new Label();
            Label MazeThreadNum= new Label();
            try {
                MazeAlgo = new Label("Algorithm generated Mouse Maze: " + config.getiMazeGenerator());
            }catch (NullPointerException Ne){}
            try {
                MazeSolveAlgo = new Label("Algorithm generated Mouse Maze: " + config.getIsa());
            }catch (NullPointerException Ne){}
            try {
                MazeThreadNum = new Label("Number of threads that are run in Mouse Maze: " + config.getNumberOfThreadsInString());
            }
            catch (NullPointerException Ne) {}
            layout.getChildren().addAll(comboBoxGenerate,comboBoxSolve,label,ThreadsTf, submitButton, MazeAlgo, MazeSolveAlgo,MazeThreadNum);
            Scene scene = new Scene(layout, 400, 350);

            stage.setScene(scene);
            submitButton.setOnAction(eventHandleProperties -> handleProperties(comboBoxGenerate.getValue(), comboBoxSolve.getValue(),ThreadsTf.getText(), stage, true));
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is handle at the switching between Configuration Properties by the user
     * @param generate
     * @param solve
     * @param numOfThreads
     * @param stage
     * @param submit
     */

    private void handleProperties(String generate, String solve, String numOfThreads, Stage stage, boolean submit){
        try{
            Integer.parseInt(numOfThreads);
            if(Integer.parseInt(numOfThreads) <= 0)
            {
                throw new NumberFormatException();
            }
            if((generate == null)||(solve == null)||(numOfThreads == null))
            {
                throw new NumberFormatException();
            }
            if(submit)
            {
                stage.close();
            }
            Configurations configurations = new Configurations();
            configurations.setiMazeGenerator(generate);
            configurations.setIsa(solve);
            configurations.setNumOfThreads(numOfThreads);
            generateMaze();
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Digits only and positive numbers please at the filed Number of threads and fill all the properties");
            alert.showAndWait();
            stage.close();
            Properties();
        }

    }

    public void ThreeD(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "coming soon...");
        alert.showAndWait();
    }

    public void Rules(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Rules of the game");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Rules.fxml").openStream());
            Scene scene = new Scene(root, 500, 180);
            scene.getStylesheets().add(getClass().getResource("Rules.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {

        }
    }

    public void AboutDevelopers(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("About Developer");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("AboutDevelopers.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e){}
    }

    public void AboutTheGame(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Information about Mouse Maze");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("AboutTheGame.fxml").openStream());
            Scene scene = new Scene(root, 365, 150);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e){}
    }
}
