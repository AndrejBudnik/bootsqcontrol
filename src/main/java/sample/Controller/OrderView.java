package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.Model.CustomerEntity;
import sample.Model.ZakazEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static sample.Constants.*;
/**
 * Created by kotsy on 22.12.2016.
 */
public class OrderView {
    @FXML
    public Button cancelButton;
    @FXML
    public Button confirmButton;
    @FXML
    public Button exitButton;
    @FXML
    private TextField countField;
    @FXML
    private ChoiceBox<String> fasonField;
    @FXML
    private TextField sizeField;
    @FXML
    private DatePicker dateField;
    @FXML
    private ChoiceBox<CustomerEntity> customerField;

    private boolean isDataOk;

    /**
     * Initializes Order adding window, where customerField stands for the dropdown menu,
     * fasonField, countField, sizeField are TextFeild items,
     * dateField is a DatePicker.
     */
    @SuppressWarnings(UNCHECKED)
    @FXML
    public void initialize(){

        customerField.setTooltip(new Tooltip(USER_CHOOSE_CUSTOMER));
        customerField.setItems(new CustomerEntity().select());
        customerField.getItems().add(null);
        fasonField.setTooltip(new Tooltip(USER_CHOOSE_CUSTOMER));
        customerField.setItems(new CustomerEntity().select());
        fieldListener(countField, REGEX_COUNT_MATCH);
        fieldListener(sizeField, REGEX_SIZE_MATCH);
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
        if(isDataOk) {
            try {
                int count = Integer.parseInt(countField.getText());
                int size = Integer.parseInt(sizeField.getText());

                CustomerEntity customer = customerField.getSelectionModel().getSelectedItem();

                LocalDate ld = dateField.getValue();
                Date date = Date.valueOf(ld);

                ZakazEntity entity = new ZakazEntity();
                entity.setCount(count);
                entity.setSize(size);
                entity.setFason(fasonField.getSelectionModel().getSelectedItem());
                entity.setOrderDate(date);
                entity.setCustomer(customer);

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
        sizeField.clear();
        countField.clear();

        fasonField.setStyle(null);
        sizeField.setStyle(null);
        countField.setStyle(null);
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
