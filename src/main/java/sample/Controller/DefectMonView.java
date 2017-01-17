package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import static sample.Constants.*;
import sample.Model.DefectEntity;
import sample.Model.DefectMonEntity;
import sample.Model.PlanEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kotsy on 22.12.2016.
 */
public class DefectMonView {
    @FXML
    public Button cancelButton;
    @FXML
    public Button confirmButton;
    @FXML
    public Button exitButton;

    @FXML
    private DatePicker dateField;
    @FXML
    private TextField defectCountField;
    @FXML
    private ChoiceBox<PlanEntity> planField;
    @FXML
    private ChoiceBox<DefectEntity> defectTypeField;

    private boolean isDataOk;

    /**
     * Initializes Defect Monitor adding window, where planField, defectTypeField stand for the dropdown menu,
     * defectCountField is a TextFeild item,
     */
    @SuppressWarnings(UNCHECKED)
    @FXML
    public void initialize(){

        planField.setTooltip(new Tooltip(USER_CHOOSE_PLAN));
        planField.setItems(new PlanEntity().select());
        planField.getSelectionModel().selectFirst();

        defectTypeField.setTooltip(new Tooltip(USER_CHOOSE_DEFECT_TYPE));
        defectTypeField.setItems(new DefectEntity().select());
        defectTypeField.getSelectionModel().selectFirst();

        fieldListener(defectCountField, REGEX_COUNT_MATCH);

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
            PlanEntity plan = planField.getSelectionModel().getSelectedItem();
            System.out.println(plan);
            DefectEntity defect = defectTypeField.getSelectionModel().getSelectedItem();
            System.out.println(defect);
            LocalDate ld = dateField.getValue();
            Date date = Date.valueOf(ld);
            try {
                int defectCount = Integer.parseInt(defectCountField.getText());
                try {
                    DefectMonEntity entity = new DefectMonEntity();
                    entity.setDefectCount(defectCount);
                    entity.setPlan(plan);
                    entity.setDefectType(defect);
                    entity.setCheckDate(date);
                    System.out.println(entity.getPlan().getId());

                    entity.insert(entity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (NumberFormatException n) {
                n.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ALERT_INCORRECT_DATA);
            alert.setContentText(ALERT_CONTEXT);
            alert.showAndWait();
        }
    }

    /**
     * Parses the data from a TextField immediately after the one loses focus.
     * Checks input with a regex pattern and highlights the field if the data is incorrect.
     * @param field - textField to listen
     * @param pattern - regex pattern to check
     */
    private void fieldListener(TextField field, String pattern){
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!field.isFocused()){
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(field.getText());
                if(!m.matches()){
                    field.setStyle(STYLE_BG_COLOR);
                    this.isDataOk = false;
                }
                else {
                    this.isDataOk = true;
                }
            } else {
                field.setStyle(null);
            }
        });
    }

    /**
     * Clears all the input fields and disables highlighting.
     */
    private void clearAllFields(){
        defectCountField.clear();
        defectCountField.setStyle(null);
        defectTypeField.getSelectionModel().selectFirst();
        planField.getSelectionModel().selectFirst();
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
