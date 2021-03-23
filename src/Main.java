import Model.MyModel;
import Server.Server;
import View.MyViewController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.nio.file.Paths;
import java.util.Optional;

public class Main extends Application {

    MyViewController myViewController;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Stage window = primaryStage;
        MyModel myModel = new MyModel();
        myModel.startServers(); // raise up the servers

        MyViewModel myViewModel = new MyViewModel(myModel);
        myModel.addObserver(myViewModel);

        primaryStage.setTitle("Mouse Maze");

        //primaryStage
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/View/MyView.fxml").openStream());
        Scene scene = new Scene(root, 800, 650);
        scene.getStylesheets().add(getClass().getResource("/View/MainStyle.css").toExternalForm());
        primaryStage.setScene(scene);

        myViewController.setStage(primaryStage);
        //primaryStage.show();

        //--------------------
        MyViewController myViewController = fxmlLoader.getController();

        //myViewController.setResizeEvent(scene);

        myViewController.setViewModel(myViewModel);

        myViewModel.addObserver(myViewController);
        //--------------
        myViewController.Music();
        SetStageCloseEvent(primaryStage, myModel, myViewController);

        Label label1 = new Label("Welcome to Mouse Maze!");
        Button button1 = new Button("Start!");
        button1.setOnAction(e -> window.setScene(scene));

        VBox Vbox = new VBox();
        Vbox.getChildren().addAll(label1, button1);
        Vbox.setAlignment(Pos.CENTER);
        Scene LogInScene = new Scene(Vbox, 800, 650);
        LogInScene.getStylesheets().add(getClass().getResource("/View/LogIn.css").toExternalForm());
        primaryStage.setScene(LogInScene);

        window.show();

    }

    private void SetStageCloseEvent(Stage primaryStage, MyModel myModel, MyViewController myViewController) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    // Close program
                    myViewController.stopMusic();
                    myModel.stopServers();
                    System.exit(0);
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*
TODO
jar file
 */