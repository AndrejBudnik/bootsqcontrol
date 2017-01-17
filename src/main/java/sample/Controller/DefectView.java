package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.DefectEntity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static sample.Constants.*;

/**
 * Created by kotsy on 22.12.2016.
 */
public class DefectView {
    @FXML
    public Button cancelButton;
    @FXML
    public Button confirmButton;
    @FXML
    public Button exitButton;
    @FXML
    private TextArea defectDescription;

    private boolean isDataOk;

    /**
     * Initializes Defect adding window, where defectDecription stands for the TextArea
     * that contains defect description data
     */
    @SuppressWarnings(UNCHECKED)
    @FXML
    public void initialize(){
        fieldListener(defectDescription, REGEX_DESCRIPTION_MATCH);
    }

    /**
     * OnAction method for confirmButton.
     * Parses all the data from the window,
     * inserts created entity into database if the data is correct.
     */
    @SuppressWarnings(UNCHECKED)
    @FXML
    public void addEntity(){
        if(isDataOk) {
                try {
                    DefectEntity entity = new DefectEntity();
                    entity.setType(defectDescription.getText());

                    entity.insert(entity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ALERT_INCORRECT_DATA);
            alert.setContentText(ALERT_CONTEXT);
            alert.showAndWait();
        }
    }

    /**
     * Parses the data from a TextArea immediately after the one loses focus.
     * Checks input with a regex pattern and highlights the field if the data is incorrect.
     * @param area - textArea to listen
     * @param pattern - regex pattern to check
     */
    private void fieldListener(TextArea area, String pattern){
        area.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!area.isFocused()){
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(area.getText());
                if(!m.matches()){
                    area.setStyle(STYLE_BG_COLOR);
                    this.isDataOk = false;
                }
                else {
                    this.isDataOk = true;
                }
            } else {
                area.setStyle(null);
            }
        });
    }

    /**
     * Clears all the input fields and disables highlighting.
     */
    private void clearAllFields(){
        defectDescription.clear();
        defectDescription.setStyle(null);
    }

    /**
     * OnAction method for cancelButton.
     */
    @FXML
    public void cancel(){
        clearAllFields();
    }

    /**
     * OnAction method for exitButton.
     * Closes the stage and exits the window.
     */
    @FXML
    public void exit(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }


}
