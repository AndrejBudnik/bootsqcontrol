package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Model.EmployeeEntity;
import sample.Model.PlanEntity;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static sample.Constants.*;

/**
 * Created by kotsy on 22.12.2016.
 */
public class EmployeeView {
    @FXML
    public Button cancelButton;
    @FXML
    public Button confirmButton;
    @FXML
    public Button exitButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField postField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField telNumField1;
    @FXML
    private TextField telNumField2;
    @FXML
    private TextField telNumField3;
    @FXML
    private TextField telNumField4;

    private boolean isDataOk;

    /**
     * Initializes Employee adding window, where planField stands for the dropdown menu,
     * nameField, postField, salaryField are TextFeild items,
     * telNumField* are the fields for a telephone number.
     */
    @SuppressWarnings(UNCHECKED)
    @FXML
    public void initialize(){
        fieldListener(nameField, REGEX_NAME_MATCH);
        fieldListener(postField, REGEX_POST_MATCH);
        fieldListener(salaryField, REGEX_CURRENCY_MATCH);

        numberFocusesCheck(telNumField1);
        numberFocusesCheck(telNumField2);
        numberFocusesCheck(telNumField3);
        numberFocusesCheck(telNumField4);

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
            StringBuilder sb = new StringBuilder();

            String number = sb.append(telNumField1.getText()).append(telNumField2.getText()).append(telNumField3.getText())
                    .append(telNumField4.getText()).toString();
            try {
                BigDecimal salary = new BigDecimal(salaryField.getText());
                try {
                    EmployeeEntity entity = new EmployeeEntity();
                    entity.setName(nameField.getText());
                    entity.setSalary(salary);
                    entity.setPost(postField.getText());
                    entity.setTelNum(number);

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
                    isDataOk = false;
                }
                else {
                    isDataOk = true;
                }
            } else {
                field.setStyle(null);
            }
        });
    }

    /**
     * Parses the data from a telNum fields after they lose focus.
     * Converts it to a single telephone String variable.
     * @param field - is the number field to listen
     */
    private void numberFocusesCheck(TextField field){
        StringBuilder sb = new StringBuilder();
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!field.isFocused()) {
                String number = sb.append(telNumField1.getText()).append(telNumField2.getText()).append(telNumField3.getText())
                        .append(telNumField4.getText()).toString();
                Pattern p = Pattern.compile(REGEX_TELEPHONE_NUMBER_MATCH);
                Matcher m = p.matcher(number);
                System.out.println(number);
                if (!m.matches()) {
                    telNumField1.setStyle(STYLE_BG_COLOR);
                    telNumField2.setStyle(STYLE_BG_COLOR);
                    telNumField3.setStyle(STYLE_BG_COLOR);
                    telNumField4.setStyle(STYLE_BG_COLOR);
                    this.isDataOk = false;
                } else {
                    this.isDataOk = true;
                }
            } else {
                telNumField1.setStyle(null);
                telNumField2.setStyle(null);
                telNumField3.setStyle(null);
                telNumField4.setStyle(null);
            }
        });
        telNumField1.textProperty().addListener((obs, oldText, newText) -> {
            if (oldText.length() < 3 && newText.length() >= 3) {
                telNumField2.requestFocus();
            }
        });

        telNumField2.textProperty().addListener((obs, oldText, newText) -> {
            if (oldText.length() < 3 && newText.length() >= 3) {
                telNumField3.requestFocus();
            }
        });

        telNumField3.textProperty().addListener((obs, oldText, newText) -> {
            if (oldText.length() < 3 && newText.length() >= 3) {
                telNumField4.requestFocus();
            }
        });

        telNumField4.textProperty().addListener((obs, oldText, newText) -> {
            if (oldText.length() < 4 && newText.length() >= 4) {
                confirmButton.requestFocus();
            }
        });
    }

    /**
     * Clears all the input fields and disables highlighting.
     */
    private void clearAllFields(){
        nameField.clear();
        salaryField.clear();
        postField.clear();
        telNumField1.clear();
        telNumField2.clear();
        telNumField3.clear();
        telNumField4.clear();
        nameField.setStyle(null);
        salaryField.setStyle(null);
        postField.setStyle(null);
        telNumField1.setStyle(null);
        telNumField2.setStyle(null);
        telNumField3.setStyle(null);
        telNumField4.setStyle(null);
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
