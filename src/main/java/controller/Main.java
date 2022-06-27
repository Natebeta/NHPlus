package controller;

import datastorage.ConnectionBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jobrunner.QuartzController;
import utils.PasswordUtils;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private static Stage stage;

    /**
     * Sets the primary stage / main window and starts the quartz job
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindow();
        QuartzController.startTreatmentDeleter();
    }

    /**
     * Loads the main window and handles the connection to the database
     */
    public void mainWindow() {
        try {
            stage = primaryStage;
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/LoginView.fxml"));
            BorderPane pane = loader.load();

            Scene scene = new Scene(pane);
            this.primaryStage.setTitle("NHPlus");
            this.primaryStage.setScene(scene);
            this.primaryStage.setResizable(false);
            this.primaryStage.show();

            this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    ConnectionBuilder.closeConnection();
                    Platform.exit();
                    System.exit(0);
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Switches the current stage to a new scene by fxml view
     * @param fxml the view
     */
    public void changeView(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        BorderPane borderPane = fxmlLoader.load();
        stage.getScene().setRoot(borderPane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}