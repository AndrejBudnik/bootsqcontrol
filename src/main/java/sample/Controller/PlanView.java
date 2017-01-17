package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.Model.EmployeeEntity;
import sample.Model.PlanEntity;
import sample.Model.ZakazEntity;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static sample.Constants.*;

/**
 * Created by kotsy on 22.12.2016.
 */
public class PlanView {
    @FXML
    public Button cancelButton;
    @FXML
    public Button confirmButton;
    @FXML
    public Button exitButton;
    public ChoiceBox<EmployeeEntity> employeeField;
    @FXML
    private TextField actualCountField;
    @FXML
    private TextField expectedCountField;
    @FXML
    private ChoiceBox<ZakazEntity> orderField;
    @FXML
    private DatePicker dateField;

    private boolean isDataOk;

    /**
     * Initializes Plan adding window, where orderField stands for the dropdown menu,
     * fasonField, sizeField, actualCountField, expectedCountField are TextFeild items,
     * dateField is a DatePicker.
     */
    @SuppressWarnings(UNCHECKED)
    @FXML
    public void initialize(){

        orderField.setTooltip(new Tooltip(USER_CHOOSE_ORDER));
        orderField.setItems(new ZakazEntity().select());
        orderField.getSelectionModel().selectFirst();
        employeeField.setTooltip(new Tooltip(USER_CHOOSE_ORDER));
        employeeField.setItems(new EmployeeEntity().select());
        employeeField.getSelectionModel().selectFirst();
        fieldListener(actualCountField, REGEX_COUNT_MATCH);
        fieldListener(expectedCountField, REGEX_COUNT_MATCH);

        dateListener(dateField);


    }

    /**
     * OnAction method for confirmButton.
     * Parses all the data from the window,
     * inserts created entity into database if the data is correct.
     */
    @SuppressWarnings(UNCHECKED)
    @FXML
    public void addEntity(){
        ZakazEntity order = orderField.getSelectionModel().getSelectedItem();
        EmployeeEntity employee = employeeField.getSelectionModel().getSelectedItem();
        int actualCount = Integer.parseInt(actualCountField.getText());
        int expectedCount = Integer.parseInt(expectedCountField.getText());
        List<PlanEntity> plans = new PlanEntity().select();

        int plansSum = 0;
        for (PlanEntity p: plans) {
            if(p.getOrder().getId() == order.getId()){
                plansSum += p.getExpectedCnt();
            }
        }
        if(actualCount > expectedCount || expectedCount > order.getCount() || expectedCount > (order.getCount() - plansSum)){
            isDataOk = false;
        }

        if(isDataOk) {
            try {
                LocalDate ld = dateField.getValue();
                Date date = Date.valueOf(ld);

                PlanEntity entity = new PlanEntity();
                entity.setFason(order.getFason());
                entity.setSize(order.getSize());
                entity.setActualCnt(actualCount);
                entity.setOrder(order);
                entity.setExpectedCnt(expectedCount);
                entity.setPlanDate(date);
                entity.setEmployee(employee);

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
     * Parses the data from a DatePicker field after the one loses focus.
     * Converts it to a LocalDate variable.
     * @param field - DatePicker field to listen
     */
    private void dateListener(DatePicker field){
        field.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return EMPTY;
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }

    /**
     * Clears all the input fields and disables highlighting.
     */
    private void clearAllFields(){
        actualCountField.clear();
        expectedCountField.clear();
        actualCountField.setStyle(null);
        expectedCountField.setStyle(null);
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
