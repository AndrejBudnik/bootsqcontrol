package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;

import static sample.Constants.*;

public class LoginView {

    @FXML
    public Button windowOpener;
    public TextField passField;
    public TextField usernameField;

    /**
     * OnAction method for windowOpener button.
     * Checks the login-password combination,
     * opens the main window if its correct,
     * pops up an error alert otherwise.
     */
    @FXML
    public void onClickWindowOpener() {
        sample.Login login = new sample.Login();
        Stage stage = (Stage) windowOpener.getScene().getWindow();
        if(login.checkPassword(usernameField.getText() ,passField.getText())) {
//        if (login.checkPassword("qwe", "qwe123")) {
            stage.close();
            loadWindow();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ALERT_INCORRECT_LOGIN);
            alert.showAndWait();
        }
    }


    /**
     * Loads an *.fxml file and creates a new window for it.
     */
    private void loadWindow(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(MAIN_VIEW_FILEPATH));

            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root1);
            scene.getStylesheets().add("file:///" + new File(CSS_FILEPATH).getAbsolutePath().replace("\\", "/"));
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}


