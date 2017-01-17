package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Starts the application by loading the very first scene
     * which is the login window
     * @param primaryStage is the stage object.
     * @throws Exception to handle possible runtime errors.
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(Constants.LOGIN_WINDOW_FXML));
        primaryStage.setTitle(Constants.HEADER_PROGRAM_NAME);
        primaryStage.setScene(new Scene(root, 380, 430));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Config.connect();
        launch(args);
    }
}
