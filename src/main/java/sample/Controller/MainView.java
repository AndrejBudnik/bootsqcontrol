package sample.Controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;
import sample.Model.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static sample.Constants.*;


/**
 * Created by kotsy on 20.12.2016.
 */
public class MainView {

    //Some silly javafx stuff
    //Literally a redefine of all the gui elements
    public Button addCustomer;
    public TabPane tabPane;
    public Button refreshCustomer;

    @FXML
    private TableView<CustomerEntity> customerTable;
    @FXML
    private TableColumn<CustomerEntity, Integer> customerIdCol;
    @FXML
    private TableColumn<CustomerEntity, String> customerNameCol;
    @FXML
    private TableColumn<CustomerEntity, String > customerNumCol;
    @FXML
    private TableColumn<CustomerEntity, String> customerMailCol;
    public TextField customerIdSort;
    public TextField customerNumSort;
    public TextField customerMailSort;
    public TextField customerNameSort;

    public Button addDefect;
    public Button refreshDefect;
    @FXML
    private TableView<DefectEntity> defectTable;
    @FXML
    private TableColumn<DefectEntity, Integer> defectIdCol;
    @FXML
    private TableColumn<DefectEntity, String> defectDescCol;
    public TextField defectIdSort;
    public TextField defectDescSort;

    public Button addEmp;
    public Button refreshEmployee;
    @FXML
    private TableView<EmployeeEntity> empTable;
    @FXML
    private TableColumn<EmployeeEntity, Integer> empIdCol;
    @FXML
    private TableColumn<EmployeeEntity, String> empNameCol;
    @FXML
    private TableColumn<EmployeeEntity, String> empPostCol;
    @FXML
    private TableColumn<EmployeeEntity, String> empSalaryCol;
    @FXML
    private TableColumn<EmployeeEntity, String > empNumCol;
    public TextField empIdSort;
    public TextField empNameSort;
    public TextField empPostSort;
    public TextField empSalarySort;
    public TextField empNumSort;

    public Button addPlan;
    public Button refreshPlan;
    @FXML
    private TableView<PlanEntity> planTable;
    @FXML
    private TableColumn<PlanEntity, Integer> planIdCol;
    @FXML
    private TableColumn<PlanEntity, Integer> planOrderNumCol;
    @FXML
    private TableColumn<PlanEntity, String > planDateCol;
    @FXML
    private TableColumn<PlanEntity, String > planEmpCol;
    @FXML
    private TableColumn<PlanEntity, Integer> planSizeCol;
    @FXML
    private TableColumn<PlanEntity, String> planFasonCol;
    @FXML
    private TableColumn<PlanEntity, Integer> planCountCol;
    @FXML
    private TableColumn<PlanEntity, Integer> planActualCol;
    public TextField planIdSort;
    public TextField planOrderSort;
    public TextField planDateSort;
    public TextField planEmpSort;
    public TextField planSizeSort;
    public TextField planCountSort;
    public TextField planFasonSort;
    public TextField planActualSort;

    public Button addOrder;
    public Button refreshOrder;
    @FXML
    private TableView<ZakazEntity> orderTable;
    @FXML
    private TableColumn<ZakazEntity, Integer> orderIdCol;
    @FXML
    private TableColumn<ZakazEntity, String > orderCustomerCol;
    @FXML
    private TableColumn<ZakazEntity, Integer> orderCountCol;
    @FXML
    private TableColumn<ZakazEntity, String > orderFasonCol;
    @FXML
    private TableColumn<ZakazEntity, String > orderDateCol;
    @FXML
    private TableColumn<ZakazEntity, Integer > orderSizeCol;
    public TextField orderIdSort;
    public TextField orderCustSort;
    public TextField orderCountSort;
    public TextField orderFasonSort;
    public TextField orderDateSort;
    public TextField orderSizeSort;

    public Button addDefMon;
    public Button refreshDefectMon;
    @FXML
    private TableView<DefectMonEntity > defectMonTable;
    @FXML
    private TableColumn<DefectMonEntity, Integer> defConPlanNumCol;
    @FXML
    private TableColumn<DefectMonEntity, String> defConDateCol;
    @FXML
    private TableColumn<DefectMonEntity, Integer> defConCountCol;
    @FXML
    private TableColumn<DefectMonEntity, String > defConDefectCol;
    public TextField defMonPlanSort;
    public TextField defMonDateSort;
    public TextField defMonCountSort;
    public TextField defMonDefectSort;

    @FXML
    private MenuItem contextDeleteCustomer;
    @FXML
    private MenuItem contextDeleteDefMon;
    @FXML
    private MenuItem contextDeletePlan;
    @FXML
    private MenuItem contextDeleteEmp;
    @FXML
    private MenuItem contextDeleteOrder;
    @FXML
    private MenuItem contextDeleteDefect;
    @FXML
    private MenuItem contextUpdateCustomer;


    /**
     * Initialize tables on the main view, adding context actions.
     */
    @FXML
    private void initialize(){
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        initializeCustomer();
        initializeOrder();
        initializeEmployee();
        initializePlan();
        initializeDefectMonitor();
        initializeDefect();

        initializeContextDelete();
    }

    /**
     * Initialize context menu on a TableView and adding Delete option.
     */
    private void initializeContextDelete(){

        contextDeleteCustomer.setOnAction(event -> {
            if (confirmation(ALERT_DELETE_CONFIRM_TITLE, ALERT_DELETE_CONFIRM_HEADER, ALERT_DELETE_CONFIRM_TEXT) == ButtonType.OK) {
                CustomerEntity entity = customerTable.getSelectionModel().getSelectedItem();
                entity.delete(entity);
                initializeCustomer();
            }
        });

        contextDeleteDefect.setOnAction(event -> {
            if (confirmation(ALERT_DELETE_CONFIRM_TITLE, ALERT_DELETE_CONFIRM_HEADER, ALERT_DELETE_CONFIRM_TEXT) == ButtonType.OK) {
                DefectEntity entity = defectTable.getSelectionModel().getSelectedItem();
                entity.delete(entity);
                initializeDefect();
            }
        });

        contextDeleteDefMon.setOnAction(event -> {
            if (confirmation(ALERT_DELETE_CONFIRM_TITLE, ALERT_DELETE_CONFIRM_HEADER, ALERT_DELETE_CONFIRM_TEXT) == ButtonType.OK) {
                DefectMonEntity entity = defectMonTable.getSelectionModel().getSelectedItem();
                entity.delete(entity);
                initializeDefectMonitor();
            }
        });

        contextDeleteEmp.setOnAction(event -> {
            if (confirmation(ALERT_DELETE_CONFIRM_TITLE, ALERT_DELETE_CONFIRM_HEADER, ALERT_DELETE_CONFIRM_TEXT) == ButtonType.OK) {
                EmployeeEntity entity = empTable.getSelectionModel().getSelectedItem();
                entity.delete(entity);
                initializeEmployee();
            }
        });

        contextDeleteOrder.setOnAction(event -> {
            if (confirmation(ALERT_DELETE_CONFIRM_TITLE, ALERT_DELETE_CONFIRM_HEADER, ALERT_DELETE_CONFIRM_TEXT) == ButtonType.OK) {
                ZakazEntity entity = orderTable.getSelectionModel().getSelectedItem();
                entity.delete(entity);
                initializeOrder();
            }
        });

        contextDeletePlan.setOnAction(event -> {
            if (confirmation(ALERT_DELETE_CONFIRM_TITLE, ALERT_DELETE_CONFIRM_HEADER, ALERT_DELETE_CONFIRM_TEXT) == ButtonType.OK) {
                PlanEntity entity = planTable.getSelectionModel().getSelectedItem();
                entity.delete(entity);
                initializePlan();
            }
        });
    }

    //Здесь лежит UPDATE сущностей
    //Могут встретиться баги - я хотел спать.

    /**
     * Initializes Customer entity view.
     * In particular, adding table-entity and column-field relation.
     */
    private void initializeCustomer(){
        ObservableList<CustomerEntity> entities = FXCollections.observableArrayList();
        entities.addAll(new CustomerEntity().select());

        customerIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        customerNumCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelNum()));
        customerMailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        customerTable.setItems(entities);
        customerTable.setEditable(true);
        initCustomerEdit();
        exportCustomer();
        filterCustomer();
    }

    /**
     * Defines an Edit option for each Customer table's column.
     */
    private void initCustomerEdit(){
        customerNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        customerNameCol.setOnEditCommit(
                event -> {
                    String newValue = event.getNewValue();
                    Pattern p = Pattern.compile(REGEX_NAME_MATCH);
                    Matcher m = p.matcher(newValue);
                    if(m.matches()) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
                        try {
                            new CustomerEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        customerNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        customerNumCol.setOnEditCommit(
                event -> {
                    String newValue = event.getNewValue();
                    Pattern p = Pattern.compile(REGEX_SIMPLE_TELEPHONE_NUMBER_MATCH);
                    Matcher m = p.matcher(newValue);
                    if (m.matches()){
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setTelNum(newValue);
                        try {
                            new CustomerEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        customerMailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        customerMailCol.setOnEditCommit(
                event -> {
                    String newValue = event.getNewValue();
                    Pattern p = Pattern.compile(REGEX_EMAIL_MATCH);
                    Matcher m = p.matcher(newValue);
                    if (m.matches()){
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setTelNum(newValue);
                        try {
                            new CustomerEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    /**
     * Initializes Order entity view.
     * In particular, adding table-entity and column-field relation.
     */
    private void initializeOrder(){
        ObservableList<ZakazEntity> entities = FXCollections.observableArrayList();
        entities.addAll(new ZakazEntity().select());

        orderIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        orderCustomerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
        orderCountCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCount()).asObject());
        orderFasonCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFason()));
        orderDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderDate().toString()));
        orderSizeCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSize()).asObject());

        orderTable.setItems(entities);
        customerTable.setEditable(true);
        initOrderEdit();
        exportOrder();
        filterOrder();
    }
    /**
     * Defines an Edit option for each Order table's column.
     */
    private void initOrderEdit(){
        orderCustomerCol.setCellFactory(TextFieldTableCell.forTableColumn());
        orderCustomerCol.setOnEditCommit(
                event -> {
                    String newValue = event.getNewValue();
                    List<CustomerEntity> list = new CustomerEntity().select();
                    for (CustomerEntity entity: list) {
                        if (newValue.equals(list.get(list.size()-1).getName())){
                            event.getTableView().getItems().get(event.getTablePosition().getRow()).setCustomer(entity);
                            new ZakazEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                        }
                    }
                }
        );

        orderCountCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        orderCountCol.setOnEditCommit(
                event -> {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setCount(event.getNewValue());
                    new ZakazEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );

        orderFasonCol.setCellFactory(TextFieldTableCell.forTableColumn());
        orderFasonCol.setOnEditCommit(
                event -> {
                    String newValue = event.getNewValue();
                    Pattern p = Pattern.compile(REGEX_FASON_MATCH);
                    Matcher m = p.matcher(newValue);
                        if (m.matches()){
                            event.getTableView().getItems().get(event.getTablePosition().getRow()).setFason(newValue);
                            new ZakazEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                        }
                }
        );

        orderDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        orderDateCol.setOnEditCommit(
                event -> {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setOrderDate(Date.valueOf(event.getNewValue()));
                    new ZakazEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );

        orderSizeCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        orderSizeCol.setOnEditCommit(
                event -> {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setSize(event.getNewValue());
                    new ZakazEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );
    }

    /**
     * Initializes Employee entity view.
     * In particular, adding table-entity and column-field relation.
     */
    private void initializeEmployee(){
        ObservableList<EmployeeEntity> entities = FXCollections.observableArrayList();
        entities.addAll(new EmployeeEntity().select());

        BigDecimalStringConverter c = new BigDecimalStringConverter();

        empIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        empNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        empPostCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPost()));
        empSalaryCol.setCellValueFactory(cellData -> new SimpleStringProperty(c.toString(cellData.getValue().getSalary())));
        empNumCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelNum()));

        empTable.setEditable(true);
        empTable.setItems(entities);
        initEmpEdit();
        exportEmployee();
        filterEmployee();
    }
    /**
     * Defines an Edit option for each Employee table's column.
     */
    private void initEmpEdit(){
        empNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empNameCol.setOnEditCommit(
                event -> {
                    String name = event.getNewValue();
                    Pattern p = Pattern.compile(REGEX_NAME_MATCH);
                    Matcher m = p.matcher(name);
                    if (m.matches()) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
                        new EmployeeEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    }
                }
        );

        empPostCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empPostCol.setOnEditCommit(
                event -> {
                    String post = event.getNewValue();
                    Pattern p = Pattern.compile(REGEX_POST_MATCH);
                    Matcher m = p.matcher(post);
                    if (m.matches()) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setPost(event.getNewValue());
                        new EmployeeEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    }
                }
        );
        empSalaryCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empSalaryCol.setOnEditCommit(
                event -> {
                    String salary = event.getNewValue();
                    Pattern p = Pattern.compile(REGEX_CURRENCY_MATCH);
                    Matcher m = p.matcher(salary);
                    if (m.matches()) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setSalary(new BigDecimal(event.getNewValue()));
                        new EmployeeEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    }
                }
        );
        empNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        empNumCol.setOnEditCommit(
                event -> {
                    String name = event.getNewValue();
                    Pattern p = Pattern.compile(REGEX_SIMPLE_TELEPHONE_NUMBER_MATCH);
                    Matcher m = p.matcher(name);
                    if (m.matches()) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setTelNum(event.getNewValue());
                        new EmployeeEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    }
                }
        );


    }

    /**
     * Initializes Plan entity view.
     * In particular, adding table-entity and column-field relation.
     */
    private void initializePlan(){
        ObservableList<PlanEntity> entities = FXCollections.observableArrayList();
        entities.addAll(new PlanEntity().select());
        planIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        planOrderNumCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOrder().getId()).asObject());
        planDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlanDate().toString()));
        planEmpCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getName()));
        planSizeCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSize()).asObject());
        planFasonCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFason()));
        planCountCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getExpectedCnt()).asObject());
        planActualCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getActualCnt()).asObject());

        planTable.setEditable(true);
        planTable.setItems(entities);
        initPlanEdit();
        exportPlan();
        filterPlan();

    }
    /**
     * Defines an Edit option for each Plan table's column.
     */
    private void initPlanEdit(){
        planCountCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        planCountCol.setOnEditCommit(
                event -> {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setExpectedCnt(event.getNewValue());
                    new PlanEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );

        planFasonCol.setCellFactory(TextFieldTableCell.forTableColumn());
        planFasonCol.setOnEditCommit(
                event -> {
                    String newValue = event.getNewValue();
                    Pattern p = Pattern.compile(REGEX_FASON_MATCH);
                    Matcher m = p.matcher(newValue);
                    if(m.matches()) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setFason(event.getNewValue());
                        new PlanEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    }
                }
        );

        planActualCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        planActualCol.setOnEditCommit(
                event -> {
                    int newValue = event.getNewValue();
                    if (newValue < event.getTableView().getItems().get(event.getTablePosition().getRow()).getExpectedCnt()) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setSize(event.getNewValue());
                        new PlanEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    }
                }
        );

        planSizeCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        planSizeCol.setOnEditCommit(
                event -> {
                    int newValue = event.getNewValue();
                    if (newValue < 50 ) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setSize(event.getNewValue());
                        new PlanEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    }
                }
        );

        planOrderNumCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        planOrderNumCol.setOnEditCommit(
                event -> {
                    int newValue = event.getNewValue();
                    List<ZakazEntity> list = new ZakazEntity().select();
                    if (newValue < list.size()) {
                        if (newValue > list.get(list.size() - 1).getId()) {
                            event.getTableView().getItems().get(event.getTablePosition().getRow()).setOrder(list.get(newValue));
                            new PlanEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(ALERT_NO_SUCH_ORDER);
                            alert.showAndWait();
                        }
                    }
                }
        );

        planDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        planDateCol.setOnEditCommit(
                event -> {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setPlanDate(Date.valueOf(event.getNewValue()));
                    new PlanEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );

        planEmpCol.setCellFactory(TextFieldTableCell.forTableColumn());
        planEmpCol.setOnEditCommit(
                event -> {
                    String newValue = event.getNewValue();
                    List<EmployeeEntity> list = new EmployeeEntity().select();
                    for (EmployeeEntity entity: list) {
                        if (newValue.equals(list.get(list.size()-1).getName())){
                            event.getTableView().getItems().get(event.getTablePosition().getRow()).setEmployee(entity);
                            new PlanEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                        }
                    }
                }
        );
    }

    /**
     * Initializes Defect Monitor entity view.
     * In particular, adding table-entity and column-field relation.
     */
    private void initializeDefectMonitor(){
        ObservableList<DefectMonEntity> entities = FXCollections.observableArrayList();
        entities.addAll(new DefectMonEntity().select());
        System.out.println(new DefectMonEntity().select());
        defConPlanNumCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPlan().getId()).asObject());
        defConDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckDate().toString()));
        defConCountCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDefectCount()).asObject());
        defConDefectCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDefectType().getType()));

        defectMonTable.setItems(entities);
        customerTable.setEditable(true);
        initDefMonEdit();
        exportDefectMon();
        filterDefectMonitor();
    }
    /**
     * Defines an Edit option for each Defect Monitor table's column.
     */
    private void initDefMonEdit(){
        defConPlanNumCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        defConPlanNumCol.setOnEditCommit(
                event -> {
                    int newValue = event.getNewValue();
                    List<PlanEntity> list = new PlanEntity().select();
                    if (newValue > list.get(list.size()-1).getId()) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setPlan(list.get(newValue));
                        new DefectMonEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(ALERT_NO_SUCH_ORDER);
                        alert.showAndWait();
                    }
                }
        );

        defConDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        defConDateCol.setOnEditCommit(
                event -> {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setCheckDate(Date.valueOf(event.getNewValue()));
                    new DefectMonEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );

        defConDefectCol.setCellFactory(TextFieldTableCell.forTableColumn());
        defConDefectCol.setOnEditCommit(
                event -> {
                    String newValue = event.getNewValue();
                    List<DefectEntity> list = new DefectEntity().select();
                    list.stream().filter(entity -> newValue.equals(list.get(list.size() - 1).getType())).forEach(entity -> {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setDefectType(entity);
                        new DefectMonEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    });
                }
        );

        defConCountCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        defConCountCol.setOnEditCommit(
                event -> {
                    //возможно пропустил проверку
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setDefectCount(event.getNewValue());
                    new DefectMonEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );
    }

    /**
     * Initializes Defect entity view.
     * In particular, adding table-entity and column-field relation.
     */
    private void initializeDefect(){
        ObservableList<DefectEntity> entities = FXCollections.observableArrayList();
        entities.addAll(new DefectEntity().select());
        defectIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        defectDescCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));

        defectTable.setItems(entities);
        customerTable.setEditable(true);
        initDefectEdit();
        exportDefect();
        filterDefect();
    }
    /**
     * Defines an Edit option for each Defect table's column.
     */
    private void initDefectEdit(){
        defectDescCol.setCellFactory(TextFieldTableCell.forTableColumn());
        defectDescCol.setOnEditCommit(
                event -> {
                    event.getTableView().getItems().get(event.getTablePosition().getRow()).setType(event.getNewValue());
                    new DefectEntity().insert(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                }
        );
    }


    /**
     * Creates Customer.csv and exports all the Customer data from the database.
     */
    private void exportCustomer(){
        Writer writer = null;
        try {

            File file = new File(EXPORT_CUSTOMER_FILEPATH);
            writer = new BufferedWriter(new FileWriter(file));
            List<CustomerEntity> entities = new CustomerEntity().select();
            writer.write(EXPORT_CUSTOMER_TABLE_HEADER);
            for (CustomerEntity entity : entities) {

                String text = entity.getName() + COMMA + entity.getTelNum() + COMMA + entity.getEmail() + NEWLINE;
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Creates Defect.csv and exports all the Defect data from the database.
     */
    private void exportDefect(){
        Writer writer = null;
        try {
            File file = new File(EXPORT_DEFECT_FILEPATH);
            writer = new BufferedWriter(new FileWriter(file));
            List<DefectEntity> entities = new DefectEntity().select();
            writer.write(EXPORT_DEFECT_TABLE_HEADER);
            for (DefectEntity entity : entities) {
                String text = entity.getId() + COMMA + entity.getType() + NEWLINE;
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Creates DefectMonitor.csv and exports all the Defect Monitor data from the database.
     */
    private void exportDefectMon(){
        Writer writer = null;
        try {
            File file = new File(EXPORT_DEFECT_MONITOR_FILEPATH);
            writer = new BufferedWriter(new FileWriter(file));
            List<DefectMonEntity> entities = new DefectMonEntity().select();
            writer.write(EXPORT_DEFECT_MONITOR_TABLE_HEADER);
            for (DefectMonEntity entity : entities) {

                String text = entity.getCheckDate() + COMMA + entity.getDefectCount() + COMMA + entity.getDefectType() + NEWLINE;
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Creates Employee.csv and exports all the Employee data from the database.
     */
    private void exportEmployee(){
        Writer writer = null;
        try {
            File file = new File(EXPORT_EMPLOYEE_FILEPATH);
            writer = new BufferedWriter(new FileWriter(file));
            List<EmployeeEntity> entities = new EmployeeEntity().select();
            writer.write(EXPORT_EMPLOYEE_TABLE_HEADER);
            for (EmployeeEntity entity : entities) {

                String text = entity.getName() + COMMA + entity.getPost() + COMMA + entity.getSalary() + COMMA + entity.getTelNum() + NEWLINE;
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Creates Plan.csv and exports all the Plan data from the database.
     */
    private void exportPlan(){
        Writer writer = null;
        try {
            File file = new File(EXPORT_PLAN_FILEPATH);
            writer = new BufferedWriter(new FileWriter(file));
            List<PlanEntity> entities = new PlanEntity().select();
            writer.write(EXPORT_PLAN_TABLE_HEADER);
            for (PlanEntity entity : entities) {

                String text = entity.getId() + COMMA + entity.getEmployee() + COMMA + entity.getExpectedCnt() + COMMA
                        + entity.getActualCnt() + COMMA + entity.getSize() + COMMA + entity.getFason() + NEWLINE;
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Creates Order.csv and exports all the Order data from the database.
     */
    private void exportOrder(){
        Writer writer = null;
        try {
            File file = new File(EXPORT_ORDER_FILEPATH);
            writer = new BufferedWriter(new FileWriter(file));
            List<ZakazEntity> entities = new ZakazEntity().select();
            writer.write(EXPORT_ORDER_TABLE_HEADER);
            for (ZakazEntity entity : entities) {

                String text = entity.getId() + COMMA + entity.getFason() + COMMA + entity.getCount() + COMMA
                        + entity.getSize() + COMMA + entity.getOrderDate() +NEWLINE;
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Defines a filter fields below the Customer table and sets up filter algorithm.
     */
    private void filterCustomer(){
        FilteredList<CustomerEntity> filteredData = new FilteredList<>(new CustomerEntity().select(), p -> true);
        customerIdSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getId()).contains(lowerCaseFilter);
            });
        });
        customerNameSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });
        customerMailSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getEmail().toLowerCase().contains(lowerCaseFilter);
            });
        });
        customerNumSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getTelNum().contains(lowerCaseFilter);
            });
        });
        SortedList<CustomerEntity> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedData);
    }

    /**
     * Defines a filter fields below the Defect table and sets up filter algorithm.
     */
    private void filterDefect(){
        FilteredList<DefectEntity> filteredData = new FilteredList<>(new DefectEntity().select(), p -> true);
        defectIdSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getId()).contains(lowerCaseFilter);
            });
        });
        defectDescSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getType().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<DefectEntity> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(defectTable.comparatorProperty());
        defectTable.setItems(sortedData);
    }

    /**
     * Defines a filter fields below the Order table and sets up filter algorithm.
     */
    private void filterOrder(){
        FilteredList<ZakazEntity> filteredData = new FilteredList<>(new ZakazEntity().select(), p -> true);
        orderIdSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getId()).contains(lowerCaseFilter);
            });
        });
        orderSizeSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getSize()).contains(lowerCaseFilter);
            });
        });
        orderCountSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getCount()).contains(lowerCaseFilter);
            });
        });
        orderCustSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getCustomer().getId()).contains(lowerCaseFilter);
            });
        });
        orderDateSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getOrderDate()).contains(lowerCaseFilter);
            });
        });
        orderFasonSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getFason().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<ZakazEntity> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(orderTable.comparatorProperty());
        orderTable.setItems(sortedData);
    }

    /**
     * Defines a filter fields below the Defect Monitor table and sets up filter algorithm.
     */
    private void filterDefectMonitor(){
        FilteredList<DefectMonEntity> filteredData = new FilteredList<>(new DefectMonEntity().select(), p -> true);
        defMonDateSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getCheckDate()).contains(lowerCaseFilter);
            });
        });
        defMonPlanSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getPlan().getId()).contains(lowerCaseFilter);
            });
        });
        defMonCountSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getDefectCount()).contains(lowerCaseFilter);
            });
        });
        defMonDefectSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getDefectType().getType().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<DefectMonEntity> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(defectMonTable.comparatorProperty());
        defectMonTable.setItems(sortedData);
    }

    /**
     * Defines a filter fields below the Plan table and sets up filter algorithm.
     */
    private void filterPlan(){
        FilteredList<PlanEntity> filteredData = new FilteredList<>(new PlanEntity().select(), p -> true);
        planIdSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getId()).contains(lowerCaseFilter);
            });
        });
        planSizeSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getSize()).contains(lowerCaseFilter);
            });
        });
        planOrderSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getOrder().getId()).contains(lowerCaseFilter);
            });
        });
        planDateSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getPlanDate()).contains(lowerCaseFilter);
            });
        });
        planEmpSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getEmployee().getName().contains(lowerCaseFilter);
            });
        });
        planFasonSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getFason().toLowerCase().contains(lowerCaseFilter);
            });
        });
        planCountSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getExpectedCnt()).contains(lowerCaseFilter);
            });
        });
        planActualSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getActualCnt()).contains(lowerCaseFilter);
            });
        });
        SortedList<PlanEntity> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(planTable.comparatorProperty());
        planTable.setItems(sortedData);
    }

    /**
     * Defines a filter fields below the Employee table and sets up filter algorithm.
     */
    private void filterEmployee(){
        FilteredList<EmployeeEntity> filteredData = new FilteredList<>(new EmployeeEntity().select(), p -> true);
        empIdSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getId()).contains(lowerCaseFilter);
            });
        });
        empNameSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });
        empPostSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getPost().toLowerCase().contains(lowerCaseFilter);
            });
        });
        empSalarySort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return String.valueOf(entity.getSalary()).contains(lowerCaseFilter);
            });
        });
        empNumSort.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entity -> {
                if (newValue == null || newValue.isEmpty()) { return true; }
                String lowerCaseFilter = newValue.toLowerCase();
                return entity.getTelNum().contains(lowerCaseFilter);
            });
        });
        SortedList<EmployeeEntity> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(empTable.comparatorProperty());
        empTable.setItems(sortedData);
    }

    @FXML
    public void onActionAddCustomer(){
        loadScene(CUSTOMER_VIEW_FILEPATH);
    }

    @FXML
    public void onActionAddEmp(){
        loadScene(EMPLOYEE_VIEW_FILEPATH);
    }

    @FXML
    public void onActionAddOrder(){
        loadScene(ORDER_VIEW_FILEPATH);
    }

    @FXML
    public void onActionAddPlan(){
        loadScene(PLAN_VIEW_FILEPATH);
    }

    @FXML
    public void onActionAddDefect(){
        loadScene(DEFECT_VIEW_FILEPATH);
    }

    @FXML
    public void onActionAddDefectMon(){
        loadScene(DEFECT_MONITOR_VIEW_FILEPATH);
    }

    @FXML
    public void onActionRefreshCustomer(){
        initializeCustomer();
    }

    @FXML
    public void onActionRefreshEmployee(){
        initializeEmployee();
    }

    @FXML
    public void onActionRefreshPlan(){
        initializePlan();
    }

    @FXML
    public void onActionRefreshOrder(){
        initializeOrder();
    }

    @FXML
    public void onActionRefreshDefect(){
        initializeDefect();
    }

    @FXML
    public void onActionRefreshDefectMon(){
        initializeDefectMonitor();
    }

    @FXML
    public void onActionExit(){
        if (confirmation(ALERT_EXIT_CONFIRM_TITLE, ALERT_EXIT_CONFIRM_HEADER, ALERT_EXIT_CONFIRM_TEXT) == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    private ButtonType confirmation(String title, String header, String context){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get();
    }

    /**
     * Loads an *.fxml file and creates a new window for it.
     * @param xmlPath - *.fxml file path to load
     */
    private void loadScene(String xmlPath){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(xmlPath));
            Parent root2 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root2));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
